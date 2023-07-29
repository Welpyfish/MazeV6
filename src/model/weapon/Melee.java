/*
 * Final Project
 * Maze
 * William Zhou
 * 2023-06-19
 * ICS4UI-4
 *
 * The Melee class describes melee weapons that attack using weapon collision
 */

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

    // Start an attack
    @Override
    public void startAttack(){
        // Turn on collision
        active = true;
        // Set initial angle
        targetAngle = getAngle();
        // Clear set of targets hit
        targetsHit.clear();
        super.startAttack();
    }

    // Start the cooldown
    @Override
    public void startCooldown(){
        // Turn off collision
        active = false;
        setCurrentRange(getMinRange());
        super.startCooldown();
    }

    // When attacking, point the same direction as the initial angle
    @Override
    protected void attack(int attackFrame, int attackTime){
        setAngle(targetAngle);
    }

    // Determine if a target can be hit
    public boolean addTarget(Character target){
        // Only return true if the target wasn't already hit in the current attack
        // This prevents targets from being hit multiple times in a single attack
        if(!targetsHit.contains(target)) {
            targetsHit.add(target);
            return true;
        }
        return false;
    }

    // Charge and attack when pressed
    @Override
    protected void pressAction(){
        super.pressAction();
        super.releaseAction();
    }

    // Do nothing on release
    @Override
    protected void releaseAction(){

    }

    public boolean isActive() {
        return active;
    }
}
