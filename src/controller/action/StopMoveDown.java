package controller.action;

import model.Player;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class StopMoveDown extends AbstractAction {
    private Player player;

    public StopMoveDown(Player player){
        this.player = player;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(player.getMovementDirection().y == -1) {
            player.setMovementDirection(0, 0);
        }
    }
}
