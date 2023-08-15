/*
 * Final Project
 * Maze
 * William Zhou
 * 2023-06-19
 * ICS4UI-4
 *
 * The InventoryItem class describes formatting for inventory buttons
 */

package view;

import model.Constants;

import javax.swing.*;
import java.awt.*;

public class InventoryItem extends JButton {

    public InventoryItem(AbstractAction action){
        super(action);
        setPreferredSize(new Dimension(Constants.iconSize, Constants.iconSize));
        setFocusPainted(false);
        setMargin(new Insets(2, 2, 2, 2));
    }
}
