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

    // Create a projectile
    public Projectile(ProjectileStat projectileStat, Team team, Animation animation){
        super(0, 0, 1, 1, false, GameObjectType.PROJECTILE, animation);
        this.team = team;
        this.damage = projectileStat.getDamage();
        this.speed = projectileStat.getSpeed();
        this.range = projectileStat.getRange();
        this.hitRadius = projectileStat.getHitRadius();
        this.explosionDamage = projectileStat.getExplosionDamage();
        this.stun = projectileStat.getStun();
        active = false;
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

    @Override
    public void remove(){
        super.remove();
        changeX(-getVx());
        changeY(-getVy());
    }

    // Called from the Shooter class to update location and angle
    public void updateWhenLoaded(int x, int y, double angle){
        setAngle(angle);
        setX(x);
        setY(y);
    }

    // Called from the Shooter class when the projectile is released, sets launch values
    public void launch(int range, int baseDamage){
        active = true;
        xi = getIntX();
        yi = getIntY();
        this.range = (this.range==-1)?range:this.range - getCurrentImage().getWidth();
        damage += baseDamage;
        setVx(speed*Math.cos(getAngle()));
        setVy(speed*Math.sin(getAngle()));
        update();
    }

    // Getters

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
