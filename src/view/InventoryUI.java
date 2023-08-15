/*
 * Final Project
 * Maze
 * William Zhou
 * 2023-06-19
 * ICS4UI-4
 *
 * The InventoryUI class displays the inventory and controls
 */

package view;

import model.*;
import model.weapon.WeaponClass;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class InventoryUI extends JPanel {
    private Inventory inventory;
    private Player player;

    private ProjectileList arrows;
    private ProjectileList bullets;
    private ProjectileList projectiles;

    private WeaponList swords;
    private WeaponList spears;
    private WeaponList bows;
    private WeaponList guns;

    private HealthBar hp;
    private JLabel coins;
    private KeyList keyList;

    public InventoryUI(Player player){
        this.player = player;
        inventory = player.inventory;
        setPreferredSize(new Dimension(300, 720));
        this.setLayout(new GridBagLayout());

        hp = new HealthBar();
        hp.setBorder(new LineBorder(Color.black, 1, false));
        this.add(hp, new GBCB(0, 0).anchor(GridBagConstraints.NORTHWEST).weighty(0.1).insets(20, 20, 0, 20).fill(GridBagConstraints.NONE));

        coins = new JLabel("x 0", ImageLoader.getIcon("coin"), SwingConstants.LEFT);
        this.add(coins, new GBCB(0, 1).insets(0, 20, 0, 20));

        keyList = new KeyList();
        keyList.setBorder(new LineBorder(Color.black, 1, false));
        this.add(keyList, new GBCB(0, 2).anchor(GridBagConstraints.NORTHWEST).weighty(0.1).insets(20, 20, 0, 20).fill(GridBagConstraints.VERTICAL));

        JPanel weaponPanel = new JPanel(new GridBagLayout());
        swords = new WeaponList(player);
        weaponPanel.add(swords, new GBCB(0, 1).anchor(GridBagConstraints.NORTHWEST).fill(GridBagConstraints.VERTICAL));

        spears = new WeaponList(player);
        weaponPanel.add(spears, new GBCB(0, 2).anchor(GridBagConstraints.NORTHWEST).fill(GridBagConstraints.VERTICAL));

        JPanel bowPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        bows = new WeaponList(player);
        bowPanel.add(bows);
        arrows = new ProjectileList(player);
        bowPanel.add(arrows);
        weaponPanel.add(bowPanel, new GBCB(0, 3).anchor(GridBagConstraints.NORTHWEST).fill(GridBagConstraints.VERTICAL));

        JPanel gunPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        guns = new WeaponList(player);
        gunPanel.add(guns);
        bullets = new ProjectileList(player);
        gunPanel.add(bullets);
        weaponPanel.add(gunPanel, new GBCB(0, 4).anchor(GridBagConstraints.NORTHWEST).fill(GridBagConstraints.VERTICAL));

        projectiles = new ProjectileList(player);
        weaponPanel.add(projectiles, new GBCB(0, 5).anchor(GridBagConstraints.NORTHWEST).fill(GridBagConstraints.VERTICAL));

        weaponPanel.add(Box.createVerticalGlue(), new GBCB(10, 10).weightx(1).weighty(1));
        this.add(weaponPanel, new GBCB(0, 3).weightx(0.8).weighty(0.8).anchor(GridBagConstraints.NORTHWEST).insets(40, 20, 60, 20));

        // Controls
        //this.add(new JLabel(ImageLoader.getIcon("controls")), new GBCB(0, 4).weighty(0.1).anchor(GridBagConstraints.SOUTHWEST));

        this.setBorder(new LineBorder(Color.black, 3, false));

        setVisible(true);

        update();
    }

    // Update values
    public void update(){
        hp.update(player.getHp());
        if(!coins.getText().equals("x "+ player.inventory.getCoins())){
            coins.setText("x "+ player.inventory.getCoins());
        }
        keyList.updateItems(player.inventory.getKeys());

        swords.updateItems(inventory.getWeaponList(WeaponClass.SWORD), player.getWeapon().getWeaponID().weaponType());
        spears.updateItems(inventory.getWeaponList(WeaponClass.SPEAR), player.getWeapon().getWeaponID().weaponType());
        bows.updateItems(inventory.getWeaponList(WeaponClass.BOW), player.getWeapon().getWeaponID().weaponType());
        guns.updateItems(inventory.getWeaponList(WeaponClass.GUN), player.getWeapon().getWeaponID().weaponType());

        arrows.updateItems(inventory.getProjectileList(WeaponClass.BOW), inventory.getSelectedProjectile());
        bullets.updateItems(inventory.getProjectileList(WeaponClass.GUN), inventory.getSelectedProjectile());
        projectiles.updateItems(inventory.getProjectileList(WeaponClass.THROW), inventory.getSelectedProjectile());
    }
}
