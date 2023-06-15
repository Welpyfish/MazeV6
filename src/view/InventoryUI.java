package view;

import controller.GameEngine;
import model.*;
import model.weapon.ProjectileType;
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

    public InventoryUI(Player player){
        this.player = player;
        inventory = player.inventory;
        setPreferredSize(new Dimension(300, 720));
        this.setLayout(new GridBagLayout());

        hp = new HealthBar();
        this.add(hp, new ConstraintBuilder(0, 0).width(5));

        swords = new WeaponList(player);
        this.add(swords, new ConstraintBuilder(0, 1).anchor(GridBagConstraints.WEST).fill(GridBagConstraints.VERTICAL));

        spears = new WeaponList(player);
        this.add(spears, new ConstraintBuilder(0, 2).anchor(GridBagConstraints.WEST).fill(GridBagConstraints.VERTICAL));

        JPanel bowPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        bows = new WeaponList(player);
        bowPanel.add(bows);
        arrows = new ProjectileList(player);
        bowPanel.add(arrows);
        this.add(bowPanel, new ConstraintBuilder(0, 3).anchor(GridBagConstraints.WEST).fill(GridBagConstraints.VERTICAL));

        JPanel gunPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        guns = new WeaponList(player);
        gunPanel.add(guns);
        bullets = new ProjectileList(player);
        gunPanel.add(bullets);
        this.add(gunPanel, new ConstraintBuilder(0, 4).anchor(GridBagConstraints.WEST).fill(GridBagConstraints.VERTICAL));

        projectiles = new ProjectileList(player);
        this.add(projectiles, new ConstraintBuilder(0, 5).anchor(GridBagConstraints.WEST).fill(GridBagConstraints.VERTICAL));

        setBorder(new LineBorder(Color.black, 3, false));
        //setFocusable(true);
        setVisible(true);

        update();
    }

    public void update(){
        hp.update(player.getHp());

        swords.updateItems(inventory.getWeaponList(WeaponClass.SWORD), player.getWeapon().getWeaponID().weaponType());
        spears.updateItems(inventory.getWeaponList(WeaponClass.SPEAR), player.getWeapon().getWeaponID().weaponType());
        bows.updateItems(inventory.getWeaponList(WeaponClass.BOW), player.getWeapon().getWeaponID().weaponType());
        guns.updateItems(inventory.getWeaponList(WeaponClass.GUN), player.getWeapon().getWeaponID().weaponType());

        arrows.updateItems(inventory.getProjectileList(WeaponClass.BOW), inventory.getSelectedProjectile());
        bullets.updateItems(inventory.getProjectileList(WeaponClass.GUN), inventory.getSelectedProjectile());
        projectiles.updateItems(inventory.getProjectileList(WeaponClass.THROW), inventory.getSelectedProjectile());
    }
}
