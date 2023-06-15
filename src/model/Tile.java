package model;

import java.awt.*;
import java.util.Stack;

public class Tile extends Sprite{
    private final int gridx, gridy;
    private boolean occupied;

    public Tile(int x, int y){
        super(GameConstants.tileSize*x, GameConstants.tileSize*y, ImageLoader.getAnimation("tile"));
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
