/*
 * Final Project
 * Maze
 * William Zhou
 * 2023-06-19
 * ICS4UI-4
 *
 * The Item class is the base class for all items
 */

package model.item;

import model.Animation;
import model.Sprite;
import model.weapon.ProjectileType;
import model.weapon.WeaponType;

public class Item extends Sprite {
    private ItemType itemType;
    private WeaponType weaponType;
    private ProjectileType projectileType;
    private int amount;

    public Item(int x, int y, ItemType itemType, Animation animation) {
        super(x, y, animation);
        this.itemType = itemType;
        weaponType = null;
        projectileType = null;
        //amount = 0;
    }

    // Return weapon type
    public WeaponType getWeaponType() {
        return weaponType;
    }

    // Return projectile type
    public ProjectileType getProjectileType() {
        return projectileType;
    }

    public ItemType getItemType() {
        return itemType;
    }

    // Return amount
    public int getAmount() {
        return amount;
    }

    public Item itemType(ItemType itemType){
        this.itemType = itemType;
        return this;
    }

    public Item weaponType(WeaponType weaponType){
        this.weaponType = weaponType;
        return this;
    }

    public Item projectileType(ProjectileType projectileType){
        this.projectileType = projectileType;
        return this;
    }

    public Item amount(int amount) {
        this.amount = amount;
        return this;
    }
}
