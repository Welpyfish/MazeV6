package model.item;

import model.Animation;
import model.ImageLoader;
import model.Player;
import model.Tile;
import model.weapon.ProjectileType;

public class ProjectileItem extends Item {
    private ProjectileType projectileType;
    private int amount;

    public ProjectileItem(int x, int y, ProjectileType projectileType, int amount) {
        super(x, y, ImageLoader.getAnimation(projectileType.toString()));
        this.projectileType = projectileType;
        this.amount = amount;
    }

    @Override
    public ProjectileType getProjectileType() {
        return projectileType;
    }

    @Override
    public int getAmount() {
        return amount;
    }
}
