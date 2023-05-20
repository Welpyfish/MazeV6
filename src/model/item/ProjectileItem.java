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
        super(x, y);
        this.projectileType = projectileType;
        this.amount = amount;
        switch (projectileType){
            case ARROW -> setAnimation(new Animation(ImageLoader.arrow));
            case BOMB_ARROW -> setAnimation(new Animation(ImageLoader.arrow));
            case BULLET -> setAnimation(new Animation(ImageLoader.bullet));
            case BOMB -> setAnimation(new Animation(ImageLoader.bomb));
        }
    }

    public ProjectileType getProjectileType() {
        return projectileType;
    }

    public int getAmount() {
        return amount;
    }
}
