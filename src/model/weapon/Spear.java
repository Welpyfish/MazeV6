package model.weapon;

import model.*;
import model.Character;

import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.HashSet;

public class Spear extends Melee{

    public Spear (WeaponStat weaponStat, Team team, Animation animation){
        super(weaponStat, team, animation);
    }

    @Override
    protected void attack(int attackFrame, int attackTime){
        super.attack(attackFrame, attackTime);
        setCurrentRange((getMaxRange()-getCurrentImage().getWidth())*attackFrame/attackTime);
        setX(getX() + Math.cos(getAngle())*getCurrentRange());
        setY(getY() + Math.sin(getAngle())*getCurrentRange());
    }

    @Override
    protected void cooldown(int cooldownFrame, int cooldownTime){
        super.cooldown(cooldownFrame, cooldownTime);
        setCurrentRange((int) ((getMaxRange()-getCurrentImage().getWidth())*(1-(double)cooldownFrame/cooldownTime)));
        setX(getX() + Math.cos(getAngle())*getCurrentRange());
        setY(getY() + Math.sin(getAngle())*getCurrentRange());
    }
}
