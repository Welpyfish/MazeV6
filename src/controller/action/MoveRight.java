package controller.action;

import model.Player;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class MoveRight extends AbstractAction {
    private Player player;

    public MoveRight(Player player){
        this.player = player;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        player.setMovementDirection(1, 0);
    }
}
