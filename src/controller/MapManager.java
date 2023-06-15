package controller;

import model.*;

import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class MapManager {
    // Map of game objects
    public Map map;
    public Camera camera;

    private int level;

    public MapManager(Map map){
        this.map = map;
        camera = new Camera();
        ImageLoader.loadResources();

        level = 1;
    }

    public void setMousePos(int xInScreen, int yInScreen){
        map.setMouse(new Point(xInScreen+camera.getX(),yInScreen+camera.getY()));
    }

    public void startGame(){
        map.generateMap(level);
        camera.moveTo(0, 0);
    }

    public void setLevel(int level){
        this.level = level;
    }

    // Update
    public void update(){
        map.update();
        camera.update(map);
        if(map.isGameOver()){
            GameEngine.setGameState(GameState.GAMEOVER);
            Timer timer = new Timer();
            if(map.player.getHp() == 0){
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        GameEngine.setGameState(GameState.LOSE);
                        timer.cancel();
                    }
                }, 2000);
            }else if(level == GameConstants.levelCount){
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        GameEngine.setGameState(GameState.WIN);
                        timer.cancel();
                    }
                }, 2000);
            }else{
                level++;
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        GameEngine.setGameState(GameState.STARTGAME);
                        timer.cancel();
                    }
                }, 2000);
            }
        }
    }
}
