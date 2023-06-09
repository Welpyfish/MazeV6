package model;

import model.weapon.Weapon;

import java.awt.*;

public class Enemy extends Character {
    private int attackDelayTime;
    private int movementDelayTime;
    // Frames of delay between attacking
    private int attackDelay;
    // Frames of delay between movement
    private int movementDelay;

    public Enemy(Tile tile, Weapon weapon, Map map) {
        super(tile, map);
        setWeapon(weapon);
        changeHp(3);
        setSightRange(Math.max(GameConstants.tileSize*10, getWeapon().getMaxRange()+GameConstants.tileSize*3));
        attackDelayTime = (int) (getSightRange()*GameConstants.fps/(GameConstants.tileSize*3));
        movementDelayTime = (int) (GameConstants.fps);
    }

    //
    @Override
    public void update(){
        super.update();
        // Move if the player is in sight
        if(map.findClosestTarget(getCenter(), getSightRange(), Team.ENEMY) != null) {
            move();
        }
    }

    public void move(){
        if(movementDelay <= 0) {
            if (Math.hypot(getX() - map.player.getX(), getY() - map.player.getY()) > getWeapon().getMaxRange()-GameConstants.tileSize*2) {
                moveTowards(map.player.getX(), map.player.getY());
            } else if (Math.hypot(getX() - map.player.getX(), getY() - map.player.getY()) < getWeapon().getMinRange()) {
                moveAway(map.player.getX(), map.player.getY());
            }else{
                updateMovement(0, 0);
            }
            movementDelay = (int) (movementDelayTime + 2*Math.random()*GameConstants.fps);
        }else {
            movementDelay--;
        }
    }

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

    @Override
    public void changeHp(int c){
        super.changeHp(c);
        // Start charging attack if hit and player is in sight
        // Makes it hard to avoid trading hp
        if(getHp()>0 && c < 0 && map.findClosestTarget(getCenter(), getSightRange(), Team.ENEMY)!=null){
            attackDelay-=2*GameConstants.fps;
        }
    }

    protected void startAttack(){
        getWeapon().setTrigger(true);
        // Wait a short time before allowing another attack
        attackDelay = (int) (attackDelayTime + Math.random()*GameConstants.fps);
    }

    // Move towards a point
    private void moveTowards(int x, int y){
        if(Math.abs(x-this.getX())>Math.abs(this.getY()-y)){
            updateMovement(Integer.signum(x-this.getX()), 0);
        }else{
            updateMovement(0, Integer.signum(this.getY()-y));
        }
    }

    // Move away from a point
    private void moveAway(int x, int y){
        if(Math.abs(x-this.getX())>Math.abs(this.getY()-y)){
            updateMovement(-Integer.signum(x-this.getX()), 0);
        }else{
            updateMovement(0, -Integer.signum(this.getY()-y));
        }
    }
}
