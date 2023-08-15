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

import java.awt.*;

public class Character extends Sprite {
    private Weapon weapon;
    private int hp;
    private double sightRange;

    protected Map map;

    protected int movementSpeed;

    // Special effect timers
    private int stun;

    public Character(double x, double y, Weapon weapon, Animation animation){
        super(x, y, Constants.characterSize, Constants.characterSize, true, GameObjectType.CHARACTER, animation);
        this.weapon = weapon;
        this.movementSpeed = 3;
    }

    public Character(double x, double y, Weapon weapon, Animation animation, Map map){
        this(x, y, weapon, animation);
        this.map = map;
    }

    // Update
    @Override
    public void update(){
        // Only move and update weapon when not stunned
        if(stun == 0) {
            // Play movement animation when moving
            if(getVx() == 0 && getVy() == 0){
                getAnimation().pause();
            }else{
                getAnimation().play();
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
        setVx(movementSpeed*x);
        setVy(-movementSpeed*y);
    }

    // Update weapon
    protected void updateWeapon(){
        getWeapon().update(getCenterX(), getCenterY());
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
        setX(tile.getCenterX()-getWidth()/2);
        setY(tile.getCenterY()-getHeight()/2);
    }

    public Point getCenter(){
        return new Point(getCenterX(), getCenterY());
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

    public int getStun() {
        return stun;
    }
}
