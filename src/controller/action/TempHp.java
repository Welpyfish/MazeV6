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

public class TempHp extends AbstractAction {
    private Player player;

    public TempHp(Player player){
        this.player = player;
    }

    // Set the attack trigger and auto aim flags
    @Override
    public void actionPerformed(ActionEvent e) {
        if(player.inventory.getCoins() > 0){
            player.changeHp(1);
            player.inventory.changeCoins(-1);
        }
    }
}
