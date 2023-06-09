package model.weapon;

public enum ProjectileType {
    ARROW,
    ELECTRIC_ARROW,
    BOMB_ARROW,
    BULLET,
    BOMB;

    @Override
    public String toString(){
        return super.toString().toLowerCase();
    }
}
