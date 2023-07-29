/*
 * Final Project
 * Maze
 * William Zhou
 * 2023-06-19
 * ICS4UI-4
 *
 * The Gun class describes shooter weapons that attack on press
 */

package model.weapon;

import model.*;
import model.Character;

public class Gun extends Shooter{

    public Gun (WeaponStat weaponStat, Team team, Animation animation){
        super(weaponStat, team, animation);
    }

    // Charge and attack when pressed
    @Override
    protected void pressAction(){
        super.pressAction();
        super.releaseAction();
    }

    // Do nothing on release
    @Override
    protected void releaseAction(){

    }
}
