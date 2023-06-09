package model.weapon;

public enum WeaponType {
    SWORD,
    GREATSWORD,
    SPEAR,
    BOW,
    GUN,
    THROW;

    @Override
    public String toString(){
        return super.toString().toLowerCase();
    }
}

