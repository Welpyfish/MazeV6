package model.item;

import model.Animation;
import model.ImageLoader;
import model.weapon.ProjectileType;
import model.weapon.Weapon;
import model.weapon.WeaponType;

public class WeaponItem extends Item {
    private WeaponType weaponType;

    public WeaponItem(int x, int y, WeaponType weaponType) {
        super(x, y);
        this.weaponType = weaponType;
        switch (weaponType){
            case BOW -> setAnimation(new Animation(ImageLoader.bow));
            case GUN -> setAnimation(new Animation(ImageLoader.gun));
            case NONE -> setAnimation(new Animation(ImageLoader.hand));
            case SWORD -> setAnimation(new Animation(ImageLoader.arrow));
        }
    }

    public WeaponType getWeaponType() {
        return weaponType;
    }
}
