package model;

import model.weapon.Weapon;

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
    }

    public void update(){
        if(stun == 0) {
            // Move according to velocity
            vx = (nextTile.getGridx() - tile.getGridx()) * 3;
            vy = -(nextTile.getGridy() - tile.getGridy()) * 3;
            changeX(vx);
            changeY(-vy);
            if (atTile()) {
                tile.collider = null;
                tile = nextTile;
            }
            // Update weapon
            updateWeapon();
        }else{
            System.out.println(stun);
            stun--;
        }
    }

    protected void updateMovement(int x, int y){
        // When at a tile, start moving towards the next target tile
        if(atTile()){
            if(map.tileMap[tile.getGridx()+x][tile.getGridy()-y].collider==null){
                nextTile.collider=null;
                nextTile = map.tileMap[tile.getGridx()+x][tile.getGridy()-y];
                nextTile.collider=this;
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
        tile.collider = null;
        nextTile.collider = null;
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
