package model.weapon;

public enum ProjectileType {
    ARROW,
    ELECTRIC_ARROW,
    BOMB_ARROW,
    BULLET,
    BOMB,
    THROWING_SPEAR;

    @Override
    public String toString(){
        return super.toString().toLowerCase();
    }
}
