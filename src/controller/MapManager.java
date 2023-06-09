package controller;

import model.*;

import java.awt.*;
import java.awt.event.KeyEvent;

public class MapManager {
    // Map of game objects
    public Map map;
    public Camera camera;
    private Player player;

    private int level;

    public MapManager(Map map){
        this.map = map;
        player = map.player;
        camera = new Camera();
        ImageLoader.loadResources();

        level = 1;
    }

    public void setMousePos(int xInScreen, int yInScreen){
        map.setMouse(new Point(xInScreen+camera.getX(),yInScreen+camera.getY()));
    }

    public void generateMap(){
        map.generateMap("media/level"+level+".png");
    }

    // Update objects in map
    public void updateMap(){
        map.update();
        camera.update(map);
    }
}
