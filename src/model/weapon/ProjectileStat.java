package model.weapon;

import model.GameConstants;

public class ProjectileStat {
    private double speed;
    private int damage;
    private int hitRadius;
    private int explosionDamage;
    private int stun;

    public ProjectileStat(int damage, int speed){
        this.damage = damage;
        this.speed = speed;
    }

    public ProjectileStat hitRadius(double hitRadius){
        this.hitRadius = (int) (GameConstants.tileSize *hitRadius);
        return this;
    }

    public ProjectileStat explosionDamage(int explosionDamage){
        this.explosionDamage = explosionDamage;
        return this;
    }

    public ProjectileStat stun(double stun){
        this.stun = (int) (GameConstants.fps*stun);
        return this;
    }

    public int getDamage() {
        return damage;
    }

    public double getSpeed() {
        return speed;
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
