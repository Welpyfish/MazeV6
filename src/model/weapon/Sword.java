package model.weapon;

import model.*;
import model.Character;

import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.HashSet;

public class Sword extends Melee{
    private double targetAngle;
    private HashSet<Character> targetsHit;

    public Sword (WeaponStat weaponStat, Team team, Animation animation){
        super(weaponStat, team, animation);
        targetsHit = new HashSet<>();
    }

    @Override
    public void startAttack(){
        super.startAttack();
        targetAngle = getAngle();
        targetsHit.clear();
    }

    @Override
    protected void attack(int attackFrame, int attackTime){
        setAngle(targetAngle + 1.2*Math.PI*(0.5-1.0*attackFrame/attackTime));
    }

    @Override
    protected void cooldown(int cooldownFrame, int cooldownTime){
        setAngle(targetAngle + 1.2*Math.PI*(1.0*cooldownFrame/cooldownTime-0.5));
    }
}
