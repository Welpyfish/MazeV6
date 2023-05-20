package model.weapon;

import model.*;
import model.Character;

public class NoWeapon extends Shooter{
    private final int maxRange = GameConstants.tileSize*5;

    public NoWeapon(Team team, Map map){
        super(team, map);
        addProjectileType(ProjectileType.BOMB);
        setAnimation(new Animation(ImageLoader.hand));
        setWeaponType(WeaponType.NONE);
        setDamage(0);
        setAttackTime(2, 2, 10);
    }

    @Override
    protected void charge(int chargeFrame, int chargeTime){
        super.charge(chargeFrame, chargeTime);
        setRange((int) Math.min(maxRange, getDistance()));
    }
}
