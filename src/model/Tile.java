package model;

import java.awt.*;
import java.util.Stack;

public class Tile extends Sprite{
    private final int gridx, gridy;
    public TileObject collider;
    private Rectangle rect;

    public Tile(int x, int y){
        super(GameConstants.tileSize*x, GameConstants.tileSize*y);
        this.gridx = x;
        this.gridy = y;
        rect = new Rectangle(this.getX(), this.getY(), GameConstants.tileSize, GameConstants.tileSize);
        collider = null;
    }

    public int getGridx() {
        return gridx;
    }

    public int getGridy() {
        return gridy;
    }
}
