package model.weapon;

import model.*;

public class Bomb extends Projectile{
    double explosionRange;

    public Bomb(Team team, Map map){
        super(team, map);
        setAnimation(new Animation(ImageLoader.bomb));
        explosionRange = GameConstants.tileSize*1.2;
        damage = 2;
        setSpeed(4);
    }

    // When colliding, damage all characters in the explosion radius
    @Override
    void destroy(){
        super.destroy();
        for(Enemy enemy : map.enemies){
            if(enemy.distance(getX(), getY())<explosionRange) {
                enemy.changeHp(-damage);
            }
        }
        if(map.player.distance(getX(), getY())<explosionRange) {
            map.player.changeHp(-damage);
        }
        map.effects.add(new VisualEffect(getX(), getY(), new Animation(ImageLoader.explosion)));
    }
}
