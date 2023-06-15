package model.weapon;

import static model.weapon.WeaponClass.*;

public record WeaponID(WeaponClass weaponClass, WeaponType weaponType) {

    public static boolean compatible(WeaponID weaponID, ProjectileType projectileType){
        return compatible(weaponID.weaponClass, projectileType);
    }

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
            case BOMB, THROWING_SPEAR -> {
                return weaponClass == THROW;
            }
            default -> {
                return false;
            }
        }
    }
}
