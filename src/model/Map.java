package model;

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
    public ArrayList<Character> characters;
    public ArrayList<TileObject> gameElements;
    public ArrayList<Projectile> projectiles;
    public ArrayList<Item> items;
    public ArrayList<VisualEffect> effects;
    public ArrayList<Portal> portals;
    private Point mouse;

    private Map(){
        gameElements = new ArrayList<>();
        projectiles = new ArrayList<>();
        characters = new ArrayList<>();
        items = new ArrayList<>();
        effects = new ArrayList<>();
        portals = new ArrayList<>();
        mouse = new Point(0, 0);
        new WeaponFactory(this);
        ImageLoader.loadResources();
    }

    public Map(String[] levelMap){
        this();
        // Generate map
        generateMap(levelMap);
    }

    public Map(String levelMap){
        this();
        // Generate map
        generateMap(levelMap);
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

        for(Character character : characters){
            if(character.getWeapon() instanceof Melee){
                weaponCollision((Melee) character.getWeapon());
            }
        }
    }

    private void removeObjects(){
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
                if(player.inventory.getWeapon(weaponType)==null && weaponType!=WeaponType.NONE) {
                    items.add(new WeaponItem(characters.get(i).getX(), characters.get(i).getY(), weaponType));
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
     * Generate a starting map using a string array
     *
     */
    private void generateMap(String[] levelMap){
//        for(int y=0; y< levelMap.length; y++){
//            for(int x=0; x<levelMap[0].length(); x++){
//                tileMap[x][y] = new Tile(x, y);
//                switch (levelMap[y].charAt(x)){
//                    case 'P' -> {
//                        player = new Player(tileMap[x][y], this);
//                        tileMap[x][y].collider = player;
//                    }
//                    case 'E' -> {
//                        BowEnemy newEnemy = new BowEnemy(tileMap[x][y],this, ProjectileType.ARROW);
//                        tileMap[x][y].collider = newEnemy;
//                        enemies.add(newEnemy);
//                    }
//                    case 'F' -> {
//                        BowEnemy newEnemy = new BowEnemy(tileMap[x][y], this, ProjectileType.BOMB_ARROW);
//                        tileMap[x][y].collider = newEnemy;
//                        enemies.add(newEnemy);
//                    }
//                    case 'W' -> {
//                        Wall newWall = new Wall(tileMap[x][y]);
//                        tileMap[x][y].collider = newWall;
//                        gameElements.add(newWall);
//                    }
//                    case 'A' -> {
//                        tileMap[x][y].addItem(new ArrowItem());
//                    }
//                    case 'H' -> {
//                        tileMap[x][y].addItem(new HpItem());
//                    }
//                }
//            }
//        }
    }

    /*
     * Generate a starting map using pixels from an image
     *
     */
    private void generateMap(String levelMap){
        HashMap<Integer, Tile> p = new HashMap<>();
        BufferedImage level = ImageLoader.loadImage(levelMap);
        tileMap = new Tile[level.getWidth()][level.getHeight()];
        for(int y=0; y< level.getHeight(); y++){
            for(int x=0; x<level.getWidth(); x++){
                tileMap[x][y] = new Tile(x, y);
                switch (level.getRGB(x, y)){
                    case -14503604 -> {
                        player = new Player(tileMap[x][y], this);
                        tileMap[x][y].collider = player;
                        characters.add(player);
                    }
                    // rgb(245, 0, 0)
                    case -720896 -> {
                        ShooterEnemy newEnemy = new ShooterEnemy(tileMap[x][y],
                                WeaponFactory.createWeapon(WeaponType.BOW, Team.ENEMY),
                                ProjectileType.ARROW,
                                this);
                        tileMap[x][y].collider = newEnemy;
                        characters.add(newEnemy);
                    }
                    // rgb(241, 0, 0)
                    case -983040 -> {
                        ShooterEnemy newEnemy = new ShooterEnemy(tileMap[x][y],
                                WeaponFactory.createWeapon(WeaponType.BOW, Team.ENEMY),
                                ProjectileType.BOMB_ARROW,
                                this);
                        tileMap[x][y].collider = newEnemy;
                        characters.add(newEnemy);
                    }
                    // rgb(235, 0, 0)
                    case -1376256 -> {
                        ShooterEnemy newEnemy = new ShooterEnemy(tileMap[x][y],
                                WeaponFactory.createWeapon(WeaponType.NONE, Team.ENEMY),
                                ProjectileType.BOMB,
                                this);
                        tileMap[x][y].collider = newEnemy;
                        characters.add(newEnemy);
                    }
                    // rgb(225, 0, 0)
                    case -2031616 -> {
                        ShooterEnemy newEnemy = new ShooterEnemy(tileMap[x][y],
                                WeaponFactory.createWeapon(WeaponType.GUN, Team.ENEMY),
                                ProjectileType.BULLET,
                                this);
                        tileMap[x][y].collider = newEnemy;
                        characters.add(newEnemy);
                    }
                    // rgb(0, 0, 0)
                    case -16777216 -> {
                        Wall newWall = new Wall(tileMap[x][y]);
                        tileMap[x][y].collider = newWall;
                        gameElements.add(newWall);
                    }
                    // rgb(10, 10, 10)
                    case -16119286 -> {
                        items.add(new ProjectileItem(tileMap[x][y].getX()+GameConstants.tileSize/2,
                                tileMap[x][y].getY()+GameConstants.tileSize/2,
                                ProjectileType.ARROW, 10));
                    }
                    // rgb(33, 33, 33)
                    case -14606047 -> {
                        items.add(new ProjectileItem(tileMap[x][y].getX()+GameConstants.tileSize/2,
                                tileMap[x][y].getY()+GameConstants.tileSize/2,
                                ProjectileType.ELECTRIC_ARROW, 3));
                    }
                    // rgb(43, 43, 43)
                    case -0 -> {
                        items.add(new ProjectileItem(tileMap[x][y].getX()+GameConstants.tileSize/2,
                                tileMap[x][y].getY()+GameConstants.tileSize/2,
                                ProjectileType.BOMB_ARROW, 3));
                    }
                    // rgb(105, 105, 105)
                    case -9868951 -> {
                        items.add(new ProjectileItem(tileMap[x][y].getX()+GameConstants.tileSize/2,
                                tileMap[x][y].getY()+GameConstants.tileSize/2,
                                ProjectileType.BULLET, 5));
                    }
                    // rgb(0, 245, 245)
                    case -16714251 -> {
                        items.add(new WeaponItem(tileMap[x][y].getX()+GameConstants.tileSize/2,
                                tileMap[x][y].getY()+GameConstants.tileSize/2,
                                WeaponType.BOW));
                    }
                    // rgb(0, 235, 235)
                    case -16716821 -> {
                        items.add(new ProjectileItem(tileMap[x][y].getX()+GameConstants.tileSize/2,
                                tileMap[x][y].getY()+GameConstants.tileSize/2,
                                ProjectileType.BOMB, 1));
                    }
                    // rgb(254, 0, 254)
                    case -130818 -> {
                        items.add(new HpItem(tileMap[x][y].getX()+GameConstants.tileSize/2,
                                tileMap[x][y].getY()+GameConstants.tileSize/2,
                                1));
                    }
                    // rgb(250, 0, 250)
                    case -392966 -> {
                        items.add(new HpItem(tileMap[x][y].getX()+GameConstants.tileSize/2,
                                tileMap[x][y].getY()+GameConstants.tileSize/2,
                                5));
                    }
                    case -6075996 -> {
                        if(p.containsKey(-6075996)){
                            portals.add(new Portal(tileMap[x][y], p.get(-6075996), new Color(-6075996)));
                        }else{
                            p.put(-6075996, tileMap[x][y]);
                        }
                    }
                    case -6694422 -> {
//                        items.add(new ProjectileItem(tileMap[x][y].getX()+GameConstants.tileSize/2,
//                                tileMap[x][y].getY()+GameConstants.tileSize/2,
//                                ProjectileType.BOMB_ARROW, 1));
                    }
                    case -1 -> {

                    }
                    default -> {
                        System.out.println(level.getRGB(x, y)+" "+x+" "+y);
                    }
                }
            }
        }
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
            if(character.distance(projectile.getX(), projectile.getY()) <= projectile.getHitRadius()){
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
}
