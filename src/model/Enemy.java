/*
 * Final Project
 * Maze
 * William Zhou
 * 2023-06-19
 * ICS4UI-4
 *
 * The Enemy class describes all enemy characters
 */

package model;

import model.weapon.ProjectileType;
import model.weapon.Weapon;

import java.awt.*;

public class Enemy extends Character {
    private int attackDelayTime;
    private int movementDelayTime;
    // Frames of delay between attacking
    private int attackDelay;
    // Frames of delay between movement
    private int movementDelay;

    private ProjectileType projectileType;
    private boolean dropWeapon;
    private int coinValue;

    public Enemy(Tile tile, Weapon weapon, ProjectileType projectileType, Animation animation, Map map) {
        super(tile, weapon, animation, map);
        setWeapon(weapon);
        this.projectileType = projectileType;
        changeHp(3);
        setSightRange(Math.max(GameConstants.tileSize*10, getWeapon().getMaxRange()+GameConstants.tileSize*3));
        attackDelayTime = (int) (getSightRange()*GameConstants.fps/(GameConstants.tileSize*3));
        movementDelayTime = (int) (0.1*GameConstants.fps);
        attackDelay = (int) GameConstants.fps;

        dropWeapon = true;
        coinValue = 1;
    }

    // Update
    @Override
    public void update(){
        // Move if the player is in sight
        if(map.findClosestTarget(getCenter(), getSightRange(), Team.ENEMY) != null) {
            move();
        }
        super.update();
    }

    // Manage movement
    public void move(){
        // Move only when delay is over
        if(movementDelay <= 0) {
            // Move towards player if out of range, away if too close
            if (Math.hypot(getIntX() - map.player.getIntX(), getIntY() - map.player.getIntY()) > getWeapon().getMaxRange()-GameConstants.tileSize*2) {
                moveTowards(map.player.getIntX(), map.player.getIntY());
            } else if (Math.hypot(getIntX() - map.player.getIntX(), getIntY() - map.player.getIntY()) < getWeapon().getMinRange()) {
                moveAway(map.player.getIntX(), map.player.getIntY());
            }else{
                updateMovement(0, 0);
            }
            movementDelay = (int) (movementDelayTime + 2*Math.random()*GameConstants.fps);
        }else {
            movementDelay--;
        }
    }

    // Update weapon
    @Override
    public void updateWeapon(){
        // When the player is in sight, continually try to charge weapon and attack
        Point target = map.findClosestTarget(getCenter(), getSightRange(), Team.ENEMY);
        if(target != null){
            // Only attack if the delay time is finished
            if(attackDelay <= 0) {
                startAttack();
            }else{
                getWeapon().setTrigger(false);
            }
            // Aim at player when in sight
            getWeapon().setTarget(target, getCenter());
        }else{
            getWeapon().setTrigger(false);
        }
        if(attackDelay > 0) {
            // Count down the delay
            attackDelay--;
        }
        super.updateWeapon();
    }

    // Change hp
    @Override
    public void changeHp(int c){
        super.changeHp(c);
        // Reduce attack delay if attacked and player is in sight
        // Makes it harder to avoid trading hp
        if(getHp()>0 && c < 0 && map.findClosestTarget(getCenter(), getSightRange(), Team.ENEMY)!=null){
            attackDelay-=1.5*GameConstants.fps;
        }
    }

    // Start attacking
    protected void startAttack(){
        getWeapon().setProjectile(projectileType);
        getWeapon().setTrigger(true);
        // Wait a short time before allowing another attack
        attackDelay = (int) (attackDelayTime + Math.random()*GameConstants.fps);
    }

    // Move towards a point
    private void moveTowards(int x, int y){
        if(Math.abs(x-this.getIntX())>Math.abs(this.getIntY()-y)){
            updateMovement(Integer.signum(x-this.getIntX()), 0);
        }else{
            updateMovement(0, Integer.signum(this.getIntY()-y));
        }
    }

    // Move away from a point
    private void moveAway(int x, int y){
        if(Math.abs(x-this.getIntX())>Math.abs(this.getIntY()-y)){
            updateMovement(-Integer.signum(x-this.getIntX()), 0);
        }else{
            updateMovement(0, -Integer.signum(this.getIntY()-y));
        }
    }

    public int getCoinValue() {
        return coinValue;
    }

    public boolean isDropWeapon() {
        return dropWeapon;
    }
}
