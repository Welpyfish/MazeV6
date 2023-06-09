package model.item;

import model.Animation;
import model.Sprite;
import model.Tile;
import model.TileObject;
import model.weapon.ProjectileType;
import model.weapon.WeaponType;

public class Item extends Sprite {
    public Item(int x, int y, Animation animation) {
        super(x, y, animation);
    }

    public WeaponType getWeaponType() {
        return null;
    }

    public ProjectileType getProjectileType() {
        return null;
    }

    public int getAmount() {
        return 0;
    }
}
