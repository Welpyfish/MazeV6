package model;

import model.weapon.Weapon;

import java.awt.*;

public class Character extends TileObject {
    private Weapon weapon;
    private int hp;
    private double sightRange;
    private Tile nextTile;
    protected Map map;
    private double vx;
    private double vy;

    // Special effect timers
    private int stun;

    public Character(Tile tile, Map map) {
        super(tile);
        this.map = map;
        nextTile = tile;
        tile.setOccupied(true);
    }

    public Character(Tile tile, Weapon weapon, Animation animation){
        super(tile, animation);
        nextTile = tile;
        tile.setOccupied(true);
        setWeapon(weapon);
    }

    public void update(){
        if(stun == 0) {
            // Move according to velocity
            vx = (nextTile.getGridx() - tile.getGridx()) * 3;
            vy = -(nextTile.getGridy() - tile.getGridy()) * 3;
            changeX(vx);
            changeY(-vy);
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
    }

    protected void updateMovement(int x, int y){
        // When at a tile, start moving towards the next target tile
        if(atTile()){
            System.out.println("at tile");
            if(!map.tileMap[tile.getGridx()+x][tile.getGridy()-y].isOccupied()){
                nextTile = map.tileMap[tile.getGridx()+x][tile.getGridy()-y];
                nextTile.setOccupied(true);
                System.out.println("set target");
            }
        }
    }

    protected void updateWeapon(){
        getWeapon().update(getCenterX(), getCenterY());
    }

    protected boolean atTile(){
        return (getX()== nextTile.getX() && getY()== nextTile.getY());
    }

    public double getVx() {
        return vx;
    }

    public double getVy() {
        return vy;
    }

    public int getHp() {
        return hp;
    }

    protected void setHp(int hp){
        this.hp = hp;
    }

    public void changeHp(int c){
        hp+=c;
        if(hp<=0){
            remove();
            hp = 0;
        }
    }

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
