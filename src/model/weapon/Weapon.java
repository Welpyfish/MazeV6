package model.weapon;

import model.*;
import model.Character;

import java.awt.*;

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
    private WeaponID weaponID;

    // Frame is which frame the state is currently on
    // Time is the number of frame for that state
    private int chargeFrame;
    private int chargeTime;
    private int attackFrame;
    private int attackTime;
    private int cooldownFrame;
    private int cooldownTime;

    private Team team;

    private boolean pressed;

    protected Map map;

    public Weapon(Team team, Map map){
        super(0, 0);
        this.team = team;
        this.map = map;
    }

    // Start the first state if cooldown is finished
    public void startCharge(){
        chargeFrame = 1;
        getAnimation().play();
    }

    protected boolean charged(){
        return chargeFrame >= chargeTime;
    }

    // Start the attack state if the weapon is charged (subject to change)
    public void startAttack(){
        attackFrame = 1;
        chargeFrame = 0;
        getAnimation().play();
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

    public void setTarget(Point p){
        // Set the aiming angle
        angle = Math.atan2(p.y-getY(), p.x-getX());
        // Update the target distance
        distance = Math.hypot(p.y-getY(), getX()-p.x);
    }

    // Update weapon given target location
    public void update(int x, int y){
        // Set location to center of character
        setX(x);
        setY(y);

        if(pressed){
            pressAction();
        }

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

    // Shooter methods

    public void setProjectile(ProjectileType projectileType){}

    public int ammoUsed(){
        return 0;
    }

    public double getAngle() {
        return angle;
    }

    protected void setAngle(double angle) {
        this.angle = angle;
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

    public WeaponID getWeaponID() {
        return weaponID;
    }

    protected void setWeaponID(WeaponID weaponID) {
        this.weaponID = weaponID;
    }

    protected double getDistance() {
        return distance;
    }

    public Team getTeam() {
        return team;
    }

    public void setPressed(boolean pressed) {
        if(!pressed && this.pressed){
            releaseAction();
        }
        this.pressed = pressed;
    }

    protected void pressAction(){
        if(chargeFrame ==0 && cooldownFrame == 0) {
            startCharge();
        }
    }

    protected void releaseAction(){
        if(attackFrame ==0 && charged()) {
            startAttack();
        }
    }
}
