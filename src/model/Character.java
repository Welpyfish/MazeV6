/*
 * Final Project
 * Maze
 * William Zhou
 * 2023-06-19
 * ICS4UI-4
 *
 * The Character class describes all characters in the game that can be interacted with
 */

package model;

import model.weapon.Weapon;

public class Character extends TileObject {
    private Weapon weapon;
    private int hp;
    private double sightRange;
    // Target tile for movement
    private Tile nextTile;
    protected Map map;

    protected int movementSpeed;

    // Special effect timers
    private int stun;

    public Character(Tile tile, Weapon weapon, Animation animation){
        super(tile, animation);
        nextTile = tile;
        tile.setOccupied(true);
        this.weapon = weapon;
        this.movementSpeed = 3;
    }

    public Character(Tile tile, Weapon weapon, Animation animation, Map map){
        this(tile, weapon, animation);
        this.map = map;
    }

    // Update
    @Override
    public void update(){
        // Only move and update weapon when not stunned
        if(stun == 0) {
            // Move according to velocity
//            vx = (nextTile.getGridx() - tile.getGridx()) * movementSpeed;
//            vy = -(nextTile.getGridy() - tile.getGridy()) * movementSpeed;
//            changeX(vx);
//            changeY(-vy);
            // Play movement animation when moving
            if(getVx() == 0 && getVy() == 0){
                getAnimation().pause();
            }else{
                getAnimation().play();
            }
            // Update tile occupation
            if(atTile()){
                tile.setOccupied(false);
                nextTile.setOccupied(true);
                tile = nextTile;
            }
            // Update weapon
            updateWeapon();
        }else{
            stun--;
        }
        super.update();
    }

    // Set target movement
    protected void updateMovement(int x, int y){
        // When at a tile, start moving towards the next target tile
        if(atTile()){
            if(!map.tileMap[tile.getGridx()+x][tile.getGridy()-y].isOccupied()){
                nextTile = map.tileMap[tile.getGridx()+x][tile.getGridy()-y];
                nextTile.setOccupied(true);
            }
        }
    }

    // Update weapon
    protected void updateWeapon(){
        getWeapon().update(getCenterX(), getCenterY());
    }

    protected boolean atTile(){
        return (getX()== nextTile.getX() && getY()== nextTile.getY());
    }

    public int getHp() {
        return hp;
    }

    protected void setHp(int hp){
        this.hp = hp;
    }

    // Remove this character if hp = 0
    public void changeHp(int c){
        hp+=c;
        if(hp<=0){
            remove();
            hp = 0;
        }
    }

    // Remove this character
    @Override
    public void remove(){
        super.remove();
        tile.setOccupied(false);
        nextTile.setOccupied(false);
        weapon.reset();
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
        updateWeapon();
    }

    // Set current location to a tile
    public void setLocation(Tile tile){
        this.tile = tile;
        tile.setOccupied(true);
        setX(tile.getX());
        setY(tile.getY());
        nextTile = tile;
    }

    protected double getSightRange() {
        return sightRange;
    }

    public void setSightRange(double sightRange) {
        this.sightRange = sightRange;
    }

    public void setStun(int stun) {
        this.stun = stun;
    }
}
