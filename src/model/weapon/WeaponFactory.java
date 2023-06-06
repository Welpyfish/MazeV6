package model.weapon;

import model.GameConstants;
import model.ImageLoader;
import model.Map;
import model.Team;

public class WeaponFactory {
    private static Map map;

    public WeaponFactory(Map map){
        this.map = map;
    }

    public static Weapon createWeapon(WeaponType weaponType, Team team){
        Weapon weapon = null;
        switch (weaponType){
            case NONE -> {
                if(team == Team.PLAYER) {
                    weapon = new NoWeapon(
                            new WeaponStat(5, 0, new WeaponID(WeaponClass.THROW, weaponType)).ammoCost(1),
                            team, ImageLoader.getAnimation("noweapon"));
                }else{
                    weapon = new Gun(
                            new WeaponStat(5, 0, new WeaponID(WeaponClass.BOW, weaponType)),
                            team, ImageLoader.getAnimation("noweapon"));
                }
            }
            case SWORD -> weapon = new Sword(
                    new WeaponStat(2, 1, new WeaponID(WeaponClass.SWORD, weaponType)).attackTime(0.3).cooldownTime(0.2),
                    team, ImageLoader.getAnimation("sword"));
            case BOW -> {
                if(team == Team.PLAYER) {
                    weapon = new Bow(
                            new WeaponStat(9, 0, new WeaponID(WeaponClass.BOW, weaponType)).chargeTime(0.3).ammoCost(1),
                            team, ImageLoader.getAnimation("bow"));
                }else{
                    weapon = new Gun(
                            new WeaponStat(9, 0, new WeaponID(WeaponClass.BOW, weaponType)).chargeTime(0.3),
                            team, ImageLoader.getAnimation("bow"));
                }
            }
            case GUN -> weapon = new Gun(
                    new WeaponStat(13, 0, new WeaponID(WeaponClass.GUN, weaponType)).cooldownTime(0.8).ammoCost(1),
                    team, ImageLoader.getAnimation("gun"));
        }
        return weapon;
    }

    public static Projectile createProjectile(ProjectileType projectileType, Team team){
        Projectile projectile = null;
        switch (projectileType){
            case ARROW -> projectile = new Projectile(
                    new ProjectileStat(1, 8),
                    team, ImageLoader.getAnimation("arrow"));
            case ELECTRIC_ARROW -> projectile = new Projectile(
                    new ProjectileStat(1, 8).stun(3.5),
                    team, ImageLoader.getAnimation("electricarrow"));
            case BOMB_ARROW -> projectile = new Projectile(
                    new ProjectileStat(1, 8).hitRadius(1.2).explosionDamage(2),
                    team, ImageLoader.getAnimation("bombarrow"));
            case BULLET -> projectile = new Projectile(
                    new ProjectileStat(2, 12),
                    team, ImageLoader.getAnimation("bullet"));
            case BOMB -> projectile = new Projectile(
                    new ProjectileStat(0, 5).hitRadius(1.2).explosionDamage(2),
                    team, ImageLoader.getAnimation("bomb"));
        }
        map.projectiles.add(projectile);
        return projectile;
    }
}
