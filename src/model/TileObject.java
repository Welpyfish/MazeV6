/*
 * Final Project
 * Maze
 * William Zhou
 * 2023-06-19
 * ICS4UI-4
 *
 * The TileObject class describes all game objects that stay on the grid
 */

package model;

import java.awt.*;

//unused/removed
public class TileObject extends Sprite{
    protected Tile tile;
    private Rectangle rect;

    public TileObject(Tile tile, boolean collision, GameObjectType type, Animation animation){
        super(tile.getIntX(), tile.getIntY(), 36, 36, collision, type, animation);
        this.tile = tile;
        rect = new Rectangle(getIntX(), getIntY(), 36, 36);
    }

    public TileObject(int x, int y, boolean collision, GameObjectType type, Animation animation){
        super(x, y, 36, 36, collision, type, animation);
        this.tile = null;
        rect = new Rectangle(x, y, 36, 36);
    }

    @Override
    public void update(){
        rect.setLocation(getIntX(), getIntY());
    }

    // Find the shortest distance from this rectangle to a point
    private double distance(int x, int y) {
        int dx = Math.max(Math.max(rect.x - x, 0), x - rect.x-rect.width);
        int dy = Math.max(Math.max(rect.y - y, 0), y - rect.y-rect.height);
        return Math.hypot(dx, dy);
    }

    protected Point getCenter(){
        return new Point(getCenterX(), getCenterY());
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
        rect.setLocation((int) x, getIntY());
    }

    protected void setY(double y){
        super.setY(y);
        rect.setLocation(getIntX(), (int) y);
    }

    protected Rectangle getRect() {
        return rect;
    }

    public int getGridx(){
        return tile.getGridx();
    }

    public int getGridy(){
        return tile.getGridy();
    }
}
