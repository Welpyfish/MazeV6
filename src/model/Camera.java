package model;

import model.Map;

public class Camera {
    private double x;
    private double y;
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
        this.x = l;
        this.y = t;
    }

    public void moveTo(double x, double y){
        this.l = x;
        this.r = x+ width;
        this.t =y;
        this.b = y+height;
        this.x = l;
        this.y = t;
    }

    public int getX() {
        return (int) x;
    }

    public void update(Map map){
        double dx = 0;
        double dy = 0;
        if(map.player.getVx() > 0 &&
                map.player.getX()+map.player.getRect().width >= r-rm &&
                x < map.tileMap.length*GameConstants.tileSize - width){
            dx = map.player.getVx();
        } else if(map.player.getVx() < 0 && map.player.getX() <= l+lm &&
                x > 0){
            dx = map.player.getVx();
        }
        if(map.player.getVy() < 0 &&
                map.player.getY() +map.player.getRect().height >= b-bm &&
                y < map.tileMap[0].length*GameConstants.tileSize - height){
            dy = map.player.getVy();
        } else if(map.player.getVy() > 0 &&
                map.player.getY() <= t+tm &&
                y > 0){
            dy = map.player.getVy();
        }
        move(dx, dy);
    }

    public int getY() {
        return (int) y;
    }
}