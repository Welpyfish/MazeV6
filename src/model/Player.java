package model;

import model.item.HpItem;
import model.item.Item;
import model.item.ProjectileItem;
import model.item.WeaponItem;
import model.weapon.*;

import java.awt.*;

public class Player extends Character {
    public Inventory inventory;
    private boolean autoAim;
    private boolean attacking;
    private Point movementDirection;

    public Player(Tile tile, Map map) {
        super(tile, map);
        inventory = new Inventory();

        // Create starting weapons
        createWeapon(WeaponType.NONE);
        createWeapon(WeaponType.BOW);
        createWeapon(WeaponType.GUN);
        setWeapon(inventory.getWeapon(WeaponType.BOW));

        changeHp(5);
        setSightRange(GameConstants.tileSize*15);
        movementDirection = new Point(0, 0);
    }

    @Override
    public void update(){
        // Set target direction of movement
        updateMovement(movementDirection.x, movementDirection.y);
        // Update movement and weapon
        super.update();
        if(atTile()){
            // If at a portal, go through it
            Tile portalTile = map.checkPortals(tile);
            if(portalTile != null){
                setLocation(portalTile);
            }
        }
        // Check if any items should be collected
        Item item = map.checkItems(getRect());
        if(item!=null){
            if(item instanceof ProjectileItem){
                inventory.changeProjectile(((ProjectileItem) item).getProjectileType(),
                        ((ProjectileItem) item).getAmount());
            } else if(item instanceof WeaponItem){
                createWeapon(((WeaponItem) item).getWeaponType());
            } else if(item instanceof HpItem){
                changeHp(((HpItem) item).getAmount());
            }
        }
    }

    @Override
    protected void updateWeapon(){
        // If attack button is held, load weapon
        if(attacking){
            getWeapon().setProjectile(inventory.getProjectile(getWeapon()));
        }
        if(autoAim){
            // If using auto aim, target the nearest enemy
            Point target = map.findClosestTarget(getCenter(), getSightRange(), Team.PLAYER);
            // If there is no target then point towards mouse
            if(target == null){
                getWeapon().setTarget(map.getMouse());
            }else{
                getWeapon().setTarget(target);
            }
        }else {
            // When not using auto aim, aim towards mouse
            getWeapon().setTarget(map.getMouse());
        }
        super.updateWeapon();
        // If a weapon launched any projectiles, subtract them from inventory
        inventory.changeProjectile(inventory.getSelectedProjectile(), - getWeapon().ammoUsed());
    }

    public void setAutoAim(boolean autoAim) {
        this.autoAim = autoAim;
    }

    public void setMovementDirection(int x, int y){
        movementDirection.setLocation(x, y);
    }

    public Point getMovementDirection(){
        return movementDirection;
    }

    public void setAttacking(boolean attacking) {
        getWeapon().setPressed(attacking);
        this.attacking = attacking;
    }

    // Create a new weapon and put it in inventory
    private void createWeapon(WeaponType weaponType){
        switch (weaponType){
            case NONE -> inventory.addWeapon(new NoWeapon(Team.PLAYER, map));
            case BOW -> inventory.addWeapon(new Bow(Team.PLAYER, map));
            case GUN -> inventory.addWeapon(new Gun(Team.PLAYER, map));
        }
    }
}
