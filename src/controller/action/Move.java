/*
 * Final Project
 * Maze
 * William Zhou
 * 2023-06-19
 * ICS4UI-4
 *
 * The Move action sets the player's target direction
 */

package controller.action;

import model.Player;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class Move extends AbstractAction {
    private Player player;
    private int x, y;

    public Move(Player player, int x, int y){
        this.player = player;
        this.x = x;
        this.y = y;
    }

    // Set the player movement direction
    @Override
    public void actionPerformed(ActionEvent e) {
        player.setMovementDirection(x, y);
    }
}
