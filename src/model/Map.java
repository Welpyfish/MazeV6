package model;

import model.item.*;
import model.weapon.*;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.util.*;

// Hold all game objects
public class Map {
    public Tile[][] tileMap;
    public Player player;
    public ArrayList<Enemy> enemies;
    public ArrayList<TileObject> gameElements;
    public ArrayList<Projectile> projectiles;
    public ArrayList<Item> items;
    public ArrayList<VisualEffect> effects;
    public ArrayList<Portal> portals;
    private Point mouse;

    private Map(){
        gameElements = new ArrayList<>();
        projectiles = new ArrayList<>();
        enemies = new ArrayList<>();
        items = new ArrayList<>();
        effects = new ArrayList<>();
        portals = new ArrayList<>();
        mouse = new Point(0, 0);
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
        for(int i= projectiles.size()-1; i>=0; i--){
            if(projectiles.get(i).isHit()){
                projectiles.remove(i);
            }else {
                projectiles.get(i).update();
            }
        }

        if(player.getHp()<=0){
            System.exit(0);
        }
        player.update();

        for(int i= enemies.size()-1; i>=0; i--){
            if(enemies.get(i).getHp()<=0){
                enemies.get(i).getWeapon().reset();
                enemies.remove(i);
            }else {
                enemies.get(i).update();
            }
        }

        for(int i= effects.size()-1; i>=0; i--){
            if(effects.get(i).finished()){
                effects.remove(i);
            }else {
                effects.get(i).update();
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
                    }
                    // rgb(245, 0, 0)
                    case -720896 -> {
                        ShooterEnemy newEnemy = new ShooterEnemy(tileMap[x][y],
                                new Bow(Team.ENEMY, this),
                                ProjectileType.ARROW,
                                this);
                        tileMap[x][y].collider = newEnemy;
                        enemies.add(newEnemy);
                    }
                    // rgb(241, 0, 0)
                    case -983040 -> {
                        ShooterEnemy newEnemy = new ShooterEnemy(tileMap[x][y],
                                new Bow(Team.ENEMY, this),
                                ProjectileType.BOMB_ARROW,
                                this);
                        tileMap[x][y].collider = newEnemy;
                        enemies.add(newEnemy);
                    }
                    // rgb(235, 0, 0)
                    case -1376256 -> {
                        ShooterEnemy newEnemy = new ShooterEnemy(tileMap[x][y],
                                new NoWeapon(Team.ENEMY, this),
                                ProjectileType.BOMB,
                                this);
                        tileMap[x][y].collider = newEnemy;
                        enemies.add(newEnemy);
                    }
                    // rgb(225, 0, 0)
                    case -2031616 -> {
                        ShooterEnemy newEnemy = new ShooterEnemy(tileMap[x][y],
                                new Gun(Team.ENEMY, this),
                                ProjectileType.BULLET,
                                this);
                        tileMap[x][y].collider = newEnemy;
                        enemies.add(newEnemy);
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
                                ProjectileType.BOMB_ARROW, 3));
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

    // Check if a projectile hit a character
    // May change to not return a tile object and manage projectile destruction in map
    public TileObject projectileCollision(int x, int y, Team team){
        for (TileObject collider : gameElements) {
            if (collider.getRect().contains(x, y)) {
                return collider;
            }
        }
        if(team != Team.ENEMY) {
            for (Enemy enemy : enemies) {
                if (enemy.getRect().contains(x, y)) {
                    return enemy;
                }
            }
        }else if(team != Team.PLAYER){
            if (player.getRect().contains(x, y)) {
                return player;
            }
        }
        return null;
    }

    // To be refactored to not use character references
    public boolean inLineOfSight(Character c1, Character c2, double sightRange){
        Line2D line = new Line2D.Float(c1.getCenterX(), c1.getCenterY(), c2.getCenterX(), c2.getCenterY());
        for(TileObject collider : gameElements){
            if(line.intersects(collider.getRect())){
                return false;
            }
        }
        return Math.hypot(c1.getX()-c2.getX(), c1.getY()-c2.getY())<sightRange;
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
                Item temp = item;
                items.remove(item);
                return temp;
            }
        }
        return null;
    }
}
