package controller.action;

import model.Player;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class StopMoveLeft extends AbstractAction {
    private Player player;

    public StopMoveLeft(Player player){
        this.player = player;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(player.getMovementDirection().x == -1) {
            player.setMovementDirection(0, 0);
        }
    }
}
