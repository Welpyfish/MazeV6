/*
 * Final Project
 * Maze
 * William Zhou
 * 2023-06-19
 * ICS4UI-4
 *
 * The MouseController class detects mouse presses during the game to trigger attacks
 */

package controller;

import model.Player;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseController extends MouseAdapter {
    private boolean pressed;
    private Player player;

    public MouseController(Player player){
        pressed = false;
        this.player = player;
    }

    // Start attack and aim towards mouse when pressed
    @Override
    public void mousePressed(MouseEvent e) {
        super.mousePressed(e);
        // Only react if the mouse was previously released
        if(!pressed){
            pressed = true;
            player.setAutoAim(false);
            player.setAttacking(true);
        }
    }

    // Stop attacking when mouse is released
    @Override
    public void mouseReleased(MouseEvent e) {
        super.mouseReleased(e);
        // Only react if the mouse was previously pressed
        if(pressed){
            pressed = false;
            player.setAttacking(false);
        }
    }
}
