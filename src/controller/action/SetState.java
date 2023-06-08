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

    @Override
    public void actionPerformed(ActionEvent e) {
        GameEngine.setGameState(gameState);
    }
}
