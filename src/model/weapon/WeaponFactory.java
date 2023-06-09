package model.weapon;

import model.*;

public class WeaponFactory {
    private static Map map;

    public WeaponFactory(Map map){
        this.map = map;
    }

    public static Weapon createWeapon(WeaponType weaponType, Team team){
        Weapon weapon = null;
        WeaponStat weaponStat = null;
        switch (weaponType){
            case THROW -> weaponStat = new WeaponStat(5, 0,
                    new WeaponID(WeaponClass.THROW, weaponType)).ammoCost(1);
            case SWORD -> weaponStat = new WeaponStat(2, 1,
                    new WeaponID(WeaponClass.SWORD, weaponType)).attackTime(0.3).cooldownTime(0.2);
            case GREATSWORD -> weaponStat = new WeaponStat(2.5, 2,
                    new WeaponID(WeaponClass.SWORD, weaponType)).attackTime(0.4).cooldownTime(0.4);
            case SPEAR -> weaponStat = new WeaponStat(3, 1,
                    new WeaponID(WeaponClass.SPEAR, weaponType)).attackTime(0.15).cooldownTime(0.4);
            case BOW -> weaponStat = new WeaponStat(9, 0,
                    new WeaponID(WeaponClass.BOW, weaponType)).chargeTime(0.3).ammoCost(1);
            case GUN -> weaponStat = new WeaponStat(13, 0,
                    new WeaponID(WeaponClass.GUN, weaponType)).cooldownTime(0.8).ammoCost(1);
        }

        Animation animation = ImageLoader.getAnimation(weaponType.toString());

        switch (weaponType){
            case THROW -> {
                if(team == Team.PLAYER) {
                    weapon = new NoWeapon(weaponStat, team, animation);
                }else{
                    weapon = new Gun(weaponStat.chargeTime(0.1), team, animation);
                }
            }
            case SWORD, GREATSWORD -> weapon = new Sword(weaponStat, team, animation);
            case SPEAR -> weapon = new Spear(weaponStat, team, animation);
            case BOW -> {
                if(team == Team.PLAYER) {
                    weapon = new Bow(weaponStat, team, animation);
                }else{
                    weapon = new Gun(weaponStat, team, animation);
                }
            }
            case GUN -> weapon = new Gun(weaponStat, team, animation);
        }

        return weapon;
    }

    public static Projectile createProjectile(ProjectileType projectileType, Team team){
        Projectile projectile = null;
        ProjectileStat projectileStat = null;
        switch (projectileType){
            case ARROW -> projectileStat = new ProjectileStat(1, 12);
            case ELECTRIC_ARROW -> projectileStat = new ProjectileStat(1, 12).stun(3.5);
            case BOMB_ARROW -> projectileStat = new ProjectileStat(1, 12).hitRadius(1.2).explosionDamage(2);
            case BULLET -> projectileStat = new ProjectileStat(2, 20);
            case BOMB -> projectileStat = new ProjectileStat(0, 5).hitRadius(1.2).explosionDamage(2);
        }

        Animation animation = ImageLoader.getAnimation(projectileType.toString());

        projectile = new Projectile(projectileStat, team, animation);

        map.projectiles.add(projectile);
        return projectile;
    }
}
