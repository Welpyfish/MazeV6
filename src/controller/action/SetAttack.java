package controller.action;

import model.Player;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class SetAttack extends AbstractAction {
    private Player player;
    private boolean autoAim;
    private boolean trigger;

    public SetAttack(Player player, boolean trigger, boolean autoAim){
        this.player = player;
        this.trigger = trigger;
        this.autoAim = autoAim;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        player.setAttacking(trigger);
        if(trigger) {
            player.setAutoAim(autoAim);
        }
    }
}
