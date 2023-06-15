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
        reset();
    }

    public void reset(){
        unremove();
        inventory.reset();

        // Create starting weapons
        inventory.addWeapon(WeaponFactory.createWeapon(WeaponType.THROW, Team.PLAYER));
        setWeapon(inventory.getWeapon(WeaponType.THROW));

        setHp(5);
        setSightRange(GameConstants.tileSize*15);
        movementDirection = new Point(0, 0);
    }

    @Override
    public void update(){
        // Set target direction of movement
        updateMovement(movementDirection.x, movementDirection.y);
        // Update movement and weapon
        super.update();
//        if(atTile()){
//            // If at a portal, go through it
//            Tile portalTile = map.checkPortals(tile);
//            if(portalTile != null){
//                setLocation(portalTile);
//            }
//        }
    }

    @Override
    protected void updateWeapon(){
        // If attack button is held, load weapon
        if(attacking){
            getWeapon().setProjectile(inventory.getProjectile(getWeapon()));
        }
        getWeapon().setTarget(map.getMouse(), getCenter());
        if(autoAim){
            // If using auto aim, target the nearest enemy
            Point target = map.findClosestTarget(getCenter(), getSightRange(), Team.PLAYER);
            // If there is no target then point towards mouse
            if(target != null){
                getWeapon().setTarget(target, getCenter());
            }
        }
        super.updateWeapon();
        // If a weapon launched any projectiles, subtract them from inventory
        inventory.changeProjectile(inventory.getSelectedProjectile(), - getWeapon().ammoUsed());
    }

    public void setWeapon(Weapon weapon){
        super.setWeapon(weapon);
        if(!WeaponID.compatible(weapon.getWeaponID(), inventory.getSelectedProjectile())){
            inventory.setSelectedProjectile(null);
        }
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
        getWeapon().setTrigger(attacking);
        this.attacking = attacking;
    }

    public void addWeapon(WeaponType weaponType){
        inventory.addWeapon(WeaponFactory.createWeapon(weaponType, Team.PLAYER));
        if(inventory.getWeapon(WeaponType.THROW) == getWeapon() && !attacking){
            setWeapon(inventory.getWeapon(weaponType));
        }
    }
}
