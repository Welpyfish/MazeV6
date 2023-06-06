package view;

import controller.action.SelectAmmo;
import controller.action.SelectProjectile;
import model.Player;
import model.weapon.ProjectileType;

import javax.swing.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.concurrent.ConcurrentHashMap;

public class ProjectileList extends JPanel {
    private LinkedHashMap<ProjectileType, InventoryItem> projectileButtons;
    private Player player;

    public ProjectileList(Player player){
        super();
        projectileButtons = new LinkedHashMap<>();
        this.player = player;
    }

    public void updateItems(ConcurrentHashMap<ProjectileType, Integer> projectileList){
        ArrayList<ProjectileType> removed = new ArrayList<>();
        // Remove all projectile buttons that are not in inventory
        for(ProjectileType type : projectileButtons.keySet()){
            if(!projectileList.containsKey(type)){
                this.remove(projectileButtons.get(type));
                removed.add(type);
            }
        }
        for(ProjectileType type : removed){
            projectileButtons.remove(type);
        }

        // Match projectile button values to those in inventory
        for(ProjectileType type : projectileList.keySet()){
            if(!projectileButtons.containsKey(type)){
                InventoryItem newProjectile = new InventoryItem(
                        new SelectProjectile(player, type));
                projectileButtons.put(type, newProjectile);
                this.add(newProjectile);
            }
            projectileButtons.get(type).update(player.inventory.getProjectileCount(type));
        }
    }
}
