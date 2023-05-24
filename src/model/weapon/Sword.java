package model.weapon;

import model.Map;
import model.Team;

import java.util.HashSet;

public class Sword extends Weapon{
    private double targetAngle;
    private HashSet enemiesHit;

    public Sword(Team team, Map map) {
        super(team, map);
        enemiesHit = new HashSet<>();
        setAttackTime(2, 60, 20);
    }

    @Override
    public void startAttack(){
        super.startAttack();
        targetAngle = getAngle();
        enemiesHit.clear();
    }

    @Override
    protected void attack(int attackFrame, int attackTime){
        setAngle(targetAngle + (0.5-1.0*attackFrame/attackTime));
    }

    //public void hitEnemy
}
