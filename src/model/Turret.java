package model;

import model.weapon.ProjectileType;
import model.weapon.Weapon;

import java.awt.*;

public class Turret extends TileObject{
    private Weapon weapon;
    private ProjectileType projectileType;

    public Turret(Tile tile, Weapon weapon, ProjectileType projectileType, double angle, Animation animation) {
        super(tile, true, GameObjectType.TURRET, animation);
        this.weapon = weapon;
        this.projectileType = projectileType;
        setCollision(true);
        setAngle(angle);
    }

    @Override
    public void update(){
        weapon.setProjectile(projectileType);
        weapon.setTrigger(true);
        weapon.setTarget(new Point((int) (getCenterX()+weapon.getMaxRange()*Math.cos(getAngle())),
                (int) (getCenterY()+weapon.getMaxRange()*Math.sin(getAngle()))),
                getCenter());
        weapon.update(getCenterX(), getCenterY());
        super.update();
    }

    public Weapon getWeapon() {
        return weapon;
    }
}
