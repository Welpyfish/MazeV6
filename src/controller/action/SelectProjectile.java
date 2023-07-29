/*
 * Final Project
 * Maze
 * William Zhou
 * 2023-06-19
 * ICS4UI-4
 *
 * The SelectProjectile action sets the inventory's selected projectile
 */

package controller.action;

import model.*;
import model.weapon.*;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class SelectProjectile extends AbstractAction {
    private Player player;
    private ProjectileType projectileType;

    public SelectProjectile(Player player, ProjectileType projectileType){
        // The starting number is 0, but will change depending on the number in the inventory
        super("0");
        this.player = player;
        this.projectileType = projectileType;
        putValue(Action.LARGE_ICON_KEY, ImageLoader.getIcon(projectileType.toString()));
    }

    // Select the projectile
    @Override
    public void actionPerformed(ActionEvent e) {
        // Only reset the weapon if the new projectile is compatible
        if(projectileType != player.inventory.getSelectedProjectile() &&
                WeaponID.compatible(player.getWeapon().getWeaponID(), projectileType)){
            player.getWeapon().reset();
        }
        // When selecting throwable projectile, switch to the throw weapon
        if(WeaponID.compatible(WeaponClass.THROW, projectileType)) {
            player.setWeapon(player.inventory.getWeapon(WeaponType.THROW));
        }
        player.inventory.setSelectedProjectile(projectileType);
    }
}
