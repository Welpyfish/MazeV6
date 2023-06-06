package model.weapon;

import model.*;

import java.awt.*;

public class Weapon extends Sprite {
    // Pointing direction (between positive x and positive y)
    private double angle;
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

    // Create a weapon object given weapon stats, team, and animation
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
        chargeFrame = -2;
        attackFrame = -2;
        cooldownFrame = -2;
        projectileType = null;
    }

    // Start the charge state
    protected void startCharge(){
        chargeFrame = -1;
        getAnimation().play();
        System.out.println("startCharge");
    }

    // Start the attack state
    protected void startAttack(){
        attackFrame = -1;
        chargeFrame = -2;
        getAnimation().play();
        System.out.println("startAttack");
    }

    protected void startCooldown(){
        cooldownFrame = -1;
        attackFrame = -2;
        System.out.println("startCooldown");
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
        chargeFrame = -2;
        attackFrame = -2;
        cooldownFrame = -2;
        getAnimation().reset();
        System.out.println("reset");
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
        if(cooldownFrame > -2) {
            System.out.println("cooldown started");
            if (cooldownFrame < cooldownTime) {
                cooldownFrame++;
                cooldown(cooldownFrame, cooldownTime);
                System.out.println("cooldown===");
            } else {
                //At the end of cooldown, reset weapon
                reset();
                System.out.println("cooldown=reset");
            }
            //System.out.println("d");
        }
        // If attack is started, run attack state
        else if(attackFrame > -2) {
            System.out.println("attack started");
            if (attackFrame < attackTime) {
                attackFrame++;
                attack(attackFrame, attackTime);
                System.out.println("attack====");
            }else{
                // Start cooldown immediately after attack
                startCooldown();
                System.out.println("startCooldown===");
            }
            //System.out.println("a");
        }
        // If attack hasn't started, run charge state if charge was started
        else if(chargeFrame > -2) {
            System.out.println("start charge");
            if (chargeFrame < chargeTime) {
                chargeFrame++;
                System.out.println("charge=====");
            } else {
                // Once charged, remain in this state but pause animation
                getAnimation().pause();
            }
            charge(chargeFrame, chargeTime);
            //System.out.println("c");
        }
        super.update();

        //System.out.println("C "+chargeFrame+" A "+attackFrame+" D "+cooldownFrame);
        //System.out.println("C "+chargeTime+" A "+attackTime+" D "+cooldownTime);
        //System.out.println();
    }

    // By default, charge on press
    protected void pressAction(){
        if(chargeFrame == -2 && attackFrame == -2 && cooldownFrame == -2) {
            startCharge();

            System.out.println("pressAction-charge");
        }
        else
        {
            int i=1;
            System.out.println("pressAction-nothing");
        }
    }

    // By default, attack on release
    protected void releaseAction(){
        if(attackFrame == -2 && cooldownFrame == -2 && charged()) {
            startAttack();
            System.out.println("releaseAction-startattack");
        }
        else
        {
            int i=1;
            System.out.println("releaseAction-none");
        }
    }

    // Getter methods

    public double getAngle() {
        return angle;
    }

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

    public void setTarget(Point p){
        // Set the aiming angle
        angle = Math.atan2(p.y-getY(), p.x-getX());
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

    protected void setAngle(double angle) {
        this.angle = angle;
    }

    // For shooter weapons
    public void setProjectile(ProjectileType projectileType){

    }

    protected void setProjectileType(ProjectileType projectileType){
        this.projectileType = projectileType;
    }
}
