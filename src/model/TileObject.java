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

public class TileObject extends Sprite{
    protected Tile tile;
    private Rectangle rect;
    private GameObjectType gameObjectType;

    public TileObject(Tile tile, Animation animation){
        super(tile.getIntX(), tile.getIntY(), 36, 36, animation);
        this.tile = tile;
        rect = new Rectangle(getIntX(), getIntY(), 36, 36);
        setCollision(true);
    }

    public TileObject(int x, int y, GameObjectType type, Animation animation){
        super(x, y, 36, 36, animation);
        this.tile = null;
        this.gameObjectType = type;
        rect = new Rectangle(x, y, 36, 36);
        setCollision(true);
    }

    @Override
    public void update(){
        rect.setLocation(getIntX(), getIntY());
    }

    // Find the shortest distance from this rectangle to a point
    public double distance(int x, int y) {
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

    public GameObjectType getGameObjectType() {
        return gameObjectType;
    }
}
