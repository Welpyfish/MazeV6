/*
 * Final Project
 * Maze
 * William Zhou
 * 2023-06-19
 * ICS4UI-4
 *
 * The Projectile class describes all projectiles fired from shooter weapons
 * Projectiles are represented as points and don't stick to the grid
 */

package model.weapon;

import model.*;

public class Projectile extends Sprite {
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

    // Create a projectile
    public Projectile(ProjectileStat projectileStat, Team team, Animation animation){
        super(0, 0, 1, 1, animation);
        this.team = team;
        this.damage = projectileStat.getDamage();
        this.speed = projectileStat.getSpeed();
        this.range = projectileStat.getRange();
        this.hitRadius = projectileStat.getHitRadius();
        this.explosionDamage = projectileStat.getExplosionDamage();
        this.stun = projectileStat.getStun();
        active = false;
        setCollision(false);
    }

    // Update
    @Override
    public void update(){
        // If launched
        if(active) {
            // Get destroyed if at end of range
            if (Math.hypot(getX() - xi, getY() - yi) > range) {
                remove();
            }
        }
        super.update();
        updateX();
        updateY();
    }

    // Called from the Shooter class to update location and angle
    public void updateWhenLoaded(int x, int y, double angle){
        this.angle = angle;
        setX(x);
        setY(y);
    }

    // Called from the Shooter class when the projectile is released, sets launch values
    public void launch(int range, int baseDamage){
        active = true;
        xi = getIntX();
        yi = getIntY();
        this.range = Math.max(this.range, range) - getCurrentImage().getWidth();
        damage += baseDamage;
        setVx(speed*Math.cos(angle));
        setVy(speed*Math.sin(angle));
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
