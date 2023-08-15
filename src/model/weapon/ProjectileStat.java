/*
 * Final Project
 * Maze
 * William Zhou
 * 2023-06-19
 * ICS4UI-4
 *
 * The ProjectileStat class holds all stats relevant to projectile creation
 * This reduces the number of parameters in the constructor and allows default values
 * Builder pattern is used for optional values
 */

package model.weapon;

import model.Constants;

public class ProjectileStat {
    private double speed;
    private int damage;
    private int range;
    private int hitRadius;
    private int explosionDamage;
    private int stun;

    public ProjectileStat(int damage, int speed){
        this.damage = damage;
        this.speed = speed;
        this.range = -1;
    }

    public ProjectileStat range(double range){
        this.range = (int) (Constants.tileSize*range);
        return this;
    }

    public ProjectileStat hitRadius(double hitRadius){
        this.hitRadius = (int) (Constants.tileSize *hitRadius);
        return this;
    }

    public ProjectileStat explosionDamage(int explosionDamage){
        this.explosionDamage = explosionDamage;
        return this;
    }

    public ProjectileStat stun(double stun){
        this.stun = (int) (Constants.fps*stun);
        return this;
    }

    public int getDamage() {
        return damage;
    }

    public double getSpeed() {
        return speed;
    }

    public int getRange(){
        return range;
    }

    public int getHitRadius() {
        return hitRadius;
    }

    public int getExplosionDamage() {
        return explosionDamage;
    }

    public int getStun() {
        return stun;
    }
}
