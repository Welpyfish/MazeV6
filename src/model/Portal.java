package model;

import java.awt.*;

public class Portal {
    private Tile start, end;
    private Color color;
    public Portal(Tile start, Tile end, Color color) {
        this.start = start;
        this.end = end;
        this.color = color;
    }

    public Tile getStart() {
        return start;
    }

    public Tile getEnd() {
        return end;
    }

    public Color getColor() {
        return color;
    }
}
