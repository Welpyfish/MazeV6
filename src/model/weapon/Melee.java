package model.weapon;

import model.Animation;
import model.Character;
import model.Team;

import java.util.HashSet;

public class Melee extends Weapon{
    private double targetAngle;
    private HashSet<Character> targetsHit;
    private boolean active;

    public Melee(WeaponStat weaponStat, Team team, Animation animation){
        super(weaponStat, team, animation);
        targetsHit = new HashSet<>();
    }

    @Override
    public void startAttack(){
        active = true;
        targetAngle = getAngle();
        targetsHit.clear();
        super.startAttack();
    }

    @Override
    public void startCooldown(){
        active = false;
        super.startCooldown();
    }

    public boolean addTarget(Character target){
        if(!targetsHit.contains(target)) {
            targetsHit.add(target);
            return true;
        }
        return false;
    }

    @Override
    protected void pressAction(){
        super.pressAction();
        super.releaseAction();
    }

    @Override
    protected void releaseAction(){

    }

    protected double getTargetAngle() {
        return targetAngle;
    }

    public boolean isActive() {
        return active;
    }
}
