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
        // If attack button is held, charge weapon
        if(attacking){
            if(getWeapon() instanceof Shooter) {
                ((Shooter)getWeapon()).setProjectile(inventory.getProjectile((Shooter) getWeapon()));
            }
            getWeapon().startCharge();
            if(getWeapon() instanceof Gun) {
                getWeapon().startAttack();
            }
        }
    }

    @Override
    protected void updateWeapon(){
        if(autoAim){
            // If using auto aim, target the nearest enemy
            Enemy closest = null;
            for(Enemy enemy : map.enemies){
                // Distance to the enemy
                double t = Math.hypot(enemy.getX()-getX(), enemy.getY()-getY());
                // If there is no enemy yet or the new enemy is closer and in sight range
                if((closest==null||t < Math.hypot(closest.getX()-getX(), closest.getY()-getY())) &&
                        map.inLineOfSight(this, enemy, getSightRange())){
                    closest = enemy;
                }
            }
            // If there is no target then point towards mouse
            if(closest == null){
                getWeapon().setTarget(map.getMouse().x, map.getMouse().y);
            }else{
                getWeapon().setTarget(closest.getCenterX(), closest.getCenterY());
            }
        }else {
            // When not using auto aim, aim towards mouse
            getWeapon().setTarget(map.getMouse().x, map.getMouse().y);
        }
        super.updateWeapon();
        // If a shooter weapon launched any projectiles, subtract them from inventory
        if(getWeapon() instanceof Shooter){
            inventory.changeProjectile(inventory.getSelectedProjectile(), -((Shooter) getWeapon()).ammoUsed());
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
        if(!attacking && this.attacking){
            // Attack on release weapons
            if(getWeapon() instanceof Bow || getWeapon() instanceof NoWeapon) {
                getWeapon().startAttack();
            }
        }
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
