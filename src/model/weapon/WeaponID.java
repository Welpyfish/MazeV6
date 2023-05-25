package model.weapon;

import static model.weapon.WeaponClass.*;

public record WeaponID(WeaponClass weaponClass, WeaponType weaponType) {

    public static boolean compatible(WeaponID weaponID, ProjectileType projectileType){
        switch (projectileType){
            case ARROW, BOMB_ARROW -> {
                return weaponID.weaponClass == BOW;
            }
            case BULLET -> {
                return weaponID.weaponClass == GUN;
            }
            case BOMB -> {
                return weaponID.weaponClass == THROW;
            }
            default -> {
                return false;
            }
        }
    }

    public static boolean compatible(WeaponClass weaponClass, ProjectileType projectileType){
        switch (projectileType){
            case ARROW, BOMB_ARROW -> {
                return weaponClass == BOW;
            }
            case BULLET -> {
                return weaponClass == GUN;
            }
            case BOMB -> {
                return weaponClass == THROW;
            }
            default -> {
                return false;
            }
        }
    }
}
