package model.weapon;

import model.*;
import model.Character;

import java.awt.*;

public final class NoWeapon extends Shooter{

    public NoWeapon (WeaponStat weaponStat, Team team, Animation animation){
        super(weaponStat, team, animation);
    }

    @Override
    public void setTarget(Point p){
        super.setTarget(p);
        // Update the target distance
        setCurrentRange(Math.min((int) Math.hypot(p.y-getY(), getX()-p.x), getMaxRange()));
    }
}
