/*
 * Final Project
 * Maze
 * William Zhou
 * 2023-06-19
 * ICS4UI-4
 *
 * The MapManager class updates the model and manages the level
 */

package controller;

import model.*;

import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class MapManager {
    public Map map;
    public Camera camera;

    private int level;

    public MapManager(Map map){
        this.map = map;
        camera = new Camera();
        // loadResources has to be called once before the game starts to load animations that take time
        ImageLoader.loadResources();

        level = 1;
    }

    // Called from GameEngine to update mouse position
    public void setMousePos(int xInScreen, int yInScreen){
        map.setMouse(new Point(xInScreen+camera.getX(),yInScreen+camera.getY()));
    }

    // (Re)start the current level
    public void startGame(){
        map.generateMap(level);
        camera.moveTo(0, 0);
    }

    // Update map, camera, and level
    public void update(){
        map.update();
        camera.update(map);
        // Manage game over
        if(map.isGameOver()){
            // Pause for 2 seconds when game over before going to next state
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

    public void setLevel(int level){
        this.level = level;
    }
}
