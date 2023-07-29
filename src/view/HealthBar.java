/*
 * Final Project
 * Maze
 * William Zhou
 * 2023-06-19
 * ICS4UI-4
 *
 * The HealthBar class displays the hp of the player
 */

package view;

import model.ImageLoader;

import javax.swing.*;
import java.awt.*;
import java.util.Stack;

public class HealthBar extends JPanel {
    private final Stack<JLabel> hearts;

    public HealthBar(){
        this.setLayout(new GridBagLayout());
        hearts = new Stack<>();
        setVisible(true);
        setPreferredSize(new Dimension(300, 100));
    }

    // Update the number of hearts to match the player
    public void update(int h){
        while(hearts.size()<h){
            hearts.push(new JLabel(ImageLoader.getIcon("heart")));
            this.add(hearts.peek(), new GBCB((hearts.size()-1)%10, (hearts.size()-1)/10).anchor(GridBagConstraints.WEST));
        }
        while(hearts.size()>h){
            this.remove(hearts.pop());
        }
    }
}
