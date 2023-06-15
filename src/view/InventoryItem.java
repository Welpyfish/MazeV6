package view;

import model.GameConstants;

import javax.swing.*;
import java.awt.*;

public class InventoryItem extends JButton {

    public InventoryItem(AbstractAction action){
        super(action);
        setMinimumSize(new Dimension(GameConstants.iconSize, GameConstants.iconSize));
        setMaximumSize(new Dimension(GameConstants.iconSize, 2*GameConstants.iconSize));
        setFocusPainted(false);
        setMargin(new Insets(2, 2, 2, 2));
    }
}
