package controller.action;

import model.weapon.ProjectileType;
import model.ImageLoader;
import model.Player;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class SelectAmmo extends AbstractAction {
    private Player player;
    private ProjectileType projectileType;

    public SelectAmmo(Player player, String ammoType){
        super("0", ImageLoader.loadIcon(ammoType, 36, 36));
        this.player = player;
        switch (ammoType){
            case "arrow" -> this.projectileType = ProjectileType.ARROW;
            case "bombarrow" -> this.projectileType = ProjectileType.BOMB_ARROW;
            case "bullet" -> this.projectileType = ProjectileType.BULLET;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        player.inventory.setSelectedProjectile(projectileType);
    }
}
