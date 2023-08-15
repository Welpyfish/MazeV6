package model;

import model.weapon.ProjectileType;
import model.weapon.Weapon;

import java.awt.*;

public class Turret extends Sprite{
    private Weapon weapon;
    private ProjectileType projectileType;

    public Turret(Tile tile, Weapon weapon, ProjectileType projectileType, double angle, Animation animation) {
        super(tile.getCenterX(), tile.getCenterY(), Constants.tileSize, Constants.tileSize, true, GameObjectType.TURRET, animation);
        this.weapon = weapon;
        this.projectileType = projectileType;
        setCollision(true);
        if(angle == Math.PI/2){
            setAngle(3*Math.PI/2);
        } else if(angle == 3*Math.PI/2){
            setAngle(Math.PI/2);
        }else {
            setAngle(angle);
        }
    }

    @Override
    public void update(){
        weapon.setProjectile(projectileType);
        weapon.setTrigger(true);
        weapon.setTarget(new Point((int) (getCenterX()+weapon.getMaxRange()*Math.cos(getAngle())),
                (int) (getCenterY()+weapon.getMaxRange()*Math.sin(getAngle()))),
                new Point(getCenterX(), getCenterY()));
        weapon.update(getCenterX(), getCenterY());
        super.update();
    }

    public Weapon getWeapon() {
        return weapon;
    }
}
