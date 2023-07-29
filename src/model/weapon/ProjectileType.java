/*
 * Final Project
 * Maze
 * William Zhou
 * 2023-06-19
 * ICS4UI-4
 *
 * The ProjectileType enum holds possible projectile types
 */

package model.weapon;

public enum ProjectileType {
    ARROW,
    ELECTRIC_ARROW,
    BOMB_ARROW,
    BULLET,
    BOMB,
    THROWING_SPEAR;

    @Override
    public String toString(){
        return super.toString().toLowerCase();
    }
}
