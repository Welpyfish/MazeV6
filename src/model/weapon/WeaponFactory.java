/*
 * Final Project
 * Maze
 * William Zhou
 * 2023-06-19
 * ICS4UI-4
 *
 * The WeaponFactory class uses a factory pattern to create all weapon objects
 */

package model.weapon;

import model.*;

public class WeaponFactory {
    // Map is used when creating projectiles
    private static Map map;

    public WeaponFactory(Map map){
        this.map = map;
    }

    // Create a new weapon given the weapon type and team
    public static Weapon createWeapon(WeaponType weaponType, Team team){
        Weapon weapon = null;
        WeaponStat weaponStat = null;
        // Determine weapon stats
        switch (weaponType){
            case THROW -> weaponStat = new WeaponStat(5, 0,
                    new WeaponID(WeaponClass.THROW, weaponType)).ammoCost(1).minRange(Constants.characterSize);
            case DROP -> weaponStat = new WeaponStat(0, 0,
                    new WeaponID(WeaponClass.THROW, weaponType)).ammoCost(1);
            case SWORD -> weaponStat = new WeaponStat(1.5, 1,
                    new WeaponID(WeaponClass.SWORD, weaponType)).attackTime(0.3).cooldownTime(0.2);
            case GREATSWORD -> weaponStat = new WeaponStat(2.5, 2,
                    new WeaponID(WeaponClass.SWORD, weaponType)).attackTime(0.35).cooldownTime(0.4);
            case SPEAR -> weaponStat = new WeaponStat(3, 1,
                    new WeaponID(WeaponClass.SPEAR, weaponType)).attackTime(0.1).cooldownTime(0.45);
            case BOW -> weaponStat = new WeaponStat(9, 0,
                    new WeaponID(WeaponClass.BOW, weaponType)).chargeTime(0.3).ammoCost(1);
            case GUN -> weaponStat = new WeaponStat(13, 0,
                    new WeaponID(WeaponClass.GUN, weaponType)).cooldownTime(0.8).ammoCost(1);
            case TURRET -> weaponStat = new WeaponStat(999, 0,
                    new WeaponID(WeaponClass.GUN, weaponType)).chargeTime(0.5);
        }

        // Get weapon animation
        Animation animation = ImageLoader.getAnimation(weaponType.toString());

        // Create weapon
        // To make enemies simpler, all shooters used by enemies are actually gun class weapons
        // Enemies may also have other modifications like slower charge times
        switch (weaponType){
            case THROW -> {
                if(team == Team.PLAYER) {
                    weapon = new Throw(weaponStat, team, animation);
                }else{
                    weapon = new Gun(weaponStat.chargeTime(0.4), team, animation);
                }
            }
            case DROP -> {
                if(team == Team.PLAYER) {
                    weapon = new Throw(weaponStat, team, animation);
                }else{
                    weapon = new Gun(weaponStat, team, animation);
                }
            }
            case SWORD, GREATSWORD -> weapon = new Sword(weaponStat, team, animation);
            case SPEAR -> weapon = new Spear(weaponStat, team, animation);
            case BOW -> {
                if(team == Team.PLAYER) {
                    weapon = new Bow(weaponStat, team, animation);
                }else{
                    weapon = new Gun(weaponStat.chargeTime(0.5), team, animation);
                }
            }
            case GUN, TURRET -> weapon = new Gun(weaponStat, team, animation);
        }

        return weapon;
    }

    // Create a projectile given the projectile type and team, and add it to map
    public static Projectile createProjectile(ProjectileType projectileType, Team team){
        Projectile projectile = null;
        ProjectileStat projectileStat = null;
        // Determine the projectile stats
        switch (projectileType){
            case ARROW -> projectileStat = new ProjectileStat(1, 12);
            case ELECTRIC_ARROW -> projectileStat = new ProjectileStat(1, 12).stun(3);
            case BOMB_ARROW -> projectileStat = new ProjectileStat(1, 12).hitRadius(1.2).explosionDamage(2);
            case BULLET -> projectileStat = new ProjectileStat(2, 20);
            case BOMB -> projectileStat = new ProjectileStat(0, 5).hitRadius(1.5).explosionDamage(2);
            case THROWING_SPEAR -> projectileStat = new ProjectileStat(3, 10).range(8);
            case SELF_DESTRUCT -> projectileStat = new ProjectileStat(0, 0).range(0).hitRadius(2).explosionDamage(3);
        }

        // Get projectile animation
        Animation animation = ImageLoader.getAnimation(projectileType.toString());
        // Create projectile
        projectile = new Projectile(projectileStat, team, animation);

        map.projectiles.add(projectile);
        map.sprites.add(projectile);
        return projectile;
    }
}
