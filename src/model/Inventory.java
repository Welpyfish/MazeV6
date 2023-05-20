package model;

import model.weapon.ProjectileType;
import model.weapon.Shooter;
import model.weapon.Weapon;
import model.weapon.WeaponType;

import java.util.concurrent.ConcurrentHashMap;

public class Inventory {
    // Dictionary of all weapons by weapon type
    private ConcurrentHashMap<WeaponType, Weapon> weapons;
    // Dictionary of the amount of each projectile type
    private ConcurrentHashMap<ProjectileType, Integer> projectiles;
    // Current selected projectile
    private ProjectileType selectedProjectile;

    public Inventory(){
        // Initialize hashmaps
        weapons = new ConcurrentHashMap<>();
        projectiles = new ConcurrentHashMap<>();

        // Starting amount of projectiles
        projectiles.put(ProjectileType.ARROW, 10);
        projectiles.put(ProjectileType.BOMB_ARROW, 1);
        projectiles.put(ProjectileType.BULLET, 5);
        projectiles.put(ProjectileType.BOMB, 0);
        selectedProjectile = ProjectileType.ARROW;
    }

    // Called by player to load a projectile onto a shooter weapon
    public ProjectileType getProjectile(Shooter shooter){
        // Auto select an appropriate ammo type (subject to change)
        if(projectiles.get(selectedProjectile)<=0 || !shooter.compatibleWith(selectedProjectile)) {
            for (ProjectileType type : projectiles.keySet()) {
                if (projectiles.get(type)>0 && shooter.compatibleWith(type)){
                    selectedProjectile = type;
                    break;
                }
            }
        }
        // Return either an available and compatible projectile type or null
        if(projectiles.get(selectedProjectile) > 0 && shooter.compatibleWith(selectedProjectile)){
            return selectedProjectile;
        }
        return null;
    }

    // Return the select projectile type
    public ProjectileType getSelectedProjectile() {
        return selectedProjectile;
    }

    // Modify the number of a type of projectile
    public void changeProjectile(ProjectileType type, int amount){
        projectiles.replace(type, projectiles.get(type)+amount);
    }

    //
    public void setSelectedProjectile(ProjectileType selectedProjectile) {
        this.selectedProjectile = selectedProjectile;
    }

    public int getProjectileCount(ProjectileType projectileType){
        return projectiles.get(projectileType);
    }

    public Weapon getWeapon(WeaponType weaponType) {
        return weapons.get(weaponType);
    }

    public void addWeapon(Weapon weapon) {
        weapons.put(weapon.getWeaponType(), weapon);
    }
}
