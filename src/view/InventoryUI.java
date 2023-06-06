package view;

import controller.GameEngine;
import controller.action.SelectAmmo;
import controller.action.SelectProjectile;
import controller.action.SelectWeapon;
import model.*;
import model.weapon.ProjectileType;
import model.weapon.Weapon;
import model.weapon.WeaponClass;
import model.weapon.WeaponType;

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

    private ProjectileList arrows;
    private ProjectileList bullets;
    private ProjectileList projectiles;

    private WeaponList swords;
    private WeaponList bows;
    private WeaponList guns;

    private HealthBar hp;

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

        swords = new WeaponList(player);
        this.add(swords, new ConstraintBuilder(0, 1));

        bows = new WeaponList(player);
        this.add(bows, new ConstraintBuilder(0, 2));

        arrows = new ProjectileList(player);
        this.add(arrows, new ConstraintBuilder(1, 2));

        guns = new WeaponList(player);
        this.add(guns, new ConstraintBuilder(0, 3));

        bullets = new ProjectileList(player);
        this.add(bullets, new ConstraintBuilder(1, 3));

        projectiles = new ProjectileList(player);
        this.add(projectiles, new ConstraintBuilder(0, 4));

        setBorder(new LineBorder(Color.black, 3, false));
        //setFocusable(true);
        setVisible(true);

        update();
    }

    public void update(){
        hp.update(player.getHp());

        swords.updateItems(inventory.getWeaponList(WeaponClass.SWORD));
        bows.updateItems(inventory.getWeaponList(WeaponClass.BOW));
        guns.updateItems(inventory.getWeaponList(WeaponClass.GUN));

        arrows.updateItems(inventory.getProjectileList(WeaponClass.BOW));
        bullets.updateItems(inventory.getProjectileList(WeaponClass.GUN));
        projectiles.updateItems(inventory.getProjectileList(WeaponClass.THROW));
    }

    public JButton getButton(ProjectileType projectileType){
        return null;
    }
}
