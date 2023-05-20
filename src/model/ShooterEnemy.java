package model;

import model.weapon.*;

// An enemy that fires projectiles using a shooter weapon
public class ShooterEnemy extends Enemy {
    private ProjectileType projectileType;

    public ShooterEnemy(Tile tile, Shooter shooter, ProjectileType projectileType, Map map) {
        super(tile, shooter, map);
        // Add spread to shooters later
        // getWeapon().setSpread(5);
        this.projectileType = projectileType;
    }

    @Override
    protected void startCharge(){
        // Add a projectile when charging
        getWeapon().setProjectile(projectileType);
        super.startCharge();
    }

    @Override
    protected void startAttack(){
        if(getWeapon() instanceof Bow){
            // Bows only attack at max range when used by enemies
            if(((Bow)getWeapon()).charged()) {
                super.startAttack();
            }
        }else{
            super.startAttack();
        }
    }

    // Return a shooter for convenience
    @Override
    public Shooter getWeapon(){
        return (Shooter)super.getWeapon();
    }
}
