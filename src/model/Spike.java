package model;

import java.awt.*;

public class Spike extends Sprite{
    private Polygon triangle;
    private int attackDelay;
    private int attackFrame;
    public Spike(double x, double y, double width, double length, boolean collision, int dps, double direction, GameObjectType type, Animation animation) {
        super(x, y, width, length, collision, type, animation);
        setAngle(direction);
        if(dps != 0) {
            attackDelay = 60 / dps;
        }
        attackFrame = 0;

        triangle = new Polygon();
        if(direction == 0){
            triangle.addPoint((int) x, (int) y);
            triangle.addPoint((int) (x + length), (int) (y + width/2));
            triangle.addPoint((int) x, (int) (y + width));
        } else if(direction == Math.PI/2){
            triangle.addPoint((int) x, (int) (y + length));
            triangle.addPoint((int) (x + width/2), (int) y);
            triangle.addPoint((int) (x + width), (int) (y + length));
            setAngle(3*Math.PI/2);
        } else if(direction == Math.PI){
            triangle.addPoint((int) (x + length), (int) y);
            triangle.addPoint((int) x, (int) (y + width/2));
            triangle.addPoint((int) (x + length), (int) (y + width));
        } else if(direction == 3*Math.PI/2){
            triangle.addPoint((int) x, (int) y);
            triangle.addPoint((int) (x + width/2), (int) (y + length));
            triangle.addPoint((int) (x + width), (int) y);
            setAngle(Math.PI/2);
        }
    }

    @Override
    public void update(){
        if(attackFrame > 0){
            attackFrame--;
        }
        super.update();
    }

    @Override
    public boolean intersects(Sprite sprite){
        return super.intersects(sprite) && triangle.intersects(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
    }

    public int getDamage() {
        if(attackFrame == 0 && attackDelay > 0) {
            attackFrame = attackDelay;
            return 1;
        }
        return 0;
    }
}
