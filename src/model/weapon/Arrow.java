package model.weapon;

import model.*;
import model.Character;

public class Arrow extends Projectile{

    // Create an arrow object
    public Arrow(Team team, Map map){
        super(team, map);
        damage = 1;
        setAnimation(new Animation(ImageLoader.arrow));
        setSpeed(8);
    }

    // Damage the target character
    @Override
    void attack(Character collider){
        collider.changeHp(-damage);
    }
}
