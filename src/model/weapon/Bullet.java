package model.weapon;

import model.*;
import model.Character;

public class Bullet extends Projectile{

    public Bullet(Team team, Map map){
        super(team, map);
        damage = 2;
        setAnimation(new Animation(ImageLoader.bullet));
        setSpeed(12);
    }

    @Override
    void attack(Character collider){
        collider.changeHp(-damage);
    }
}
