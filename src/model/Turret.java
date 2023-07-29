package model;

import model.weapon.ProjectileType;
import model.weapon.Weapon;

import java.awt.*;

public class Turret extends TileObject{
    private double angle;
    private Weapon weapon;
    private ProjectileType projectileType;

    public Turret(Tile tile, Weapon weapon, ProjectileType projectileType, Animation animation) {
        super(tile, animation);
        this.weapon = weapon;
        this.projectileType = projectileType;
        setCollision(true);

        angle = 0;
    }

    @Override
    public void update(){
        weapon.setProjectile(projectileType);
        //weapon.setTrigger(true);
        weapon.setTarget(new Point((int) (getCenterX()+weapon.getMaxRange()*Math.cos(angle)),
                (int) (getCenterY()+weapon.getMaxRange()*Math.sin(angle))),
                getCenter());
        weapon.update(getCenterX(), getCenterY());
        super.update();
    }
}
