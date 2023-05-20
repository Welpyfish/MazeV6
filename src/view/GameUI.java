package view;

import controller.GameEngine;
import controller.action.SelectAmmo;
import controller.action.SelectProjectile;
import controller.action.SelectWeapon;
import model.*;
import model.weapon.ProjectileType;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class GameUI extends JPanel {
    private GameEngine gameEngine;
    private Map map;
    private Inventory inventory;
    private Player player;
    private GridBagLayout gridBagLayout;

    private HealthBar hp;
    private InventoryItem arrows;
    private InventoryItem bombArrows;
    private InventoryItem bullets;
    private InventoryItem bombs;

    private JButton bow;
    private JButton gun;

    public GameUI(GameEngine gameEngine){
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

        arrows = new InventoryItem(new SelectAmmo(player, "arrow"));
        this.add(arrows, new ConstraintBuilder(0, 2));

        bombArrows = new InventoryItem(new SelectAmmo(player, "bombarrow"));
        this.add(bombArrows, new ConstraintBuilder(1, 2));

        gun = new JButton(new SelectWeapon(player, "gun"));
        this.add(gun, new ConstraintBuilder(0, 3));

        bullets = new InventoryItem(new SelectAmmo(player, "bullet"));
        this.add(bullets, new ConstraintBuilder(0, 4));

        bombs = new InventoryItem(new SelectProjectile(player, "bomb"));
        this.add(bombs, new ConstraintBuilder(0, 5));

        setBorder(new LineBorder(Color.black, 3, false));
        //setFocusable(true);
        setVisible(true);

        update();
    }

    public void update(){
        hp.update(player.getHp());
        arrows.update(inventory.getProjectileCount(ProjectileType.ARROW));
        bombArrows.update(inventory.getProjectileCount(ProjectileType.BOMB_ARROW));
        bullets.update(inventory.getProjectileCount(ProjectileType.BULLET));
        bombs.update(inventory.getProjectileCount(ProjectileType.BOMB));
    }

    public JButton getButton(UIButton button){
        switch (button){
            case ARROW -> {
                return arrows;
            }
            case BOMB_ARROW -> {
                return bombArrows;
            }
            default -> {
                return null;
            }
        }
    }
}
