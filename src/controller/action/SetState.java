package controller.action;

import controller.GameEngine;
import controller.GameState;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class SetState extends AbstractAction {
    private GameEngine gameEngine;
    private GameState gameState;

    public SetState(GameEngine gameEngine, GameState gameState){
        super();
        this.gameEngine = gameEngine;
        this.gameState = gameState;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        gameEngine.setGameState(gameState);
    }
}
