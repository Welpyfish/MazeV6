/*
 * Final Project
 * Maze
 * William Zhou
 * 2023-06-19
 * ICS4UI-4
 *
 * The Projectilelist class holds the projectile select buttons
 */

package view;

import controller.action.SelectProjectile;
import model.Player;
import model.weapon.ProjectileType;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.concurrent.ConcurrentHashMap;

public class ProjectileList extends JPanel {
    private LinkedHashMap<ProjectileType, NumberedItem> projectileButtons;
    private Player player;

    public ProjectileList(Player player){
        super(new GridBagLayout());
        projectileButtons = new LinkedHashMap<>();
        this.player = player;
    }

    // Update button values
    public void updateItems(ConcurrentHashMap<ProjectileType, Integer> projectileList, ProjectileType selectedProjectile){
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
                NumberedItem newProjectile = new NumberedItem(
                        new SelectProjectile(player, type));
                projectileButtons.put(type, newProjectile);
                this.add(newProjectile, new GBCB(GridBagConstraints.RELATIVE, 0).insets(1, 2, 1, 2));
            }
            projectileButtons.get(type).update(player.inventory.getProjectileCount(type));
            if(type == selectedProjectile){
                projectileButtons.get(type).setBackground(new Color(70, 145, 255, 100));
            }else{
                projectileButtons.get(type).setBackground(new Color(215, 235, 255, 100));
            }
        }
    }
}
