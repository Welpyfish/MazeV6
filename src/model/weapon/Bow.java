/*
 * Final Project
 * Maze
 * William Zhou
 * 2023-06-19
 * ICS4UI-4
 *
 * The Bow class describes shooter weapons that increase in range when held and attack on release
 */

package model.weapon;

import model.Animation;
import model.Team;

public class Bow extends Shooter {

    public Bow (WeaponStat weaponStat, Team team, Animation animation){
        super(weaponStat, team, animation);
    }

    // Allow the bow to be fired before it is fully charged
    @Override
    protected boolean charged(){
        return true;
    }

    // When charging, set range depending on how charged the bow is
    @Override
    public void charge(int chargeFrame, int chargeTime){
        super.charge(chargeFrame, chargeTime);
        setCurrentRange(getMinRange() + (getMaxRange()-getMinRange())*chargeFrame/chargeTime);
    }
}
