package controller;

import model.Map;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseController extends MouseAdapter {
    private boolean pressed;
    private Map map;

    public MouseController(Map map){
        pressed = false;
        this.map = map;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        super.mousePressed(e);
        if(!pressed){
            pressed = true;
            map.player.setAutoAim(false);
            map.player.setAttacking(true);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        super.mouseReleased(e);
        if(pressed){
            pressed = false;
            map.player.setAttacking(false);
        }
    }

    public boolean isPressed() {
        return pressed;
    }
}
