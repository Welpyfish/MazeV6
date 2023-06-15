package model;

import controller.GameEngine;
import controller.GameState;
import model.item.*;
import model.weapon.*;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.*;

// Hold all game objects
public class Map {
    public Tile[][] tileMap;
    public Player player;
    private TileObject endPortal;
    public ArrayList<Character> characters;
    public ArrayList<TileObject> gameElements;
    public ArrayList<Projectile> projectiles;
    public ArrayList<Item> items;
    public ArrayList<VisualEffect> effects;
    public ArrayList<Portal> portals;
    private Point mouse;
    private boolean gameOver;

    public Map(){
        gameElements = new ArrayList<>();
        projectiles = new ArrayList<>();
        characters = new ArrayList<>();
        items = new ArrayList<>();
        effects = new ArrayList<>();
        portals = new ArrayList<>();
        mouse = new Point(0, 0);
        new WeaponFactory(this);
    }

    public void update(){
        updateObjects();
        manageInteractions();
        removeObjects();
    }

    private void updateObjects(){
        for(Projectile projectile : projectiles){
            projectile.update();
        }

        for(Character character : characters){
            character.update();
        }

        for(VisualEffect effect : effects){
            effect.update();
        }

        for(TileObject element : gameElements){
            element.update();
        }

        for(Item item : items){
            item.update();
        }
    }

    private void manageInteractions(){
        for(Projectile projectile : projectiles){
            projectileCollision(projectile);
        }

        if(endPortal.getRect().contains(player.getRect())){
            gameOver = true;
        }
        Item item = checkItems(player.getRect());
        if(item!=null){
            if(item.getWeaponType()!=null){
                player.addWeapon(item.getWeaponType());
            }else if(item.getProjectileType()!=null){
                player.inventory.changeProjectile(item.getProjectileType(), item.getAmount());
            }else{
                player.changeHp(item.getAmount());
            }
        }

        for(Character character : characters){
            if(character.getWeapon() instanceof Melee){
                weaponCollision((Melee) character.getWeapon());
            }
        }
    }

    private void removeObjects(){
        if(player.removed()){
            gameOver = true;
        }

        for(int i=projectiles.size()-1; i>=0; i--){
            if(projectiles.get(i).removed()){
                if(projectiles.get(i).isActive() && projectiles.get(i).getHitRadius() > 0){
                    createExplosion(projectiles.get(i));
                }
                projectiles.remove(i);
            }
        }

        for(int i=characters.size()-1; i>=0; i--){
            if(characters.get(i).removed()){
                WeaponType weaponType = characters.get(i).getWeapon().getWeaponID().weaponType();
                if (player.inventory.getWeapon(weaponType) == null && weaponType != WeaponType.THROW) {
                    items.add(new WeaponItem(characters.get(i).getCenterX(), characters.get(i).getCenterY(), weaponType));
                }
                characters.remove(i);
            }
        }

        for(int i=effects.size()-1; i>=0; i--){
            if(effects.get(i).removed()){
                effects.remove(i);
            }
        }

        for(int i=items.size()-1; i>=0; i--){
            if(items.get(i).removed()){
                items.remove(i);
            }
        }
    }

    /*
     * Generate a starting map using pixels from an image
     *
     */
    public void generateMap(int level){
        gameOver = false;
        gameElements.clear();
        projectiles.clear();
        characters.clear();
        items.clear();
        effects.clear();
        portals.clear();
        HashMap<Integer, Tile> p = new HashMap<>();
        BufferedImage levelMap = ImageLoader.loadImage("media/level"+level+".png");
        BufferedImage itemMap = ImageLoader.loadImage("media/level"+level+"items.png");
        tileMap = new Tile[levelMap.getWidth()][levelMap.getHeight()];
        for(int y=0; y< levelMap.getHeight(); y++){
            for(int x=0; x<levelMap.getWidth(); x++){
                tileMap[x][y] = new Tile(x, y);
                int levelColor = levelMap.getRGB(x, y);
                int levelRed = (levelColor & 0xff0000) >> 16;
                int levelGreen = (levelColor & 0xff00) >> 8;
                int levelBlue = levelColor & 0xff;

                int itemColor = itemMap.getRGB(x, y);
                int itemRed = (itemColor & 0xff0000) >> 16;
                int itemGreen = (itemColor & 0xff00) >> 8;
                int itemBlue = itemColor & 0xff;

                if(levelRed == levelGreen && levelGreen == levelBlue){
                    switch (levelRed){
                        case 0 -> {
                            TileObject newWall = new TileObject(tileMap[x][y], ImageLoader.getAnimation("wall"));
                            tileMap[x][y].setOccupied(true);
                            gameElements.add(newWall);
                        }
                        case 1 -> {
                            endPortal = new TileObject(tileMap[x][y], ImageLoader.getAnimation("end_portal"));
                            gameElements.add(endPortal);
                        }
                    }
                }
                else if(levelRed == 255 && levelGreen < 128 && levelBlue < 128){
                    Enemy newEnemy = new Enemy(tileMap[x][y],
                            WeaponFactory.createWeapon(getWeaponType((itemGreen&0b1110000)>>4, itemGreen&0b111),
                                    Team.ENEMY),
                            getProjectileType((itemGreen&0b1110000)>>4, (itemBlue&0b1110000)>>4),
                            this);
                    characters.add(newEnemy);
                }
                else if(levelGreen == 255 && levelRed < 128 && levelBlue < 128){
                    if(player == null) {
                        player = new Player(tileMap[x][y], this);
                    }else{
                        player.reset();
                        player.setLocation(tileMap[x][y]);
                    }
                    characters.add(player);
                }
                else if(levelBlue == 255 && levelRed < 128 && levelGreen < 128){
                    int amount = itemBlue&0xf;
                    if(itemRed == 255){
                        if(amount == 0){
                            items.add(new WeaponItem(tileMap[x][y].getX()+GameConstants.tileSize/2,
                                    tileMap[x][y].getY()+GameConstants.tileSize/2,
                                    getWeaponType((itemGreen&0b1110000)>>4, itemGreen&0b111)));
                        }else{
                            items.add(new ProjectileItem(tileMap[x][y].getX()+GameConstants.tileSize/2,
                                    tileMap[x][y].getY()+GameConstants.tileSize/2,
                                    getProjectileType((itemGreen&0b1110000)>>4, (itemBlue&0b1110000)>>4),
                                    amount));
                        }
                    }else if(itemGreen == 255){
                        int type = itemRed&0b1111111;
                        if(type == 2){
                            items.add(new HpItem(tileMap[x][y].getX()+GameConstants.tileSize/2,
                                    tileMap[x][y].getY()+GameConstants.tileSize/2,
                                    amount));
                        }
                    }
                }
            }
        }
    }

    private WeaponType getWeaponType(int weaponClassCode, int weaponTypeCode){
        WeaponType weaponType = null;
        System.out.println(weaponClassCode+" "+weaponTypeCode);
        switch (weaponClassCode){
            case 0 -> {
                weaponType = WeaponType.THROW;
            }
            case 1 -> {
                switch (weaponTypeCode){
                    case 1 -> weaponType = WeaponType.SWORD;
                    case 2 -> weaponType = WeaponType.GREATSWORD;
                }
            }
            case 2 -> {
                switch (weaponTypeCode){
                    case 1 -> weaponType = WeaponType.SPEAR;
                }
            }
            case 3 -> {
                switch (weaponTypeCode){
                    case 1 -> weaponType = WeaponType.BOW;
                }
            }
            case 4 -> {
                switch (weaponTypeCode){
                    case 1 -> weaponType = WeaponType.GUN;
                }
            }
            default -> weaponType = WeaponType.THROW;
        }
        return weaponType;
    }

    private ProjectileType getProjectileType(int weaponClassCode, int projectileTypeCode){
        ProjectileType projectileType = null;
        System.out.println(weaponClassCode+" "+projectileTypeCode);
        switch (weaponClassCode){
            case 0 -> {
                switch (projectileTypeCode){
                    case 1 -> projectileType = ProjectileType.BOMB;
                    case 5 -> projectileType = ProjectileType.THROWING_SPEAR;
                }
            }
            case 3 -> {
                switch (projectileTypeCode){
                    case 1 -> projectileType = ProjectileType.ARROW;
                    case 4 -> projectileType = ProjectileType.ELECTRIC_ARROW;
                    case 6 -> projectileType = ProjectileType.BOMB_ARROW;
                }
            }
            case 4 -> {
                switch (projectileTypeCode){
                    case 1 -> projectileType = ProjectileType.BULLET;
                }
            }
        }
        return projectileType;
    }

    static String weaponCode(int c, int w, int p, int s){
        return (c<<4)+(w)+" "+(p<<4)+s;
    }

    public Point getMouse() {
        return mouse;
    }

    public void setMouse(Point mouse) {
        this.mouse = mouse;
    }

    // Check projectile collisions
    private void projectileCollision(Projectile projectile){
        for (TileObject collider : gameElements) {
            if (collider.getRect().contains(projectile.getX(), projectile.getY())) {
                projectile.remove();
            }
        }
        for(Character character : characters){
            if (projectile.getTeam() != character.getWeapon().getTeam() &&
                    character.getRect().contains(projectile.getX(), projectile.getY())) {
                projectile.remove();
                character.changeHp(-projectile.getDamage());
                character.setStun(projectile.getStun());
            }
        }
    }

    private void createExplosion(Projectile projectile){
        for(Character character : characters){
            if(inLineOfSight(character.getCenter(), new Point(projectile.getX(), projectile.getY()), projectile.getHitRadius())){
                character.changeHp(-projectile.getExplosionDamage());
            }
        }
        effects.add(new VisualEffect(projectile.getX(), projectile.getY(), ImageLoader.getAnimation("explosion")));
    }

    // Check if a weapon hit a character
    private void weaponCollision(Melee weapon){
        if(weapon.isActive()) {
            Line2D.Double weaponLine = new Line2D.Double(weapon.getX(), weapon.getY(),
                    weapon.getX() + weapon.getCurrentImage().getWidth() * Math.cos(weapon.getAngle()),
                    weapon.getY() + weapon.getCurrentImage().getWidth() * Math.sin(weapon.getAngle()));
            for (Character character : characters) {
                if (weapon.getTeam() != character.getWeapon().getTeam() &&
                        weaponLine.intersects(character.getRect()) &&
                        inLineOfSight(weaponLine.getP1(), character.getCenter()) &&
                        weapon.addTarget(character)) {
                    character.changeHp(-weapon.getDamage());
                }
            }
        }
    }

    private boolean inLineOfSight(Point2D p1, Point2D p2) {
        Line2D line = new Line2D.Double(p1.getX(), p1.getY(), p2.getX(), p2.getY());
        for (TileObject collider : gameElements) {
            if (line.intersects(collider.getRect())) {
                return false;
            }
        }
        return true;
    }

    // To be refactored to not use character references
    private boolean inLineOfSight(Point p1, Point p2, double sightRange){
        return inLineOfSight(p1, p2) && Math.hypot(p1.x - p2.x, p1.y - p2.y)<sightRange;
    }

    public Point findClosestTarget(Point center, double sightRange, Team team){
        Character closest = null;
        if(team == Team.PLAYER){
            for(Character enemy : characters){
                // Distance to the enemy
                double t = Math.hypot(enemy.getX()-player.getX(), enemy.getY()-player.getY());
                // If there is no enemy yet or the new enemy is closer and in sight range
                if((closest==null||t < Math.hypot(closest.getX()-player.getX(), closest.getY()-player.getY())) &&
                        inLineOfSight(center, enemy.getCenter(), sightRange) && enemy.getWeapon().getTeam()!=Team.PLAYER){
                    closest = enemy;
                }
            }
        }else if(team == Team.ENEMY){
            if(inLineOfSight(center, player.getCenter(), sightRange)){
                closest = player;
            }
        }
        if(closest == null){
            return null;
        }
        return new Point(closest.getCenterX(), closest.getCenterY());
    }

    // Return the end of a portal if applicable
    public Tile checkPortals(Tile tile){
        for(Portal portal : portals){
            if(tile.equals(portal.getStart())){
                return portal.getEnd();
            }
        }
        return null;
    }

    // Send collected items to player to be collected
    public Item checkItems(Rectangle playerRectangle){
        for(Item item : items){
            if(playerRectangle.contains(item.getX(), item.getY())){
                item.remove();
                return item;
            }
        }
        return null;
    }

    public boolean isGameOver() {
        return gameOver;
    }
}
