package controller;

import controller.action.*;
import model.Map;
import model.weapon.ProjectileType;
import view.UIManager;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class KeyBindings {
    public KeyBindings(Map map, UIManager uiManager, GameEngine gameEngine){
        uiManager.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke("1"), "Select arrow");
        uiManager.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke("2"), "Select bomb arrow");
        uiManager.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke("W"), "Move up");
        uiManager.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke("released W"), "Stop move up");
        uiManager.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke("UP"), "Move up");
        uiManager.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke("released UP"), "Stop move up");
        uiManager.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke("A"), "Move left");
        uiManager.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke("released A"), "Stop move left");
        uiManager.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke("LEFT"), "Move left");
        uiManager.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke("released LEFT"), "Stop move left");
        uiManager.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke("S"), "Move down");
        uiManager.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke("released S"), "Stop move down");
        uiManager.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke("DOWN"), "Move down");
        uiManager.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke("released DOWN"), "Stop move down");
        uiManager.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke("D"), "Move right");
        uiManager.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke("released D"), "Stop move right");
        uiManager.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke("RIGHT"), "Move right");
        uiManager.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke("released RIGHT"), "Stop move right");
        uiManager.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke("L"), "Auto aim");
        uiManager.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke("released L"), "Auto attack");
        uiManager.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke("P"), "Pause");

        uiManager.getActionMap().put("Move up", new Move(map.player, 0, 1));
        uiManager.getActionMap().put("Stop move up", new StopMove(map.player, 0, 1));
        uiManager.getActionMap().put("Move left", new Move(map.player, -1, 0));
        uiManager.getActionMap().put("Stop move left", new StopMove(map.player, -1, 0));
        uiManager.getActionMap().put("Move down", new Move(map.player, 0, -1));
        uiManager.getActionMap().put("Stop move down", new StopMove(map.player, 0, -1));
        uiManager.getActionMap().put("Move right", new Move(map.player, 1, 0));
        uiManager.getActionMap().put("Stop move right", new StopMove(map.player, 1, 0));

        //uiManager.getActionMap().put("Select arrow", new ClickButton(uiManager.getButton(ProjectileType.ARROW)));
        //uiManager.getActionMap().put("Select bomb arrow", new ClickButton(uiManager.getButton(ProjectileType.BOMB_ARROW)));

        uiManager.getActionMap().put("Pause", new SetState(gameEngine, GameState.PAUSE));

        uiManager.getActionMap().put("Auto aim", new SetAttack(map.player, true, true));
        uiManager.getActionMap().put("Auto attack", new SetAttack(map.player, false, true));
    }
}
