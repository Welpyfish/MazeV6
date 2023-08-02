/*
 * Final Project
 * Maze
 * William Zhou
 * 2023-06-19
 * ICS4UI-4
 *
 * The Inventory class holds the player's weapons and keeps track of projectiles
 */

package model;

import model.weapon.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.concurrent.ConcurrentHashMap;

public class Inventory {
    private ArrayList<Integer> keys;
    // Dictionary of all weapons by weapon type
    private LinkedHashMap<WeaponID, Weapon> weapons;
    // Dictionary of the amount of each projectile type
    private LinkedHashMap<ProjectileType, Integer> projectiles;
    // Current selected projectile
    private ProjectileType selectedProjectile;
    private int coins;

    public Inventory(){
        keys = new ArrayList<>();
        // Initialize hashmaps
        weapons = new LinkedHashMap<>();
        projectiles = new LinkedHashMap<>();

        selectedProjectile = null;
    }

    // Reset inventory
    public void reset(){
        keys.clear();
        weapons.clear();
        projectiles.clear();
        selectedProjectile = null;
        coins = 0;
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

    // Get a list of projectile compatible with the given weapon class
    public ConcurrentHashMap<ProjectileType, Integer> getProjectileList(WeaponClass weaponClass){
        ConcurrentHashMap<ProjectileType, Integer> result = new ConcurrentHashMap<>();
        for(ProjectileType type : projectiles.keySet()){
            if(WeaponID.compatible(weaponClass, type)){
                result.put(type, projectiles.get(type));
            }
        }
        return result;
    }

    // Get a list of all weapons that are part of the given weapon class
    public ArrayList<WeaponType> getWeaponList(WeaponClass weaponClass){
        ArrayList<WeaponType> result = new ArrayList<>();
        for(WeaponID id : weapons.keySet()){
            if(id.weaponClass() == weaponClass){
                result.add(id.weaponType());
            }
        }
        return result;
    }

    public void setSelectedProjectile(ProjectileType selectedProjectile) {
        this.selectedProjectile = selectedProjectile;
    }

    public int getProjectileCount(ProjectileType projectileType){
        if(projectiles.containsKey(projectileType)) {
            return projectiles.get(projectileType);
        }
        return 0;
    }

    // Get a weapon using the weapon type
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

    public ArrayList<Integer> getKeys(){
        return keys;
    }

    public void changeCoins(int amount) {
        this.coins += amount;
    }

    public int getCoins() {
        return coins;
    }
}
