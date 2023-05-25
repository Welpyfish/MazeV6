package view;

import controller.action.SelectAmmo;
import model.Inventory;
import model.Player;
import model.weapon.ProjectileType;

import javax.swing.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.concurrent.ConcurrentHashMap;

public class ItemList extends JPanel {
    private LinkedHashMap<ProjectileType, InventoryItem> buttons;
    private Player player;

    public ItemList(Player player){
        super();
        buttons = new LinkedHashMap<>();
        this.player = player;
    }

    private void updateItems(ConcurrentHashMap<ProjectileType, Integer> valueList){

        ArrayList<ProjectileType> removed = new ArrayList<>();
        // Remove all projectile buttons that are not in inventory
        for(ProjectileType type : buttons.keySet()){
            if(!valueList.containsKey(type)){
                this.remove(buttons.get(type));
                removed.add(type);
            }
        }
        for(ProjectileType type : removed){
            buttons.remove(type);
        }

        // Match projectile button values to those in inventory
        for(ProjectileType type : valueList.keySet()){
            if(!buttons.containsKey(type)){
                InventoryItem newProjectile = new InventoryItem(
                        new SelectAmmo(player, type));
                buttons.put(type, newProjectile);
                this.add(newProjectile);
            }
            buttons.get(type).update(player.inventory.getProjectileCount(type));
        }
    }
}
