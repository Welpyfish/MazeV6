/*
 * Final Project
 * Maze
 * William Zhou
 * 2023-06-19
 * ICS4UI-4
 *
 * The Throw class describes how projectile can be thrown
 */

package model.weapon;

import model.*;

import java.awt.*;

public final class Throw extends Shooter{

    public Throw(WeaponStat weaponStat, Team team, Animation animation){
        super(weaponStat, team, animation);
    }

    // Set the weapon target
    @Override
    public void setTarget(Point character, Point target){
        super.setTarget(character, target);
        // Allow the range to be less than the max range if the target is closer
        setCurrentRange(Math.min((int) Math.hypot(target.y-character.y, target.x - character.x), getMaxRange()));
    }
}
