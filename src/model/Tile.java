/*
 * Final Project
 * Maze
 * William Zhou
 * 2023-06-19
 * ICS4UI-4
 *
 * The Tile class represents the spaced out locations that objects occupy
 */

package model;

public class Tile extends Sprite{
    private final int gridx, gridy;
    private boolean occupied;

    public Tile(int x, int y){
        super(Constants.tileSize*(x+0.5), Constants.tileSize*(y+0.5), Constants.tileSize, Constants.tileSize, false, GameObjectType.FAKE_WALL, ImageLoader.getAnimation("tile"));
        this.gridx = x;
        this.gridy = y;
        occupied = false;
    }

    public int getGridx() {
        return gridx;
    }

    public int getGridy() {
        return gridy;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }
}
