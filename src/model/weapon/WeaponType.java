/*
 * Final Project
 * Maze
 * William Zhou
 * 2023-06-19
 * ICS4UI-4
 *
 * The WeaponType enum holds possible weapon types
 */

package model.weapon;

public enum WeaponType {
    SWORD,
    GREATSWORD,
    SPEAR,
    BOW,
    GUN,
    DROP,
    TURRET,
    THROW;

    @Override
    public String toString(){
        return super.toString().toLowerCase();
    }
}

