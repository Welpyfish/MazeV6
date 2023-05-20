package model.weapon;

import model.*;
import model.Character;

public class Bow extends Shooter {
    // Angle error when firing
    private double spread;
    private int currentRange;
    private final int minRange = GameConstants.tileSize*4;

    public Bow (Team team, Map map){
        super(team, map);
        setAnimation(new Animation(ImageLoader.bow));
        // Add compatible projectile types (arrows)
        addProjectileType(ProjectileType.ARROW);
        addProjectileType(ProjectileType.BOMB_ARROW);
        spread = 0;
        setRange(GameConstants.tileSize*9);
        setWeaponType(WeaponType.BOW);
        setAttackTime(20, 2, 1);
    }

    // Launch the loaded projectiles
    @Override
    protected void attack(int attackFrame, int attackTime){
        // Temporary fix; may add min and max range as properties at some point
        int temp = getRange();
        setRange(currentRange);
        super.attack(attackFrame, attackTime);
        setRange(temp);
    }

    // When charging, set range depending on how charged the bow is
    @Override
    public void charge(int chargeFrame, int chargeTime){
        super.charge(chargeFrame, chargeTime);
        currentRange = minRange + (getRange()-minRange)*chargeFrame/chargeTime;
    }

    public boolean charged() {
        return currentRange == getRange();
    }

    public int getMinRange() {
        return minRange;
    }

    public double getSpread() {
        return Math.toRadians(spread*(Math.random()-0.5));
    }

    public void setSpread(double spread) {
        this.spread = spread;
    }
}
