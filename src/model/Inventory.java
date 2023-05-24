package model;

import model.weapon.ProjectileType;
import model.weapon.Shooter;
import model.weapon.Weapon;
import model.weapon.WeaponType;

import java.util.LinkedHashMap;
import java.util.concurrent.ConcurrentHashMap;

public class Inventory {
    // Dictionary of all weapons by weapon type
    private LinkedHashMap<WeaponType, Weapon> weapons;
    // Dictionary of the amount of each projectile type
    private LinkedHashMap<ProjectileType, Integer> projectiles;
    // Current selected projectile
    private ProjectileType selectedProjectile;

    public Inventory(){
        // Initialize hashmaps
        weapons = new LinkedHashMap<>();
        projectiles = new LinkedHashMap<>();

        // Starting amount of projectiles
        //projectiles.put(ProjectileType.ARROW, 10);
        projectiles.put(ProjectileType.BOMB_ARROW, 1);
        projectiles.put(ProjectileType.BULLET, 5);
        //projectiles.put(ProjectileType.BOMB, 0);
        selectedProjectile = ProjectileType.ARROW;
    }

    // Called by player to load a projectile onto a shooter weapon
    public ProjectileType getProjectile(Weapon weapon){
        // Auto select an appropriate ammo type (subject to change)
        if(projectiles.get(selectedProjectile)<=0 || !weapon.compatibleWith(selectedProjectile)) {
            for (ProjectileType type : projectiles.keySet()) {
                if (projectiles.get(type)>0 && weapon.compatibleWith(type)){
                    selectedProjectile = type;
                    break;
                }
            }
        }
        // Return either an available and compatible projectile type or null
        if(projectiles.get(selectedProjectile) > 0 && weapon.compatibleWith(selectedProjectile)){
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
        if(projectiles.containsKey(type)) {
            projectiles.replace(type, projectiles.get(type) + amount);
            if(projectiles.get(type) <= 0){
                projectiles.remove(type);
            }
        }else{
            projectiles.put(type, amount);
        }
    }

    //
    public void setSelectedProjectile(ProjectileType selectedProjectile) {
        this.selectedProjectile = selectedProjectile;
    }

    public int getProjectileCount(ProjectileType projectileType){
        if(projectiles.containsKey(projectileType)) {
            return projectiles.get(projectileType);
        }
        return 0;
    }

    public Weapon getWeapon(WeaponType weaponType) {
        return weapons.get(weaponType);
    }

    public void addWeapon(Weapon weapon) {
        weapons.put(weapon.getWeaponType(), weapon);
    }
}
