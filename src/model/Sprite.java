/*
 * Final Project
 * Maze
 * William Zhou
 * 2023-06-19
 * ICS4UI-4
 *
 * The Sprite class is the base class for all sprites
 */

package model;

import java.awt.image.BufferedImage;

public class Sprite {
    private double x, y;
    private double width, height;
    private double vx;
    private double vy;
    private boolean collided;
    private boolean collision;
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

    public Sprite(double x, double y, double width, double height, Animation animation){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.vx = 0;
        this.vy = 0;
        this.collision = false;
        this.remove = false;
        this.animation = animation;
    }

    // Update
    public void update(){
        collided = false;
        // Update animation
        if(animation!=null){
            animation.update();
        }
    }

    public void updateX(){
        x += vx;
    }

    public void updateY(){
        y += vy;
    }

    public boolean intersects(Sprite sprite){
        if(this.x >= sprite.x + sprite.width){
            return false;
        }
        if(this.x + this.width <= sprite.x){
            return false;
        }
        if(this.y >= sprite.y + sprite.height){
            return false;
        }
        if(this.y + this.height <= sprite.y){
            return false;
        }
        return true;
    }

    public void collide(Sprite collider, boolean horizontal){
        collided = true;
        if(horizontal){
            if(vx > 0){
                this.x = collider.x - this.width;
            }else {//if(vx < 0){
                this.x = collider.x + collider.width;
            }
            vx = 0;
        }else{
            if(vy < 0){
                this.y = collider.y + collider.height;
            }else {//if(vy > 0){
                this.y = collider.y - this.height;
            }
            vy = 0;
        }
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int getIntX() {
        return (int) x;
    }

    public int getIntY() {
        return (int) y;
    }

    protected int getCenterX(){
        return (int) (x + width/2);
    }

    protected int getCenterY(){
        return (int) (y + height/2);
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

    public boolean hasCollision() {
        return collision;
    }

    public void setCollision(boolean collision) {
        this.collision = collision;
    }

    public double getVx() {
        return vx;
    }

    public void setVx(double vx) {
        this.vx = vx;
    }

    public double getVy() {
        return vy;
    }

    public void setVy(double vy) {
        this.vy = vy;
    }

    public boolean isCollided() {
        return collided;
    }

    public void setCollided(boolean collided) {
        this.collided = collided;
    }
}
