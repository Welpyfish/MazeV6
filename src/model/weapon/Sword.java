package model.weapon;

import model.*;
import model.Character;

import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.HashSet;

public class Sword extends Melee{

    public Sword (WeaponStat weaponStat, Team team, Animation animation){
        super(weaponStat, team, animation);
    }

    @Override
    protected void charge(int chargeFrame, int chargeTime){
        setAngle(getAngle() + 0.9*Math.PI*(0.5*chargeFrame/chargeTime));
    }

    @Override
    protected void attack(int attackFrame, int attackTime){
        super.attack(attackFrame, attackTime);
        setAngle(getAngle() + 0.9*Math.PI*(0.5-1.0*attackFrame/attackTime));
    }

    @Override
    protected void cooldown(int cooldownFrame, int cooldownTime){
        super.cooldown(cooldownFrame, cooldownTime);
        setAngle(getAngle() + 0.9*Math.PI*(1.0*cooldownFrame/cooldownTime-0.5));
    }
}
