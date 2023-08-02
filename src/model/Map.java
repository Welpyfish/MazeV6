/*
 * Final Project
 * Maze
 * William Zhou
 * 2023-06-19
 * ICS4UI-4
 *
 * The Map class is a container for all game objects
 */

package model;

import model.item.*;
import model.weapon.*;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Map {
    public Tile[][] tileMap;
    public Player player;
    private TileObject endPortal;

    public ArrayList<Sprite> sprites;

    public ArrayList<Character> characters;
    public ArrayList<Sprite> gameElements;
    public ArrayList<Projectile> projectiles;
    public ArrayList<Item> items;
    public ArrayList<VisualEffect> effects;

    private ArrayList<Spike> spikes;
    private ConcurrentHashMap<Integer, Sprite> doors;

    private Point mouse;
    private boolean gameOver;

    public Map(){
        sprites = new ArrayList<>();

        gameElements = new ArrayList<>();
        projectiles = new ArrayList<>();
        characters = new ArrayList<>();
        items = new ArrayList<>();
        effects = new ArrayList<>();
        mouse = new Point(0, 0);
        new WeaponFactory(this);

        spikes = new ArrayList<>();
        doors = new ConcurrentHashMap<>();
    }

    // Update all objects for 1 frame
    public void update(){
        updateObjects();
        manageInteractions();
        removeObjects();
    }

    // Update all objects
    private void updateObjects(){
        for(Projectile projectile : projectiles){
            projectile.update();
        }

        for(Character character : characters){
            character.update();
            updateSpriteLocation(character);
        }

        for(VisualEffect effect : effects){
            effect.update();
        }

        for(Sprite element : gameElements){
            element.update();
        }

        for(Item item : items){
            item.update();
        }
    }

    private void updateSpriteLocation(Character sprite){
        if(sprite.hasCollision() && sprite.getStun() == 0) {
            sprite.updateX();
            for(Sprite collider : sprites){
                if(sprite!=collider && collider.hasCollision() && collider.intersects(sprite)){
                    manageCharacterCollisions(sprite, collider);
                    sprite.collide(collider, true);
                }
            }
            sprite.updateY();
            for(Sprite collider : sprites){
                if(sprite!=collider && collider.hasCollision() && collider.intersects(sprite)){
                    manageCharacterCollisions(sprite, collider);
                    sprite.collide(collider, false);
                }
            }
        }
    }

    private void manageCharacterCollisions(Character c, Sprite collider){
        if(collider instanceof Spike){
            c.changeHp(-((Spike)collider).getDamage());
        }
    }

    // Manage interactions between objects such as collision
    private void manageInteractions(){
        for(Projectile projectile : projectiles){
            if(projectile.isActive()) {
                projectileCollision(projectile);
            }
        }

        checkDoors(player);
        // Check end portal
        if(endPortal.getRect().contains(player.getRect())){
            gameOver = true;
        }

        // Collect items
        Item item = checkItems(player.getRect());
        if(item!=null){
            switch (item.getItemType()){
                case WEAPON -> player.addWeapon(item.getWeaponType());
                case PROJECTILE -> player.inventory.changeProjectile(item.getProjectileType(), item.getAmount());
                case HP -> player.changeHp(item.getAmount());
                case COIN -> player.inventory.changeCoins(item.getAmount());
                case KEY -> player.inventory.getKeys().add(item.getAmount());
            }
        }

        // Check melee weapon collision
        for(Character character : characters){
            if(character.getWeapon() instanceof Melee){
                weaponCollision((Melee) character.getWeapon());
            }
            for(Spike spike : spikes){
                if(spike.intersects(character)){
                    character.changeHp(-spike.getDamage());
                }
            }
        }

        for(Sprite sprite : gameElements){
            if(sprite.getGameObjectType() == GameObjectType.MINE){
                checkMine(sprite);
            }
        }
    }

    // Remove objects that should not exist anymore
    private void removeObjects(){
        for(int i=gameElements.size()-1; i>=0; i--){
            if(gameElements.get(i).removed()){
                if(gameElements.get(i).getGameObjectType() == GameObjectType.MINE){
                    Projectile explosion = WeaponFactory.createProjectile(ProjectileType.BOMB, Team.ENEMY);
                    explosion.updateWhenLoaded(gameElements.get(i).getCenterX(), gameElements.get(i).getCenterY(), 0);
                    createExplosion(explosion);
                    explosion.remove();
                }
                gameElements.remove(i);
            }
        }

        // End game if player died
        if(player.removed()){
            gameOver = true;
        }

        for(int i=projectiles.size()-1; i>=0; i--){
            if(projectiles.get(i).removed()){
                // If activated (launched), projectiles will explode
                if(projectiles.get(i).isActive() && projectiles.get(i).getHitRadius() > 0){
                    createExplosion(projectiles.get(i));
                }
                projectiles.remove(i);
            }
        }

        for(int i=characters.size()-1; i>=0; i--){
            // Enemies drop weapons if the player doesn't have the weapon yet
            if(characters.get(i).removed() && characters.get(i) instanceof Enemy){
                Enemy enemy = (Enemy) characters.get(i);
                WeaponType weaponType =enemy.getWeapon().getWeaponID().weaponType();
                if (player.inventory.getWeapon(weaponType) == null && weaponType != WeaponType.THROW && enemy.isDropWeapon()) {
                    items.add(new Item(enemy.getCenterX(), enemy.getCenterY(), ItemType.WEAPON,
                            ImageLoader.getAnimation(weaponType.toString())).weaponType(weaponType));
                }
                items.add(new Item(enemy.getCenterX(), enemy.getCenterY(), ItemType.COIN,
                        ImageLoader.getAnimation("coin"+enemy.getCoinValue())).amount(enemy.getCoinValue()));
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

        for(int i=sprites.size()-1; i>=0; i--){
            if(sprites.get(i).removed()){
                sprites.remove(i);
            }
        }
    }

    // Generate a map using image pixels
    public void generateMap(int level){
        // Reset collections
        sprites.clear();

        gameOver = false;
        gameElements.clear();
        projectiles.clear();
        characters.clear();
        items.clear();
        effects.clear();
        spikes.clear();
        doors.clear();
        //portals.clear();
        // Load the image for level map which includes characters and game elements (walls)
        BufferedImage levelMap = ImageLoader.loadImage("media/level"+level+".png");
        // Load the second layer that contains information about items and weapons
        BufferedImage itemMap = ImageLoader.loadImage("media/level"+level+"items.png");
        tileMap = new Tile[levelMap.getWidth()][levelMap.getHeight()];
        for(int y=0; y< levelMap.getHeight(); y++){
            for(int x=0; x<levelMap.getWidth(); x++){
                tileMap[x][y] = new Tile(x, y);
                // Decode the pixels
                int levelColor = levelMap.getRGB(x, y);
                int levelRed = (levelColor & 0xff0000) >> 16;
                int levelGreen = (levelColor & 0xff00) >> 8;
                int levelBlue = levelColor & 0xff;

                int itemColor = itemMap.getRGB(x, y);
                int itemRed = (itemColor & 0xff0000) >> 16;
                int itemGreen = (itemColor & 0xff00) >> 8;
                int itemBlue = itemColor & 0xff;

                // Black creates game elements
                if(levelRed == levelGreen && levelGreen == levelBlue){
                    switch (levelRed){
                        case 0 -> {
                            TileObject newWall;
                            if((itemBlue & 0b1) == 0){
                                newWall = new TileObject(tileMap[x][y], true, GameObjectType.WALL, ImageLoader.getAnimation("wall"));
                            }else{
                                newWall = new TileObject(tileMap[x][y], false, GameObjectType.FAKE_WALL, ImageLoader.getAnimation("fake_wall"));
                            }
                            //tileMap[x][y].setOccupied(true);
                            gameElements.add(newWall);
                            sprites.add(newWall);
                        }
                        case 1 -> {
                            endPortal = new TileObject(tileMap[x][y], false, GameObjectType.END_PORTAL, ImageLoader.getAnimation("end_portal"));
                            endPortal.getAnimation().play();
                            gameElements.add(endPortal);
                            sprites.add(endPortal);
                        }
                        case 2 -> {
                            TileObject newDoor = new TileObject(tileMap[x][y], true, GameObjectType.DOOR, ImageLoader.getAnimation("door"));
                            //tileMap[x][y].setOccupied(true);
                            gameElements.add(newDoor);
                            sprites.add(newDoor);
                            doors.put(itemBlue&0xf, newDoor);
                        }
                        case 3 -> {
                            Spike newSpike;
                            double angle = 0;
                            switch (itemBlue & 0b1111){
                                case 1 -> angle = 0;
                                case 2 -> angle = Math.PI/2;
                                case 3 -> angle = Math.PI;
                                case 4 -> angle = 3*Math.PI/2;
                                case 5 -> angle = -1;
                            }
                            switch (itemRed&0xf){
                                case 0 -> newSpike = new Spike(tileMap[x][y].getX(), tileMap[x][y].getY(), 36, 36, false, 0, angle,
                                        GameObjectType.WALL, ImageLoader.getAnimation("fake_spike"));
                                case 1 -> newSpike = new Spike(tileMap[x][y].getX(), tileMap[x][y].getY(), 36, 36, true, 1, angle,
                                        GameObjectType.WALL, ImageLoader.getAnimation("spike"));
                                case 2 -> newSpike = new Spike(tileMap[x][y].getX(), tileMap[x][y].getY(), 36, 36, false, 2, angle,
                                        GameObjectType.WALL, ImageLoader.getAnimation("fire"));
                                default -> newSpike = new Spike(tileMap[x][y].getX(), tileMap[x][y].getY(), 36, 36, true, 1, angle,
                                        GameObjectType.WALL, ImageLoader.getAnimation("spike"));
                            }
                            gameElements.add(newSpike);
                            spikes.add(newSpike);
                            sprites.add(newSpike);
                        }
                        case 5 -> {
                            double angle = 0;
                            switch (itemBlue & 0b1111){
                                case 1 -> angle = 0;
                                case 2 -> angle = Math.PI/2;
                                case 3 -> angle = Math.PI;
                                case 4 -> angle = 3*Math.PI/2;
                                case 5 -> angle = -1;
                            }
                            Turret newTurret = new Turret(tileMap[x][y],
                                    WeaponFactory.createWeapon(WeaponType.TURRET, Team.ENEMY),
                                    getProjectileType((itemGreen & 0b1110000) >> 4, (itemBlue & 0b1110000) >> 4),
                                    angle, ImageLoader.getAnimation("turret_base"));
                            gameElements.add(newTurret);
                            sprites.add(newTurret);
                        }
                        case 6 -> {
                            TileObject newMine = new TileObject(tileMap[x][y].getIntX(),
                                    tileMap[x][y].getIntY(), false, GameObjectType.MINE, ImageLoader.getAnimation("mine"));
                            gameElements.add(newMine);
                            sprites.add(newMine);
                        }
                    }
                }
                // Red creates enemies
                else if(levelRed == 255 && levelGreen < 128 && levelBlue < 128){
                    Weapon weapon = WeaponFactory.createWeapon(getWeaponType((itemGreen & 0b1110000) >> 4, itemGreen & 0b111),
                            Team.ENEMY);
                    ProjectileType projectileType = getProjectileType((itemGreen & 0b1110000) >> 4, (itemBlue & 0b1110000) >> 4);
                    if(levelBlue == 4){

                    }else {
                        // The enemy weapon is found from the item map
                        Enemy newEnemy = new Enemy(tileMap[x][y], weapon, projectileType, ImageLoader.getAnimation("enemy"),
                                this);
                        characters.add(newEnemy);
                        sprites.add(newEnemy);
                    }
                }
                // Green creates player
                else if(levelGreen == 255 && levelRed < 128 && levelBlue < 128){
                    if(player == null) {
                        player = new Player(tileMap[x][y], ImageLoader.getAnimation("player"), this);
                    }else{
                        player.reset();
                        player.setLocation(tileMap[x][y]);
                    }
                    characters.add(player);
                    sprites.add(player);
                }
                // Blue creates items
                else if(levelBlue == 255 && levelRed < 128 && levelGreen < 128){
                    int amount = itemBlue&0xf;
                    // Red items are weapons
                    if(itemRed == 255){
                        if(amount == 0){
                            WeaponType weaponType = getWeaponType((itemGreen&0b1110000)>>4, itemGreen&0b111);
                            Item newItem = new Item(tileMap[x][y].getIntX()+GameConstants.tileSize/2,
                                    tileMap[x][y].getIntY()+GameConstants.tileSize/2,
                                    ItemType.WEAPON,
                                    ImageLoader.getAnimation(weaponType.toString())).weaponType(weaponType);
                            items.add(newItem);
                            sprites.add(newItem);
                        }else{
                            ProjectileType projectileType = getProjectileType((itemGreen&0b1110000)>>4, (itemBlue&0b1110000)>>4);
                            Item newItem = new Item(tileMap[x][y].getIntX()+GameConstants.tileSize/2,
                                    tileMap[x][y].getIntY()+GameConstants.tileSize/2,
                                    ItemType.PROJECTILE,
                                    ImageLoader.getAnimation(projectileType.toString())).amount(amount).projectileType(projectileType);
                            items.add(newItem);
                            sprites.add(newItem);
                        }
                    }
                    // Green items are hp
                    else if(itemGreen == 255){
                        int type = itemRed&0b1111111;
                        if(type == 2){
                            Item newItem = new Item(tileMap[x][y].getIntX()+GameConstants.tileSize/2,
                                    tileMap[x][y].getIntY()+GameConstants.tileSize/2,
                                    ItemType.HP, ImageLoader.getAnimation("heart"+amount)).amount(amount);
                            items.add(newItem);
                            sprites.add(newItem);
                        }
                        else if(type == 3){
                            Item newItem = new Item(tileMap[x][y].getIntX()+GameConstants.tileSize/2,
                                    tileMap[x][y].getIntY()+GameConstants.tileSize/2,
                                    ItemType.COIN, ImageLoader.getAnimation("coin"+amount)).amount(amount);
                            items.add(newItem);
                            sprites.add(newItem);
                        }
                        else if(type == 4){
                            // start at 2 (1 is end key)
                            String path = "key";
                            if(amount == 1){
                                path = "endkey";
                            }else if(amount > 100){
                                path = "cagekey";
                            }
                            Item newItem = new Item(tileMap[x][y].getIntX()+GameConstants.tileSize/2,
                                    tileMap[x][y].getIntY()+GameConstants.tileSize/2,
                                    ItemType.KEY, ImageLoader.getAnimation(path)).amount(amount);
                            items.add(newItem);
                            sprites.add(newItem);
                        }
                    }
                }
            }
        }
    }

    // Return the weapon type that corresponds to the code from a pixel
    private WeaponType getWeaponType(int weaponClassCode, int weaponTypeCode){
        WeaponType weaponType = null;
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

    // Return the projectile type that corresponds to the code from a pixel
    private ProjectileType getProjectileType(int weaponClassCode, int projectileTypeCode){
        ProjectileType projectileType = null;
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

    // Used when designing levels to get the correct rgb colour
//    static String weaponCode(int c, int w, int p, int s){
//        return (c<<4)+(w)+" "+(p<<4)+s;
//    }

    public Point getMouse() {
        return mouse;
    }

    public void setMouse(Point mouse) {
        this.mouse = mouse;
    }

    // Check projectile collisions
    private void projectileCollision(Projectile projectile){
        // Collisions with game elements
        for (Sprite collider : gameElements) {
            if (collider.hasCollision() && collider.intersects(projectile)) {
                projectile.remove();
            }
        }
        // Collisions with characters
        for(Character character : characters){
            if (projectile.getTeam() != character.getWeapon().getTeam() &&
                    character.intersects(projectile)) {
                projectile.remove();
                character.changeHp(-projectile.getDamage());
                character.setStun(projectile.getStun());
            }
        }
    }

    // Create an explosion from a projectile
    private void createExplosion(Projectile projectile){
//        projectile.setVx(-projectile.getVx());
//        projectile.setVy(-projectile.getVy());
//        projectile.updateX();
//        projectile.updateY();
//        projectile.setCollision(true);
//        projectile.setVx(-projectile.getVx());
//        projectile.setVy(-projectile.getVy());
//        updateSpriteLocation(projectile);
        for(Character character : characters){
            if(inLineOfSight(character.getCenter(), new Point(projectile.getCenterX(), projectile.getCenterY()), projectile.getHitRadius())){
                character.changeHp(-projectile.getExplosionDamage());
            }
        }
        VisualEffect newEffect = new VisualEffect(projectile.getCenterX(), projectile.getCenterY(), ImageLoader.getAnimation("explosion"));
        effects.add(newEffect);
        sprites.add(newEffect);
    }

    // Check if a weapon hit a character
    private void weaponCollision(Melee weapon){
        if(weapon.isActive()) {
            // The melee weapon is represented by a line
            Line2D.Double weaponLine = new Line2D.Double(weapon.getIntX(), weapon.getIntY(),
                    weapon.getIntX() + weapon.getCurrentImage().getWidth() * Math.cos(weapon.getAngle()),
                    weapon.getIntY() + weapon.getCurrentImage().getWidth() * Math.sin(weapon.getAngle()));
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

    private void checkDoors(Player player){
        if(player.isAttacking()){
            for(Integer id : doors.keySet()){
                Sprite door = doors.get(id);
                if(Math.abs(door.getIntX()-player.getIntX())+Math.abs(door.getIntY()-player.getIntY()) <= GameConstants.tileSize
                        && player.inventory.getKeys().contains(id)){
                    player.inventory.getKeys().remove(id);
                    door.setCollision(false);
                    door.getAnimation().play();
                    door.getAnimation().update();
                    door.getAnimation().pause();
                }
            }
        }
    }

    // Check if 2 points have no game elements between them
    private boolean inLineOfSight(Point2D p1, Point2D p2) {
        Line2D line = new Line2D.Double(p1.getX(), p1.getY(), p2.getX(), p2.getY());
        for (Sprite collider : gameElements) {
            if (collider.hasCollision() && line.intersects(collider.getX(), collider.getY(), collider.getWidth(), collider.getHeight())) {
                return false;
            }
        }
        return true;
    }

    // Line of sight with a range limitation
    private boolean inLineOfSight(Point p1, Point p2, double sightRange){
        return inLineOfSight(p1, p2) && Math.hypot(p1.x - p2.x, p1.y - p2.y)<sightRange;
    }

    // Find the closest target for a character to auto aim at
    public Point findClosestTarget(Point center, double sightRange, Team team){
        Character closest = null;
        if(team == Team.PLAYER){
            for(Character enemy : characters){
                // Distance to the enemy
                double t = Math.hypot(enemy.getIntX()-player.getIntX(), enemy.getIntY()-player.getIntY());
                // If there is no enemy yet or the new enemy is closer and in sight range
                if((closest==null||t < Math.hypot(closest.getIntX()-player.getIntX(), closest.getIntY()-player.getIntY())) &&
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

    // Check item and player interaction
    public Item checkItems(Rectangle playerRectangle){
        for(Item item : items){
            if(player.intersects(item)){
                item.remove();
                return item;
            }
        }
        return null;
    }

    private void checkMine(Sprite mine){
        if(player.intersects(mine)){
            mine.remove();
        }
        for(Projectile p : projectiles){
            if(p.removed() && p.isActive() && mine.intersects(p)){
                mine.remove();
            }
        }
    }

    public boolean isGameOver() {
        return gameOver;
    }
}
