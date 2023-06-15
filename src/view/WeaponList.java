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
                this.add(Box.createHorizontalStrut(5));
                this.add(newWeapon);
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
