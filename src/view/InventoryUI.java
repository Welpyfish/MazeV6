package view;

import controller.GameEngine;
import model.*;
import model.weapon.ProjectileType;
import model.weapon.WeaponClass;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

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
    private WeaponList spears;
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
        this.add(hp, new ConstraintBuilder(0, 0).width(5));

        swords = new WeaponList(player);
        this.add(swords, new ConstraintBuilder(0, 1).anchor(GridBagConstraints.WEST));

        spears = new WeaponList(player);
        this.add(spears, new ConstraintBuilder(0, 2).anchor(GridBagConstraints.WEST));

        bows = new WeaponList(player);
        this.add(bows, new ConstraintBuilder(0, 3).anchor(GridBagConstraints.WEST));

        arrows = new ProjectileList(player);
        this.add(arrows, new ConstraintBuilder(1, 3).anchor(GridBagConstraints.WEST));

        guns = new WeaponList(player);
        this.add(guns, new ConstraintBuilder(0, 4).anchor(GridBagConstraints.WEST));

        bullets = new ProjectileList(player);
        this.add(bullets, new ConstraintBuilder(1, 4).anchor(GridBagConstraints.WEST));

        projectiles = new ProjectileList(player);
        this.add(projectiles, new ConstraintBuilder(0, 5).anchor(GridBagConstraints.WEST));

        setBorder(new LineBorder(Color.black, 3, false));
        //setFocusable(true);
        setVisible(true);

        update();
    }

    public void update(){
        hp.update(player.getHp());

        swords.updateItems(inventory.getWeaponList(WeaponClass.SWORD));
        spears.updateItems(inventory.getWeaponList(WeaponClass.SPEAR));
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
