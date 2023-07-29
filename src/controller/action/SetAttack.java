/*
 * Final Project
 * Maze
 * William Zhou
 * 2023-06-19
 * ICS4UI-4
 *
 * The SetAttack action sets the attack and auto aim flags of player
 */

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

    // Set the attack trigger and auto aim flags
    @Override
    public void actionPerformed(ActionEvent e) {
        player.setAttacking(trigger);
        // The auto aim flag doesn't change when the trigger is released
        if(trigger) {
            player.setAutoAim(autoAim);
        }
    }
}
