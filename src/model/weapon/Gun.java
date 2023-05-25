package model.weapon;

import model.*;
import model.Character;

public class Gun extends Shooter{
    private double spread;

    public Gun(Team team, Map map){
        super(team, map);
        setAnimation(new Animation(ImageLoader.gun));
        setRange(GameConstants.tileSize*12);
        spread = 0;
        setWeaponID(new WeaponID(WeaponClass.GUN, WeaponType.GUN));
        setAttackTime(2, 2, 50);
    }

    @Override
    protected void pressAction(){
        super.pressAction();
        super.releaseAction();
    }

    public double getSpread() {
        return Math.toRadians(spread*(Math.random()-0.5));
    }

    public void setSpread(double spread) {
        this.spread = spread;
    }
}
