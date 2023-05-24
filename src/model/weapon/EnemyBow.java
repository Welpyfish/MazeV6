package model.weapon;

import model.*;

public class EnemyBow extends Shooter {
    // Angle error when firing
    private double spread;
    private int currentRange;
    private final int minRange = GameConstants.tileSize*4;

    public EnemyBow(Team team, Map map){
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

    @Override
    protected void pressAction(){
        super.pressAction();
        super.releaseAction();
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
