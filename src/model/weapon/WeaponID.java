/*
 * Final Project
 * Maze
 * William Zhou
 * 2023-06-19
 * ICS4UI-4
 *
 * The WeaponID class records a weapon's class and type
 * It also checks weapon and projectile compatibility
 */

package model.weapon;

import static model.weapon.WeaponClass.*;

public record WeaponID(WeaponClass weaponClass, WeaponType weaponType) {

    // Check compatibility between a WeaponID and ProjectileType
    public static boolean compatible(WeaponID weaponID, ProjectileType projectileType){
        return compatible(weaponID.weaponClass, projectileType);
    }

    // Check compatibility between a WeaponClass and ProjectileType
    public static boolean compatible(WeaponClass weaponClass, ProjectileType projectileType){
        if(projectileType == null){
            return false;
        }
        switch (projectileType){
            case ARROW, ELECTRIC_ARROW, BOMB_ARROW -> {
                return weaponClass == BOW;
            }
            case BULLET -> {
                return weaponClass == GUN;
            }
            case BOMB, THROWING_SPEAR, SELF_DESTRUCT -> {
                return weaponClass == THROW;
            }
            default -> {
                return false;
            }
        }
    }
}
