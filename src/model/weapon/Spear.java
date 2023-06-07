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
        setAngle(getTargetAngle());
        setX(getX() + Math.cos(getAngle())*
                (getMaxRange()-getCurrentImage().getWidth())*attackFrame/attackTime);
        setY(getY() + Math.sin(getAngle())*
                (getMaxRange()-getCurrentImage().getWidth())*attackFrame/attackTime);
    }

    @Override
    protected void cooldown(int cooldownFrame, int cooldownTime){
        setAngle(getTargetAngle());
        setX(getX() + Math.cos(getAngle())*
                (getMaxRange()-getCurrentImage().getWidth())*(1-(double)cooldownFrame/cooldownTime));
        setY(getY() + Math.sin(getAngle())*
                (getMaxRange()-getCurrentImage().getWidth())*(1-(double)cooldownFrame/cooldownTime));
    }
}
