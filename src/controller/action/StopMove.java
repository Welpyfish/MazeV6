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

    @Override
    public void actionPerformed(ActionEvent e) {
        if(player.getMovementDirection().x == x || player.getMovementDirection().y == y) {
            player.setMovementDirection(0, 0);
        }
    }
}
