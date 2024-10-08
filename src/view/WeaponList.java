/*
 * Final Project
 * Maze
 * William Zhou
 * 2023-06-19
 * ICS4UI-4
 *
 * The WeaponList class holds the weapon select buttons
 */

package view;

import controller.action.SelectWeapon;
import model.Player;
import model.weapon.WeaponType;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class WeaponList extends JPanel {
    private LinkedHashMap<WeaponType, InventoryItem> weaponButtons;
    private Player player;

    public WeaponList(Player player){
        super();
        setLayout(new GridBagLayout());
        weaponButtons = new LinkedHashMap<>();
        this.player = player;
    }

    // Add or remove buttons to match inventory
    public void updateItems(ArrayList<WeaponType> weaponList, WeaponType selectedWeapon){
        ArrayList<WeaponType> removed = new ArrayList<>();
        // Remove all projectile buttons that are not in inventory
        for(WeaponType type : weaponButtons.keySet()){
            if(!weaponList.contains(type)){
                this.remove(weaponButtons.get(type));
                removed.add(type);
            }
        }
        for(WeaponType type : removed){
            weaponButtons.remove(type);
        }

        for(WeaponType type : weaponList){
            if(!weaponButtons.containsKey(type)){
                InventoryItem newWeapon = new InventoryItem(new SelectWeapon(player, type));
                this.add(newWeapon, new GBCB(GridBagConstraints.RELATIVE, 0).insets(1, 2, 1, 2));
                weaponButtons.put(type, newWeapon);
            }
            if(type == selectedWeapon){
                weaponButtons.get(type).setBackground(new Color(70, 145, 255, 100));
            }else{
                weaponButtons.get(type).setBackground(new Color(215, 235, 255, 100));
            }
        }
    }
}
