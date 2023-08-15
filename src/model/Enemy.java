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

    public Enemy(double x, double y, Weapon weapon, ProjectileType projectileType, Animation animation, Map map) {
        super(x, y, weapon, animation, map);
        setWeapon(weapon);
        this.projectileType = projectileType;
        changeHp(3);
        setSightRange(Math.max(Constants.tileSize*10, getWeapon().getMaxRange()+Constants.tileSize*3));
        attackDelayTime = (int) (getSightRange()*Constants.fps/(Constants.tileSize*3));
        movementDelayTime = (int) (0.1*Constants.fps);
        attackDelay = (int) Constants.fps;

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
            if(getVx() == 0 && getVy() == 0) {
                // Move towards player if out of range, away if too close
                if (Math.hypot(getIntX() - map.player.getIntX(), getIntY() - map.player.getIntY()) > Math.max(getWeapon().getMaxRange() - Constants.tileSize, 0)) {
                    moveTowards(map.player.getIntX(), map.player.getIntY());
                } else if (Math.hypot(getIntX() - map.player.getIntX(), getIntY() - map.player.getIntY()) < getWeapon().getMinRange()) {
                    moveAway(map.player.getIntX(), map.player.getIntY());
                    System.out.println(getWeapon().getMinRange());
                }
                movementDelay = (int) (movementDelayTime + 0.2*Math.random()*Constants.fps);
            }else{
                updateMovement(0, 0);
                movementDelay = (int) (movementDelayTime + 1.8*Math.random()*Constants.fps);
            }
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
            if(attackDelay <= 0 && Math.hypot(getCenterX()-target.getX(), getCenterY()-target.getY())<=getWeapon().getMaxRange()+1.4*Constants.tileSize) {
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
            attackDelay-=1.5*Constants.fps;
        }
    }

    // Start attacking
    protected void startAttack(){
        getWeapon().setProjectile(projectileType);
        getWeapon().setTrigger(true);
        // Wait a short time before allowing another attack
        attackDelay = (int) (attackDelayTime + Math.random()*Constants.fps);
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
