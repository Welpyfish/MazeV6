/*
 * Final Project
 * Maze
 * William Zhou
 * 2023-06-19
 * ICS4UI-4
 *
 * The SetState class sets the GameEngine GameState
 */

package controller.action;

import controller.GameEngine;
import controller.GameState;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class SetState extends AbstractAction {
    private GameState gameState;

    public SetState(GameState gameState){
        super();
        this.gameState = gameState;
    }

    // Set the game state
    @Override
    public void actionPerformed(ActionEvent e) {
        GameEngine.setGameState(gameState);
    }
}
