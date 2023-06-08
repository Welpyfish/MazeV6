package controller;

import model.*;

import java.awt.*;
import java.awt.event.KeyEvent;

public class MapManager {
    // Map of game objects
    public Map map;
    public Camera camera;
    private Player player;

    public MapManager(Map map){
        this.map = map;
        player = map.player;
        camera = new Camera();
        ImageLoader.loadResources();
    }

    public void setMousePos(int xInScreen, int yInScreen){
        map.setMouse(new Point(xInScreen+camera.getX(),yInScreen+camera.getY()));
    }

    // Update objects in map
    public void updateMap(){
        map.update();
        camera.update(map);
    }
}
