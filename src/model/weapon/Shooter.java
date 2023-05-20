package model.weapon;

import model.*;
import model.Character;

import java.util.HashSet;

// A weapon the fires projectiles
public class Shooter extends Weapon{
    // Compatible projectile types
    private HashSet<ProjectileType> projectileTypes;
    // Current projectile type
    private ProjectileType projectileType;
    // Current loaded projectile
    private Projectile projectile;
    //
    private int ammoCount = 0;

    // Creates a shooter
    public Shooter(Team team, Map map){
        super(team, map);
        projectileTypes = new HashSet<>();
    }

    @Override
    public void reset(){
        super.reset();
        if(projectile != null){
            projectile.remove();
            projectile = null;
        }
        projectileType = null;
    }

    // Set the current projectile
    public void setProjectile(ProjectileType projectileType){
        if(this.projectileType != projectileType){
            reset();
        }
        this.projectileType = projectileType;
    }

    // Return the number of projectiles fired in the current frame
    public int ammoUsed(){
        int temp = ammoCount;
        ammoCount = 0;
        return temp;
    }

    // Create a projectile to load
    @Override
    protected void charge(int attackFrame, int attackTime){
        // Create a new projectile if there wasn't one originally
        if(projectileType != null && projectile == null){
            projectile = createProjectile();
        }
        // Update any loaded projectile
        if(projectile != null) {
            projectile.updateWhenLoaded((int) (getX()+ Math.cos(getAngle())*getCurrentImage().getWidth()/2),
                    (int) (getY()+ Math.sin(getAngle())*getCurrentImage().getWidth()/2),
                    getAngle());
        }
    }

    // Launch the loaded projectiles
    @Override
    protected void attack(int attackFrame, int attackTime){
        if(projectile != null) {
            projectile.launch(getRange(), getDamage());
            ammoCount = 1;
            // Only line of map code
            map.projectiles.add(projectile);
            projectile = null;
        }
    }

    // Check if a projectile type is compatible
    public boolean compatibleWith(ProjectileType type){
        return projectileTypes.contains(type);
    }

    // Create a projectile based on projectile type
    private Projectile createProjectile(){
        Projectile result = null;
        switch (projectileType){
            case ARROW -> result = new Arrow(getTeam(), map);
            case BOMB_ARROW -> result = new BombArrow(getTeam(), map);
            case BULLET -> result = new Bullet(getTeam(), map);
            case BOMB -> result = new Bomb(getTeam(), map);
        }
        return result;
    }

    protected void addProjectileType(ProjectileType projectileType){
        projectileTypes.add(projectileType);
    }
}
