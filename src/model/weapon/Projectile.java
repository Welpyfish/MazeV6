package model.weapon;

import model.Character;
import model.Map;
import model.Sprite;
import model.Team;
import model.TileObject;

public class Projectile extends Sprite {
    // Reference to map to check surroundings
    Map map;
    // Fired by player, enemy, or game level
    private Team team;
    // If the projectile has been fired
    private boolean active;
    // How far the projectile will travel
    private int range;
    // How fast the projectile travels
    private double speed;
    // Launch position
    int xi, yi;
    // Angle the projectile is facing
    public double angle;
    // Current velocity
    double vx, vy;
    // If the projectile should be removed
    private boolean hit;
    // Damage upon hitting a character
    protected int damage;

    // Create a projectile
    public Projectile(Team team, Map map){
        super(0, 0);
        // Know if the projectile was fired by a player, enemy, or game level
        this.team = team;
        // Stick to the shooter
        active = false;
        this.map = map;
        map.projectiles.add(this);
    }

    public void update(){
        // If launched
        if(active) {
            // Move in a straight line
            changeX(vx);
            changeY(vy);
            // Get destroyed if at end of range or hit a character on a different team
            if (Math.hypot(getX() - xi, getY() - yi) > range) {
                destroy();
            } else {
                // Get destroyed when colliding with an object
                TileObject collider = map.projectileCollision(getX(), getY(), team);
                if(collider != null){
                    destroy();
                    // If the collider was a character, attack it
                    if(collider instanceof model.Character){
                        attack((model.Character) collider);
                    }
                }
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
        this.range = range;
        damage += baseDamage;
        vx = speed*Math.cos(angle);
        vy = speed*Math.sin(angle);
    }

    // Called when projectile hits something
    void destroy(){
        remove();
    }

    // Remove itself
    public void remove(){
        hit = true;
    }

    // Attack a character
    void attack(Character collider){

    }

    // Called by map to remove this projectile
    public boolean isHit() {
        return hit;
    }

    // Set launch speed
    public void setSpeed(double speed){
        this.speed = speed;
    }
}
