package model.weapon;

import model.*;
import model.Character;

public class Bow extends Shooter {

    public Bow (WeaponStat weaponStat, Team team, Animation animation){
        super(weaponStat, team, animation);
    }

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
