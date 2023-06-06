package model.weapon;

import model.Animation;
import model.Character;
import model.Team;

import java.util.HashSet;

public class Melee extends Weapon{
    private double targetAngle;
    private HashSet<Character> targetsHit;

    public Melee(WeaponStat weaponStat, Team team, Animation animation){
        super(weaponStat, team, animation);
        targetsHit = new HashSet<>();
    }

    @Override
    public void startAttack(){
        super.startAttack();
        targetAngle = getAngle();
        targetsHit.clear();
        setCurrentRange(getMaxRange());
    }

    @Override
    public void startCooldown(){
        super.startCooldown();
        setCurrentRange(getMinRange());
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
}
