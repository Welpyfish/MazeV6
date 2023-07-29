/*
 * Final Project
 * Maze
 * William Zhou
 * 2023-06-19
 * ICS4UI-4
 *
 * The WeaponStat class holds all stats relevant to weapon creation
 * This reduces the number of parameters in the constructor and allows default values
 * Builder pattern is used for optional values
 */

package model.weapon;

import model.GameConstants;

public class WeaponStat {
    private int minRange, maxRange, startRange;
    private int damage;
    private WeaponID weaponID;
    private int chargeTime, attackTime, cooldownTime;
    private int ammoCost;

    public WeaponStat(double maxRange, int damage, WeaponID weaponID){
        this.maxRange = (int) (GameConstants.tileSize*maxRange);
        minRange = Math.max(GameConstants.tileSize, this.maxRange-4* GameConstants.tileSize);
        startRange = this.maxRange;
        this.damage = damage;
        this.weaponID = weaponID;
        chargeTime = 1;
        attackTime = 1;
        cooldownTime = 1;
    }

    public WeaponStat startRange(double startRange){
        this.startRange = (int) (GameConstants.tileSize*startRange);
        return this;
    }

    public WeaponStat minRange(double minRange){
        this.minRange = (int) (GameConstants.tileSize*minRange);
        return this;
    }

    public WeaponStat chargeTime(double chargeTime){
        this.chargeTime = (int) (GameConstants.fps*chargeTime);
        return this;
    }

    public WeaponStat attackTime(double attackTime){
        this.attackTime = (int) (GameConstants.fps*attackTime);
        return this;
    }

    public WeaponStat cooldownTime(double cooldownTime){
        this.cooldownTime = (int) (GameConstants.fps*cooldownTime);
        return this;
    }

    public WeaponStat ammoCost(int ammoCost){
        this.ammoCost = ammoCost;
        return this;
    }

    public int getMinRange() {
        return minRange;
    }

    public int getMaxRange() {
        return maxRange;
    }

    public int getStartRange() {
        return startRange;
    }

    public int getDamage() {
        return damage;
    }

    public WeaponID getWeaponID() {
        return weaponID;
    }

    public int getChargeTime() {
        return chargeTime;
    }

    public int getAttackTime() {
        return attackTime;
    }

    public int getCooldownTime() {
        return cooldownTime;
    }

    public int getAmmoCost() {
        return ammoCost;
    }
}
