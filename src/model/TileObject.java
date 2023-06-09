package model;

import java.awt.*;
// basic object
public class TileObject extends Sprite{
    protected Tile tile;
    private Rectangle rect;

    public TileObject(Tile tile){
        super(tile.getX(), tile.getY());
        this.tile = tile;
        rect = new Rectangle(getX(), getY(), GameConstants.tileSize, GameConstants.tileSize);
    }

    // Find the shortest distance from this rectangle to a point
    public double distance(int x, int y) {
        int dx = Math.max(Math.max(rect.x - x, 0), x - rect.x-rect.width);
        int dy = Math.max(Math.max(rect.y - y, 0), y - rect.y-rect.height);
        return Math.hypot(dx, dy);
    }

    protected int getCenterX(){
        return (int) rect.getCenterX();
    }

    protected int getCenterY(){
        return (int) rect.getCenterY();
    }

    protected void changeX(double dx){
        super.changeX(dx);
        rect.translate((int) dx, 0);
    }

    protected void changeY(double dy){
        super.changeY(dy);
        rect.translate(0, (int) dy);
    }

    protected void setX(double x){
        super.setX(x);
        rect.setLocation((int) x, getY());
    }

    protected void setY(double y){
        super.setY(y);
        rect.setLocation(getX(), (int) y);
    }

    // not good
    protected Rectangle getRect() {
        return rect;
    }
}
