package controller.action;

import model.*;
import model.weapon.ProjectileType;
import model.weapon.WeaponType;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class SelectProjectile extends AbstractAction {
    private Player player;
    private ProjectileType projectileType;

    public SelectProjectile(Player player, String ammoType){
        super("0", ImageLoader.loadIcon(ammoType, 36, 36));
        this.player = player;
        switch (ammoType){
            case "bomb" -> this.projectileType = ProjectileType.BOMB;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        player.getWeapon().reset();
        player.setWeapon(player.inventory.getWeapon(WeaponType.NONE));
        player.inventory.setSelectedProjectile(projectileType);
    }
}
