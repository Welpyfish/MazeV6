/*
 * Final Project
 * Maze
 * William Zhou
 * 2023-06-19
 * ICS4UI-4
 *
 * The NumberedItem class describes buttons with numbers
 */

package view;

import model.Constants;

import javax.swing.*;
import java.awt.*;

public class NumberedItem extends JButton {
    private int amount;

    public NumberedItem(AbstractAction action){
        super(action);
        setPreferredSize(new Dimension(Constants.iconSize, Constants.iconSize));
        setHorizontalTextPosition(AbstractButton.CENTER);
        setVerticalTextPosition(AbstractButton.BOTTOM);
        setFocusPainted(false);
        setMargin(new Insets(2, 2, 2, 2));
        amount = -1;
    }

    public void update(int value){
        if(amount!=value){
            amount = value;
            getAction().putValue(Action.NAME, ""+amount);
        }
    }
}
