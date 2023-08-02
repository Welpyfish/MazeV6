/*
 * Final Project
 * Maze
 * William Zhou
 * 2023-06-19
 * ICS4UI-4
 *
 * The Weapon class is the base class for all weapons
 */

package model.weapon;

import model.*;

import java.awt.*;

public class Weapon extends Sprite {
    // Range of weapon attack
    private int currentRange, minRange, maxRange;
    // Base damage
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

    private boolean triggered;

    // Shooter variables
    private ProjectileType projectileType;
    private int ammoCost;

    public Weapon(WeaponStat weaponStat, Team team, Animation animation){
        super(0, 0, animation);
        this.team = team;
        this.currentRange = weaponStat.getStartRange();
        this.minRange = weaponStat.getMinRange();
        this.maxRange = weaponStat.getMaxRange();
        this.damage = weaponStat.getDamage();
        this.weaponID = weaponStat.getWeaponID();
        this.chargeTime = weaponStat.getChargeTime();
        this.attackTime = weaponStat.getAttackTime();
        this.cooldownTime = weaponStat.getCooldownTime();
        this.ammoCost = weaponStat.getAmmoCost();
        chargeFrame = -1;
        attackFrame = -1;
        cooldownFrame = -1;
        projectileType = null;
    }

    // Start the charge state
    protected void startCharge(){
        chargeFrame = 0;
        getAnimation().play();
        charge(chargeFrame, chargeTime);
    }

    // Start the attack state
    protected void startAttack(){
        attackFrame = 0;
        chargeFrame = -1;
        getAnimation().play();
        attack(attackFrame, attackTime);
    }

    // Start the cooldown state
    protected void startCooldown(){
        cooldownFrame = 0;
        attackFrame = -1;
        cooldown(cooldownFrame, cooldownTime);
    }

    // Called from update() during the charge state
    protected void charge(int chargeFrame, int chargeTime){

    }

    // Called from update() during the attack state
    protected void attack(int attackFrame, int attackTime){

    }

    // Called from update() during the attack state
    protected void cooldown(int cooldownFrame, int cooldownTime){

    }

    // Return to the original state
    public void reset(){
        chargeFrame = -1;
        attackFrame = -1;
        cooldownFrame = -1;
        getAnimation().reset();
    }

    // Update weapon given target location
    public void update(int x, int y){
        // Set location to center of character
        setX(x);
        setY(y);

        if(triggered){
            pressAction();
        }

        // If cooldown is started, run cooldown state
        if(cooldownFrame > -1) {
            if (cooldownFrame < cooldownTime) {
                cooldownFrame++;
                cooldown(cooldownFrame, cooldownTime);
            } else {
                //At the end of cooldown, reset weapon
                reset();
            }
        }
        // If attack is started, run attack state
        else if(attackFrame > -1) {
            if (attackFrame < attackTime) {
                attackFrame++;
                attack(attackFrame, attackTime);
            }else {
                // Start cooldown immediately after attack
                startCooldown();
            }
        }
        // If attack hasn't started, run charge state if charge was started
        else if(chargeFrame > -1) {
            if (chargeFrame < chargeTime) {
                chargeFrame++;
            } else {
                // Once charged, remain in this state but pause animation
                getAnimation().pause();
                pressAction();
            }
            charge(chargeFrame, chargeTime);
        }
        super.update();
    }

    // By default, charge on press
    protected void pressAction(){
        if((chargeFrame == -1 && attackFrame == -1 && cooldownFrame == -1)) {
            startCharge();
        }
    }

    // By default, attack on release
    protected void releaseAction(){
        if(attackFrame == -1 && cooldownFrame == -1 && charged()) {
            startAttack();
        }
    }

    // Getter methods

    public int getDamage() {
        return damage;
    }

    public int getMinRange() {
        return minRange;
    }

    public int getMaxRange() {
        return maxRange;
    }

    public WeaponID getWeaponID() {
        return weaponID;
    }

    public Team getTeam() {
        return team;
    }

    public int ammoUsed(){
        return 0;
    }

    public int getCurrentRange() {
        return currentRange;
    }

    protected ProjectileType getProjectileType(){
        return projectileType;
    }

    protected int getAmmoCost() {
        return ammoCost;
    }

    protected boolean charged(){
        return chargeFrame >= chargeTime;
    }

    // Setter methods

    public void setTarget(Point target, Point character){
        // Set the aiming angle
        setAngle(Math.atan2(target.y-character.y, target.x - character.x));
    }

    public void setTrigger(boolean triggered) {
        if(!triggered && this.triggered){
            releaseAction();
        }
        this.triggered = triggered;
    }

    protected void setCurrentRange(int currentRange) {
        this.currentRange = currentRange;
    }

    // For shooter weapons
    public void setProjectile(ProjectileType projectileType){

    }

    protected void setProjectileType(ProjectileType projectileType){
        this.projectileType = projectileType;
    }
}
