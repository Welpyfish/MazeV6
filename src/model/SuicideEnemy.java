package model;

import model.weapon.ProjectileType;
import model.weapon.Weapon;
import model.weapon.WeaponFactory;
import model.weapon.WeaponType;
//might be unnecessary for now
public class SuicideEnemy extends Enemy{
    public SuicideEnemy(double x, double y, ProjectileType projectileType, Animation animation, Map map) {
        super(x, y, WeaponFactory.createWeapon(WeaponType.DROP, Team.ENEMY), projectileType, animation, map);
    }
}
