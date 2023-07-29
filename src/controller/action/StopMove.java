/*
 * Final Project
 * Maze
 * William Zhou
 * 2023-06-19
 * ICS4UI-4
 *
 * StopMove class stops the player from moving in a certain direction
 */

package controller.action;

import model.Player;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class StopMove extends AbstractAction {
    private Player player;
    private int x, y;

    public StopMove(Player player, int x, int y){
        this.player = player;
        this.x = x;
        this.y = y;
    }

    // Stop the player if it is moving in the direction stored by this action
    @Override
    public void actionPerformed(ActionEvent e) {
        if(player.getMovementDirection().x == x || player.getMovementDirection().y == y) {
            player.setMovementDirection(0, 0);
        }
    }
}
