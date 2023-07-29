/*
 * Final Project
 * Maze
 * William Zhou
 * 2023-06-19
 * ICS4UI-4
 *
 * The Spear class describes melee weapons that attack by stabbing
 */

package model.weapon;

import model.*;

public class Spear extends Melee{

    public Spear (WeaponStat weaponStat, Team team, Animation animation){
        super(weaponStat, team, animation);
    }

    // Attack action
    @Override
    protected void attack(int attackFrame, int attackTime){
        super.attack(attackFrame, attackTime);
        setCurrentRange((getMaxRange()-getCurrentImage().getWidth())*attackFrame/attackTime);
        setX(getX() + Math.cos(getAngle())*getCurrentRange());
        setY(getY() + Math.sin(getAngle())*getCurrentRange());
    }

    // Cooldown action
    @Override
    protected void cooldown(int cooldownFrame, int cooldownTime){
        super.cooldown(cooldownFrame, cooldownTime);
        setCurrentRange((int) ((getMaxRange()-getCurrentImage().getWidth())*(1-(double)cooldownFrame/cooldownTime)));
        setX(getX() + Math.cos(getAngle())*getCurrentRange());
        setY(getY() + Math.sin(getAngle())*getCurrentRange());
    }
}
