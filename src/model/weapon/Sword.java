/*
 * Final Project
 * Maze
 * William Zhou
 * 2023-06-19
 * ICS4UI-4
 *
 * The Sword class describes melee weapons that attack by slashing
 */

package model.weapon;

import model.*;

public class Sword extends Melee{

    public Sword (WeaponStat weaponStat, Team team, Animation animation){
        super(weaponStat, team, animation);
    }

    // Charge action
    @Override
    protected void charge(int chargeFrame, int chargeTime){
        setAngle(getAngle() + 0.9*Math.PI*(0.5*chargeFrame/chargeTime));
    }

    // Attack action
    @Override
    protected void attack(int attackFrame, int attackTime){
        super.attack(attackFrame, attackTime);
        setAngle(getAngle() + 0.9*Math.PI*(0.5-1.0*attackFrame/attackTime));
    }

    // Cooldown action
    @Override
    protected void cooldown(int cooldownFrame, int cooldownTime){
        super.cooldown(cooldownFrame, cooldownTime);
        setAngle(getAngle() + 0.9*Math.PI*(1.0*cooldownFrame/cooldownTime-0.5));
    }
}
