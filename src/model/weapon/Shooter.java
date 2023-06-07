package model.weapon;

import model.*;
import model.Character;

import java.util.ArrayList;
import java.util.HashSet;

// A weapon the fires projectiles
public class Shooter extends Weapon{
    // Current loaded projectile
    private Projectile projectile;
    //
    private int ammoUsed;

    public Shooter(WeaponStat weaponStat, Team team, Animation animation){
        super(weaponStat, team, animation);
        projectile = null;
//        ammoUsed = 0;
    }

    @Override
    public void reset(){
        super.reset();
        if(projectile != null){
            projectile.remove();
            projectile = null;
        }
    }

    // Set the current projectile
    @Override
    public void setProjectile(ProjectileType projectileType){
        ProjectileType currentType = getProjectileType();
        if(currentType != null && currentType != projectileType){
            reset();
        }
        setProjectileType(projectileType);
        System.out.println(projectileType);
    }

    // Return the number of projectiles fired in the current frame
    @Override
    public int ammoUsed() {
        int temp = ammoUsed;
        ammoUsed = 0;
        return temp;
    }

    @Override
    public void startCharge(){
        // Create a new projectile if there wasn't one originally
        if(getProjectileType() != null && projectile == null){
            projectile = WeaponFactory.createProjectile(getProjectileType(), getTeam());
        }
        super.startCharge();
    }

    @Override
    protected void charge(int attackFrame, int attackTime){
        // Update any loaded projectile
        if(projectile != null) {
            projectile.updateWhenLoaded((int) (getX()+ Math.cos(getAngle())*getCurrentImage().getWidth()/2),
                    (int) (getY()+ Math.sin(getAngle())*getCurrentImage().getWidth()/2),
                    getAngle());
        }
    }

    // Launch the loaded projectile
    @Override
    protected void startAttack(){
        if(projectile != null) {
            projectile.launch(getCurrentRange(), getDamage());
            ammoUsed = getAmmoCost();
            projectile = null;
            setProjectileType(null);
        }
        super.startAttack();
    }
}
