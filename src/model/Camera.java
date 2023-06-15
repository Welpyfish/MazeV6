package model;

import model.Map;

import java.awt.*;

public class Camera {
    private int height, width;
    private double l, r, t, b;
    private int lm, rm, tm, bm;

    public Camera(){
        height = 720;
        width = 1000;
        lm = 400;
        rm = 400;
        tm = 300;
        bm = 300;
        moveTo(0, 0);
    }

    public void move(double x, double y){
        this.l += x;
        this.r += x;
        this.t -= y;
        this.b -= y;
    }

    public void moveTo(double x, double y){
        this.l = x;
        this.r = x+ width;
        this.t =y;
        this.b = y+height;
    }

    public int getX() {
        return (int) l;
    }

    public int getY() {
        return (int) t;
    }

    public void update(Map map){
        double dx = 0;
        double dy = 0;
        if(map.player.getVx() > 0 &&
                map.player.getX()+map.player.getRect().width >= r-rm &&
                l < map.tileMap.length*GameConstants.tileSize - width){
            dx = map.player.getVx();
        } else if(map.player.getVx() < 0 && map.player.getX() <= l+lm &&
                l > 0){
            dx = map.player.getVx();
        }
        if(map.player.getVy() < 0 &&
                map.player.getY() +map.player.getRect().height >= b-bm &&
                t < map.tileMap[0].length*GameConstants.tileSize - height){
            dy = map.player.getVy();
        } else if(map.player.getVy() > 0 &&
                map.player.getY() <= t+tm &&
                t > 0){
            dy = map.player.getVy();
        }
        move(dx, dy);
    }
}