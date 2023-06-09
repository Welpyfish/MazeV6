package model.item;

import model.Animation;
import model.ImageLoader;
import model.weapon.ProjectileType;
import model.weapon.Weapon;
import model.weapon.WeaponType;

public class WeaponItem extends Item {
    private WeaponType weaponType;

    public WeaponItem(int x, int y, WeaponType weaponType) {
        super(x, y, ImageLoader.getAnimation(weaponType.toString()));
        this.weaponType = weaponType;
    }

    @Override
    public WeaponType getWeaponType() {
        return weaponType;
    }
}
