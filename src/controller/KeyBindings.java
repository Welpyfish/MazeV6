package controller;

import controller.action.*;
import model.Map;
import model.weapon.ProjectileType;
import view.UIManager;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class KeyBindings {
    public KeyBindings(Map map, UIManager uiManager, GameEngine gameEngine){
        addInput(uiManager, GameState.GAME, "W", "Move up");
        addInput(uiManager, GameState.GAME, "released W", "Stop move up");
        addInput(uiManager, GameState.GAME, "UP", "Move up");
        addInput(uiManager, GameState.GAME, "released UP", "Stop move up");
        addInput(uiManager, GameState.GAME, "A", "Move left");
        addInput(uiManager, GameState.GAME, "released A", "Stop move left");
        addInput(uiManager, GameState.GAME, "LEFT", "Move left");
        addInput(uiManager, GameState.GAME, "released LEFT", "Stop move left");
        addInput(uiManager, GameState.GAME, "S", "Move down");
        addInput(uiManager, GameState.GAME, "released S", "Stop move down");
        addInput(uiManager, GameState.GAME, "DOWN", "Move down");
        addInput(uiManager, GameState.GAME, "released DOWN", "Stop move down");
        addInput(uiManager, GameState.GAME, "D", "Move right");
        addInput(uiManager, GameState.GAME, "released D", "Stop move right");
        addInput(uiManager, GameState.GAME, "RIGHT", "Move right");
        addInput(uiManager, GameState.GAME, "released RIGHT", "Stop move right");

        addInput(uiManager, GameState.GAME, "L", "Auto aim");
        addInput(uiManager, GameState.GAME, "released L", "Auto attack");

        addInput(uiManager, GameState.GAME, "released P", "Pause");
        addInput(uiManager, GameState.PAUSE, "released P", "Resume");
        addInput(uiManager, GameState.GAME, "released ESCAPE", "Pause");
        addInput(uiManager, GameState.PAUSE, "released ESCAPE", "Resume");

        addAction(uiManager, GameState.GAME ,"Move up", new Move(map.player, 0, 1));
        addAction(uiManager, GameState.GAME ,"Stop move up", new StopMove(map.player, 0, 1));
        addAction(uiManager, GameState.GAME ,"Move left", new Move(map.player, -1, 0));
        addAction(uiManager, GameState.GAME ,"Stop move left", new StopMove(map.player, -1, 0));
        addAction(uiManager, GameState.GAME ,"Move down", new Move(map.player, 0, -1));
        addAction(uiManager, GameState.GAME ,"Stop move down", new StopMove(map.player, 0, -1));
        addAction(uiManager, GameState.GAME ,"Move right", new Move(map.player, 1, 0));
        addAction(uiManager, GameState.GAME ,"Stop move right", new StopMove(map.player, 1, 0));

        addAction(uiManager, GameState.GAME ,"Auto aim", new SetAttack(map.player, true, true));
        addAction(uiManager, GameState.GAME ,"Auto attack", new SetAttack(map.player, false, true));

        addAction(uiManager, GameState.GAME ,"Pause", new SetState(GameState.PAUSE));
        addAction(uiManager, GameState.PAUSE ,"Resume", new SetState(GameState.GAME));
    }

    private void addInput(UIManager uiManager, GameState gameState, String key, String action){
        uiManager.getPanel(gameState).getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(key), action);
    }

    private void addAction(UIManager uiManager, GameState gameState, String key, Action action){
        uiManager.getPanel(gameState).getActionMap().put(key, action);
    }
}
