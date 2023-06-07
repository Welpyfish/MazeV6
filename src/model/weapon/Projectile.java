package model.weapon;

import model.*;
import model.Character;

public class Projectile extends Sprite {
    // Reference to map to check surroundings
    Map map;
    // Fired by player, enemy, or game level
    private Team team;
    // If the projectile has been fired
    private boolean active;
    // How far the projectile will travel
    private int range;
    // Damage upon hitting a character
    private int damage;

    private int hitRadius;
    private int explosionDamage;
    private int stun;

    // How fast the projectile travels
    private double speed;
    // Launch position
    private int xi, yi;
    // Angle the projectile is facing
    private double angle;
    // Current velocity
    private double vx, vy;

    // Create a projectile
    public Projectile(ProjectileStat projectileStat, Team team, Animation animation){
        super(0, 0, animation);
        this.team = team;
        this.damage = projectileStat.getDamage();
        this.speed = projectileStat.getSpeed();
        this.hitRadius = projectileStat.getHitRadius();
        this.explosionDamage = projectileStat.getExplosionDamage();
        this.stun = projectileStat.getStun();
        active = false;
    }

    public void update(){
        // If launched
        if(active) {
            // Move in a straight line
            changeX(vx);
            changeY(vy);
            // Get destroyed if at end of range
            if (Math.hypot(getX() - xi, getY() - yi) > range) {
                remove();
            }
        }
    }

    // Called from the Shooter class to update location and angle
    public void updateWhenLoaded(int x, int y, double angle){
        this.angle = angle;
        setX(x);
        setY(y);
    }

    // Called from the Shooter class when the projectile is released, set launch values
    public void launch(int range, int baseDamage){
        active = true;
        xi = getX();
        yi = getY();
        this.range = range - getCurrentImage().getWidth();
        damage += baseDamage;
        vx = speed*Math.cos(angle);
        vy = speed*Math.sin(angle);
    }

    // Getters

    public double getAngle(){
        return angle;
    }

    public int getDamage(){
        return damage;
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

    public Team getTeam() {
        return team;
    }

    public boolean isActive(){
        return active;
    }
}
