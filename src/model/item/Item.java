package model.item;

import model.Sprite;
import model.Tile;
import model.TileObject;

public class Item extends Sprite {
    private boolean collected;
    public Item(int x, int y) {
        super(x, y);
        collected = false;
    }

    public void collect(){
        collected = true;
    }

}
