package model.weapon;

import model.*;
import model.Character;

import java.awt.*;

public final class NoWeapon extends Shooter{

    public NoWeapon (WeaponStat weaponStat, Team team, Animation animation){
        super(weaponStat, team, animation);
    }

    @Override
    public void setTarget(Point character, Point target){
        super.setTarget(character, target);
        // Update the target distance
        setCurrentRange(Math.min((int) Math.hypot(target.y-character.y, target.x - character.y), getMaxRange()));
    }
}
