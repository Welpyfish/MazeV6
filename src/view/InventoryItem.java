package view;

import javax.swing.*;

public class InventoryItem extends JButton {
    private int amount;

    public InventoryItem(AbstractAction action){
        super(action);
        setHorizontalTextPosition(AbstractButton.CENTER);
        setVerticalTextPosition(AbstractButton.BOTTOM);
        setFocusPainted(false);
        amount = -1;
        
    }

    public void update(int value){
        if(amount!=value){
            amount = value;
            getAction().putValue(Action.NAME, ""+amount);
            setVisible(value != 0);
        }
    }
}
