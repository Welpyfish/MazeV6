package view;

import model.GameConstants;

import javax.swing.*;
import java.awt.*;

public class InventoryItem extends JButton {

    public InventoryItem(AbstractAction action){
        super(action);
        setPreferredSize(new Dimension(GameConstants.iconSize, GameConstants.iconSize));
        setFocusPainted(false);
        setMargin(new Insets(2, 2, 2, 2));
    }
}
