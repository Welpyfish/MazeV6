package model.weapon;

import model.*;
import model.Character;

public class Weapon extends Sprite {
    // Pointing direction
    private double angle;
    // Distance from weapon to mouse
    private double distance;
    // Range of weapon attack
    private int range;
    // Base damage added to projectiles
    private int damage;
    // Weapon id
    private WeaponType weaponType;

    // Frame is which frame the state is currently on
    // Time is the number of frame for that state
    private int chargeFrame;
    private int chargeTime;
    private int attackFrame;
    private int attackTime;
    private int cooldownFrame;
    private int cooldownTime;

    private Team team;

    protected Map map;

    public Weapon(Team team, Map map){
        super(0, 0);
        this.team = team;
        this.map = map;
    }

    // Start the first state if cooldown is finished
    public void startCharge(){
        if(chargeFrame ==0 && cooldownFrame == 0) {
            chargeFrame = 1;
            getAnimation().play();
        }
    }

    // Start the attack state if the weapon is charged (subject to change)
    public void startAttack(){
        if(attackFrame ==0 && chargeFrame >= chargeTime) {
            attackFrame = 1;
            chargeFrame = 0;
            getAnimation().play();
        }
    }

    // Called from update() during the charge state
    protected void charge(int chargeFrame, int chargeTime){

    }

    // Called from update() during the attack state
    protected void attack(int attackFrame, int attackTime){

    }

    // Return to the original state
    public void reset(){
        chargeFrame = 0;
        attackFrame = 0;
        cooldownFrame = 0;
        getAnimation().reset();
    }

    // Update the target of this weapon
    public void setTarget(int tx, int ty){
        // Set the aiming angle
        angle = Math.atan2(ty-getY(), tx-getX());
        // Update the target distance
        distance = Math.hypot(ty-getY(), getX()-tx);
    }

    // Update weapon given target location
    public void update(int x, int y){
        // Set location to center of character
        setX(x);
        setY(y);

        // If attack is started, run attack state
        if(attackFrame > 0) {
            if (attackFrame < attackTime) {
                attackFrame++;
                attack(attackFrame, chargeTime);
            }
            // If attack is done, continue to cooldown state
            else if (cooldownFrame < cooldownTime) {
                cooldownFrame++;
            } else {
                //At the end of cooldown, reset weapon
                reset();
            }
        }
        // If attack hasn't started, run charge state if charge was started
        else if(chargeFrame > 0) {
            if (chargeFrame < chargeTime) {
                chargeFrame++;
            } else {
                // Once charged, remain in this state but pause animation
                getAnimation().pause();
            }
            charge(chargeFrame, chargeTime);
        }
        // Update animation
        getAnimation().update();
    }

    public double getAngle() {
        return angle;
    }

    protected int getDamage() {
        return damage;
    }

    protected void setDamage(int damage) {
        this.damage = damage;
    }

    // Called from subclass contructor to set state timelimits
    protected void setAttackTime(int chargeTime, int attackTime, int cooldownTime){
        this.chargeTime = chargeTime;
        this.attackTime = attackTime;
        this.cooldownTime = cooldownTime;
    }

    public int getRange() {
        return range;
    }

    protected void setRange(int range) {
        this.range = range;
    }

    public WeaponType getWeaponType() {
        return weaponType;
    }

    protected void setWeaponType(WeaponType weaponType) {
        this.weaponType = weaponType;
    }

    protected double getDistance() {
        return distance;
    }

    public Team getTeam() {
        return team;
    }
}
