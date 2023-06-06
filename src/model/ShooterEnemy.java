package model;

import model.weapon.*;

// An enemy that fires projectiles using a shooter weapon
public class ShooterEnemy extends Enemy {
    private ProjectileType projectileType;

    public ShooterEnemy(Tile tile, Weapon shooter, ProjectileType projectileType, Map map) {
        super(tile, shooter, map);
        // Add spread to shooters later
        // getWeapon().setSpread(5);
        this.projectileType = projectileType;
    }

    @Override
    protected void startAttack(){
        // Add a projectile when charging
        getWeapon().setProjectile(projectileType);
        super.startAttack();
    }
}
