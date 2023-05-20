package controller.action;

import model.ImageLoader;
import model.Player;
import model.weapon.WeaponType;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class SelectWeapon extends AbstractAction {
    private Player player;
    private WeaponType weaponType;

    public SelectWeapon(Player player, String weapon){
        super(null, ImageLoader.loadIcon(weapon, 36, 36));
        this.player = player;
        switch (weapon){
            case "bow" -> this.weaponType = WeaponType.BOW;
            case "gun" -> this.weaponType = WeaponType.GUN;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        player.getWeapon().reset();
        player.setWeapon(player.inventory.getWeapon(weaponType));
    }
}
