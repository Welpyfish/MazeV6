package view;

import controller.action.SelectProjectile;
import controller.action.SelectWeapon;
import model.Player;
import model.weapon.ProjectileType;
import model.weapon.WeaponType;

import javax.swing.*;
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
                this.add(new JButton(new SelectWeapon(player, type)));
                weaponButtons.add(type);
            }
        }
    }
}
