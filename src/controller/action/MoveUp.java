package controller.action;

import model.Player;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class MoveUp extends AbstractAction {
    private Player player;

    public MoveUp(Player player){
        this.player = player;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        player.setMovementDirection(0, 1);
    }
}
