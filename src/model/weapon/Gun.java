package model.weapon;

import model.*;
import model.Character;

public class Gun extends Shooter{

    public Gun (WeaponStat weaponStat, Team team, Animation animation){
        super(weaponStat, team, animation);
    }

    @Override
    protected void pressAction(){
        super.pressAction();
        super.releaseAction();
    }

    @Override
    protected void releaseAction(){

    }
}
