package model;

import model.weapon.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.concurrent.ConcurrentHashMap;

public class Inventory {
    // Dictionary of all weapons by weapon type
    private LinkedHashMap<WeaponID, Weapon> weapons;
    // Dictionary of the amount of each projectile type
    private LinkedHashMap<ProjectileType, Integer> projectiles;
    // Current selected projectile
    private ProjectileType selectedProjectile;

    public Inventory(){
        // Initialize hashmaps
        weapons = new LinkedHashMap<>();
        projectiles = new LinkedHashMap<>();

        selectedProjectile = null;
    }

    // Called by player to load a projectile onto a shooter weapon
    public ProjectileType getProjectile(Weapon weapon){
        // Auto select an appropriate ammo type (subject to change)
        if(!projectiles.containsKey(selectedProjectile) ||
                !WeaponID.compatible(weapon.getWeaponID(), selectedProjectile)) {
            selectedProjectile = null;
            for (ProjectileType type : projectiles.keySet()) {
                if (WeaponID.compatible(weapon.getWeaponID(), type)){
                    selectedProjectile = type;
                    break;
                }
            }
        }
        // Return either an available and compatible projectile type or null
        return selectedProjectile;
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
        }else if(amount > 0){
            projectiles.put(type, amount);
        }
    }

    public ConcurrentHashMap<ProjectileType, Integer> getProjectileList(WeaponClass weaponClass){
        ConcurrentHashMap<ProjectileType, Integer> result = new ConcurrentHashMap<>();
        for(ProjectileType type : projectiles.keySet()){
            if(WeaponID.compatible(weaponClass, type)){
                result.put(type, projectiles.get(type));
            }
        }
        return result;
    }

    public ArrayList<WeaponType> getWeaponList(WeaponClass weaponClass){
        ArrayList<WeaponType> result = new ArrayList<>();
        for(WeaponID id : weapons.keySet()){
            if(id.weaponClass() == weaponClass){
                result.add(id.weaponType());
            }
        }
        return result;
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
        for(WeaponID id : weapons.keySet()){
            if(id.weaponType() == weaponType){
                return weapons.get(id);
            }
        }
        return null;
    }

    public void addWeapon(Weapon weapon) {
        if(!weapons.containsKey(weapon.getWeaponID())) {
            weapons.put(weapon.getWeaponID(), weapon);
        }
    }
}
