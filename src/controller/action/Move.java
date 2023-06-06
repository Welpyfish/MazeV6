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

    @Override
    public void actionPerformed(ActionEvent e) {
        player.setMovementDirection(x, y);
    }
}
