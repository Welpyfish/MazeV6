package controller.action;

import model.weapon.ProjectileType;
import model.ImageLoader;
import model.Player;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class SelectAmmo extends AbstractAction {
    private Player player;
    private ProjectileType projectileType;

    public SelectAmmo(Player player, ProjectileType ammoType){
        super("0");
        this.player = player;
        projectileType = ammoType;
        putValue(Action.LARGE_ICON_KEY, ImageLoader.getProjectileIcon(ammoType));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        player.inventory.setSelectedProjectile(projectileType);
    }
}
