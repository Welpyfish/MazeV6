package model;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Deque;

// basic object
public class Sprite {
    private double x, y;
    private Animation animation;

    public Sprite(double x, double y){
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return (int) x;
    }

    public int getY() {
        return (int) y;
    }

    protected void changeX(double dx){
        x += dx;
    }

    protected void changeY(double dy){
        y += dy;
    }

    protected void setX(double x){
        this.x = x;
    }

    protected void setY(double y){
        this.y = y;
    }

    public BufferedImage getCurrentImage() {
        return animation.getImage();
    }

    protected void setAnimation(Animation animation) {
        this.animation = animation;
    }

    protected Animation getAnimation(){
        return animation;
    }
}
