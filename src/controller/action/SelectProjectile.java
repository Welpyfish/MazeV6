package controller.action;

import model.*;
import model.weapon.*;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class SelectProjectile extends AbstractAction {
    private Player player;
    private ProjectileType projectileType;

    public SelectProjectile(Player player, ProjectileType projectileType){
        super("0");
        this.player = player;
        this.projectileType = projectileType;
        putValue(Action.LARGE_ICON_KEY, ImageLoader.getProjectileIcon(projectileType));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(WeaponID.compatible(player.getWeapon().getWeaponID(), projectileType)){
            player.getWeapon().reset();
        }
        if(WeaponID.compatible(WeaponClass.THROW, projectileType)) {
            player.setWeapon(player.inventory.getWeapon(WeaponType.NONE));
        }
        player.inventory.setSelectedProjectile(projectileType);
    }
}
