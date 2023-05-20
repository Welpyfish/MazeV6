package model;

import model.weapon.Weapon;

public class Enemy extends Character {
    // Frames of delay between attacking
    private int attackDelay;
    // Frames of delay between movement
    private int movementDelay;

    public Enemy(Tile tile, Weapon weapon, Map map) {
        super(tile, map);
        setWeapon(weapon);
        changeHp(3);
        setSightRange(Math.max(GameConstants.tileSize*10, getWeapon().getRange()+GameConstants.tileSize*3));
    }

    //
    @Override
    public void update(){
        super.update();
        // Move if the player is in sight
        if(map.inLineOfSight(this, map.player, getSightRange())) {
            move();
        }
    }

    public void move(){
        if(movementDelay <= 0) {
            if (Math.hypot(getX() - map.player.getX(), getY() - map.player.getY()) > getWeapon().getRange()-GameConstants.tileSize*2) {
                moveTowards(map.player.getX(), map.player.getY());
            } else if (Math.hypot(getX() - map.player.getX(), getY() - map.player.getY()) < GameConstants.tileSize*4) {
                moveAway(map.player.getX(), map.player.getY());
            }else{
                updateMovement(0, 0);
            }
            movementDelay = (int) (GameConstants.fps*1 + 2*Math.random()*GameConstants.fps);
        }else {
            movementDelay--;
        }
    }

    @Override
    public void updateWeapon(){
        // When the player is in sight, continually try to charge weapon and attack
        if(map.inLineOfSight(this, map.player, getSightRange())){
            startCharge();
            startAttack();
            // Aim at player when in sight
            getWeapon().setTarget(map.player.getCenterX(), map.player.getCenterY());
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
        if(getHp()>0 && c < 0 && map.inLineOfSight(this, map.player, getSightRange())){
            startCharge();
            attackDelay-=GameConstants.fps;
        }
    }

    // Charge the weapon
    protected void startCharge(){
        getWeapon().startCharge();
    }

    protected void startAttack(){
        // Only attack if the delay time is finished
        if(attackDelay <= 0) {
            getWeapon().startAttack();
            // Wait a short time before allowing another attack
            attackDelay = (int) (GameConstants.fps*5 + Math.random()*GameConstants.fps);
        }
    }

    // Move towards a point
    void moveTowards(int x, int y){
        if(Math.abs(x-this.getX())>Math.abs(this.getY()-y)){
            updateMovement(Integer.signum(x-this.getX()), 0);
        }else{
            updateMovement(0, Integer.signum(this.getY()-y));
        }
    }

    // Move away from a point
    void moveAway(int x, int y){
        if(Math.abs(x-this.getX())>Math.abs(this.getY()-y)){
            updateMovement(-Integer.signum(x-this.getX()), 0);
        }else{
            updateMovement(0, -Integer.signum(this.getY()-y));
        }
    }
}
