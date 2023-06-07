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
            case NONE -> weaponStat = new WeaponStat(5, 0,
                    new WeaponID(WeaponClass.THROW, weaponType)).ammoCost(1);
            case SWORD -> weaponStat = new WeaponStat(2, 1,
                    new WeaponID(WeaponClass.SWORD, weaponType)).attackTime(0.3).cooldownTime(0.2);
            case SPEAR -> weaponStat = new WeaponStat(3, 1,
                    new WeaponID(WeaponClass.SPEAR, weaponType)).attackTime(0.15).cooldownTime(0.25);
            case BOW -> weaponStat = new WeaponStat(9, 0,
                    new WeaponID(WeaponClass.BOW, weaponType)).chargeTime(0.3).ammoCost(1);
            case GUN -> weaponStat = new WeaponStat(13, 0,
                    new WeaponID(WeaponClass.GUN, weaponType)).cooldownTime(0.8).ammoCost(1);
        }

        switch (weaponType){
            case NONE -> {
                if(team == Team.PLAYER) {
                    weapon = new NoWeapon(weaponStat, team, ImageLoader.getAnimation("noweapon"));
                }else{
                    weapon = new Gun(weaponStat, team, ImageLoader.getAnimation("noweapon"));
                }
            }
            case SWORD -> weapon = new Sword(weaponStat, team, ImageLoader.getAnimation("sword"));
            case SPEAR -> weapon = new Spear(weaponStat, team, ImageLoader.getAnimation("sword"));
            case BOW -> {
                if(team == Team.PLAYER) {
                    weapon = new Bow(weaponStat, team, ImageLoader.getAnimation("bow"));
                }else{
                    weapon = new Gun(weaponStat, team, ImageLoader.getAnimation("bow"));
                }
            }
            case GUN -> weapon = new Gun(weaponStat, team, ImageLoader.getAnimation("gun"));
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

        switch (projectileType){
            case ARROW -> projectile = new Projectile(projectileStat, team, ImageLoader.getAnimation("arrow"));
            case ELECTRIC_ARROW -> projectile = new Projectile(projectileStat, team, ImageLoader.getAnimation("electricarrow"));
            case BOMB_ARROW -> projectile = new Projectile(projectileStat, team, ImageLoader.getAnimation("bombarrow"));
            case BULLET -> projectile = new Projectile(projectileStat, team, ImageLoader.getAnimation("bullet"));
            case BOMB -> projectile = new Projectile(projectileStat, team, ImageLoader.getAnimation("bomb"));
        }

        map.projectiles.add(projectile);
        return projectile;
    }
}
