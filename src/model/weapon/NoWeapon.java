package model.weapon;

import model.*;
import model.Character;

public final class NoWeapon extends Shooter{
    private int currentRange;

    public NoWeapon(Team team, Map map){
        super(team, map);
        setAnimation(new Animation(ImageLoader.hand));
        setRange(GameConstants.tileSize*5);
        setWeaponID(new WeaponID(WeaponClass.THROW, WeaponType.NONE));
        setDamage(0);
        setAttackTime(2, 2, 10);
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

    // When charging, set range depending on target location
    @Override
    public void charge(int chargeFrame, int chargeTime){
        super.charge(chargeFrame, chargeTime);
        currentRange = (int) (Math.min(getRange(), getDistance()));
    }
}
