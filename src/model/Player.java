/*
 * Final Project
 * Maze
 * William Zhou
 * 2023-06-19
 * ICS4UI-4
 *
 * The Player class is the character controlled by the player
 */

package model;

import model.weapon.*;

import java.awt.*;

public class Player extends Character {
    public Inventory inventory;
    private boolean autoAim;
    private boolean attacking;
    private Point movementDirection;

    public Player(double x, double y, Animation animation, Map map) {
        super(x, y, null, animation, map);
        inventory = new Inventory();
        reset();
    }

    // Reset everything
    public void reset(){
        // Revive if dead
        unremove();

        inventory.reset();

        // Create starting weapons
        inventory.addWeapon(WeaponFactory.createWeapon(WeaponType.THROW, Team.PLAYER));
        setWeapon(inventory.getWeapon(WeaponType.THROW));

        setHp(50);
        setSightRange(Constants.tileSize*15);
        movementDirection = new Point(0, 0);

        setCollision(true);
    }

    // Update
    @Override
    public void update(){
        // Set target direction of movement
        updateMovement(movementDirection.x, movementDirection.y);
        // Update movement and weapon
        super.update();
    }

    // Update weapon
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

    // Select a weapon
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

    public boolean isAttacking(){
        return attacking;
    }

    public void addWeapon(WeaponType weaponType){
        inventory.addWeapon(WeaponFactory.createWeapon(weaponType, Team.PLAYER));
        if(inventory.getWeapon(WeaponType.THROW) == getWeapon() && !attacking){
            setWeapon(inventory.getWeapon(weaponType));
        }
    }
}
