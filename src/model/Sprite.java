package model;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Deque;

// done round one uml

// basic object
public class Sprite {
    private double x, y;
    private boolean remove;
    private Animation animation;

    public Sprite(double x, double y){
        this.x = x;
        this.y = y;
    }

    public Sprite(double x, double y, Animation animation){
        this.x = x;
        this.y = y;
        this.animation = animation;
    }

    public void update(){
        if(animation!=null){
            animation.update();
        }
    }

    public int getX() {
        return (int) x;
    }

    public int getY() {
        return (int) y;
    }

    public boolean removed(){
        return remove;
    }

    public BufferedImage getCurrentImage() {
        return animation.getImage();
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

    public void remove(){
        remove = true;
    }

    public void unremove(){
        remove = false;
    }

    protected Animation getAnimation(){
        return animation;
    }

    // to be removed
    protected void setAnimation(Animation animation) {
        this.animation = animation;
    }
}
