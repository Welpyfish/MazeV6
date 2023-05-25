package view;

import controller.GameEngine;
import controller.action.SelectAmmo;
import controller.action.SelectProjectile;
import controller.action.SelectWeapon;
import model.*;
import model.weapon.ProjectileType;
import model.weapon.WeaponClass;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.concurrent.ConcurrentHashMap;

public class InventoryUI extends JPanel {
    private GameEngine gameEngine;
    private Map map;
    private Inventory inventory;
    private Player player;
    private GridBagLayout gridBagLayout;

    private LinkedHashMap<ProjectileType, InventoryItem> arrowButtons;
    private JPanel arrowPanel;

    private LinkedHashMap<ProjectileType, InventoryItem> bulletButtons;
    private JPanel bulletPanel;

    private HealthBar hp;
    private InventoryItem bombs;

    private JButton bow;
    private JButton gun;

    public InventoryUI(GameEngine gameEngine){
        this.gameEngine = gameEngine;
        map = gameEngine.mapManager.map;
        player = map.player;
        inventory = player.inventory;
        setPreferredSize(new Dimension(300, 720));
        gridBagLayout = new GridBagLayout();
        this.setLayout(gridBagLayout);

        hp = new HealthBar();
        this.add(hp, new ConstraintBuilder(0, 0).setW(5));

        bow = new JButton(new SelectWeapon(player, "bow"));
        this.add(bow, new ConstraintBuilder(0, 1));

        arrowButtons = new LinkedHashMap<>();
        arrowPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        this.add(arrowPanel, new ConstraintBuilder(1, 1));

        gun = new JButton(new SelectWeapon(player, "gun"));
        this.add(gun, new ConstraintBuilder(0, 2));

        bulletButtons = new LinkedHashMap<>();
        bulletPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        this.add(bulletPanel, new ConstraintBuilder(1, 2));

        bombs = new InventoryItem(new SelectProjectile(player, "bomb"));
        this.add(bombs, new ConstraintBuilder(0, 5));

        setBorder(new LineBorder(Color.black, 3, false));
        //setFocusable(true);
        setVisible(true);

        update();
    }

    public void update(){
        hp.update(player.getHp());
        bombs.update(inventory.getProjectileCount(ProjectileType.BOMB));

        updateProjectiles(arrowPanel, arrowButtons, inventory.getProjectileList(WeaponClass.BOW));
        updateProjectiles(bulletPanel, bulletButtons, inventory.getProjectileList(WeaponClass.GUN));
    }

    private void updateProjectiles(JPanel panel,
                                   LinkedHashMap<ProjectileType, InventoryItem> items,
                              ConcurrentHashMap<ProjectileType, Integer> valueList){

        ArrayList<ProjectileType> removed = new ArrayList<>();
        // Remove all projectile buttons that are not in inventory
        for(ProjectileType type : items.keySet()){
            if(!valueList.containsKey(type)){
                panel.remove(items.get(type));
                removed.add(type);
            }
        }
        for(ProjectileType type : removed){
            items.remove(type);
        }

        // Match projectile button values to those in inventory
        for(ProjectileType type : valueList.keySet()){
            if(!items.containsKey(type)){
                InventoryItem newProjectile = new InventoryItem(
                        new SelectAmmo(player, type));
                items.put(type, newProjectile);
                panel.add(newProjectile);
            }
            items.get(type).update(valueList.get(type));
        }
    }

    public JButton getButton(ProjectileType projectileType){
        switch (projectileType){
            case ARROW -> {
                return arrowButtons.get(ProjectileType.ARROW);
            }
            case BOMB_ARROW -> {
                return arrowButtons.get(ProjectileType.BOMB);
            }
            default -> {
                return null;
            }
        }
    }
}
