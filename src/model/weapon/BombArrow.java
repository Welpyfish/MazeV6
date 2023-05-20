package model.weapon;

import model.*;

public class BombArrow extends Arrow{
    int explosionDamage;
    private Animation explosion;

    public BombArrow(Team team, Map map){
        super(team, map);
        explosionDamage = 2;
        explosion = new Animation(ImageLoader.explosion);
    }

    // When colliding, damage all characters in the explosion radius
    @Override
    void destroy(){
        super.destroy();
        for(Enemy enemy : map.enemies){
            if(enemy.distance(getX(), getY())< GameConstants.tileSize*2) {
                enemy.changeHp(-explosionDamage);
            }
        }
        if(map.player.distance(getX(), getY())<GameConstants.tileSize*2) {
            map.player.changeHp(-explosionDamage);
        }
        // Create the explosion effect
        map.effects.add(new VisualEffect(getX(), getY(), explosion));
    }
}
