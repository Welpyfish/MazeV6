package view;

import controller.action.SelectProjectile;
import controller.action.SelectWeapon;
import model.Player;
import model.weapon.ProjectileType;
import model.weapon.WeaponType;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.concurrent.ConcurrentHashMap;

public class WeaponList extends JPanel {
    private ArrayList<WeaponType> weaponButtons;
    private Player player;

    public WeaponList(Player player){
        super();
        weaponButtons = new ArrayList<>();
        this.player = player;
    }

    public void updateItems(ArrayList<WeaponType> weaponList){
        for(WeaponType type : weaponList){
            if(!weaponButtons.contains(type)){
                InventoryItem newWeapon = new InventoryItem(new SelectWeapon(player, type));
                this.add(newWeapon);
                weaponButtons.add(type);
            }
        }
    }
}
