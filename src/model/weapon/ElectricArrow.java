package model.weapon;

import model.*;
import model.Character;

public class ElectricArrow extends Arrow{

    public ElectricArrow(Team team, Map map){
        super(team, map);
    }

    // Damage the target character and stun it
    @Override
    void attack(Character collider){
        collider.changeHp(-damage);
    }
}
