/*
 * Final Project
 * Maze
 * William Zhou
 * 2023-06-19
 * ICS4UI-4
 *
 * The SelectWeapon action sets the player's selected projectile
 */

package controller.action;

import model.ImageLoader;
import model.Player;
import model.weapon.WeaponType;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class SelectWeapon extends AbstractAction {
    private Player player;
    private WeaponType weaponType;

    public SelectWeapon(Player player, WeaponType weaponType){
        super();
        this.player = player;
        this.weaponType = weaponType;
        putValue(Action.LARGE_ICON_KEY, ImageLoader.getIcon(weaponType.toString()));
    }

    // Select the weapon
    @Override
    public void actionPerformed(ActionEvent e) {
        player.getWeapon().reset();
        player.setWeapon(player.inventory.getWeapon(weaponType));
    }
}
