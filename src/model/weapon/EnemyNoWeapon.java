package model.weapon;

import model.*;

public class EnemyNoWeapon extends Shooter{

    public EnemyNoWeapon(Team team, Map map){
        super(team, map);
        setAnimation(new Animation(ImageLoader.hand));
        setRange(GameConstants.tileSize*5);
        setWeaponID(new WeaponID(WeaponClass.THROW, WeaponType.NONE));
        setDamage(0);
        setAttackTime(2, 2, 10);
    }

    @Override
    protected void pressAction(){
        super.pressAction();
        super.releaseAction();
    }
}
