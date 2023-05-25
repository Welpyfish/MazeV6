package controller.action;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class ClickButton extends AbstractAction {
    private JButton button;

    public ClickButton(JButton button){
        super();
        this.button = button;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(button!=null) {
            button.doClick();
        }
    }
}
