/*
 * Final Project
 * Maze
 * William Zhou
 * 2023-06-19
 * ICS4UI-4
 *
 * The WeaponList class holds the weapon select buttons
 */

package view;

import model.ImageLoader;
import model.Player;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class KeyList extends JPanel {
    private LinkedHashMap<Integer, JLabel> keys;

    public KeyList(){
        super();
        setLayout(new GridBagLayout());
        keys = new LinkedHashMap<>();
    }

    // Add or remove buttons to match inventory
    public void updateItems(ArrayList<Integer> keyList){
        ArrayList<Integer> removed = new ArrayList<>();
        // Remove all projectile buttons that are not in inventory
        for(Integer id : keys.keySet()){
            if(!keyList.contains(id)){
                this.remove(keys.get(id));
                removed.add(id);
            }
        }
        for(Integer id : removed){
            keys.remove(id);
        }

        for(Integer id : keyList){
            if(!keys.containsKey(id)){
                String path = "key";
                if(id == 1){
                    path = "endkey";
                }else if(id > 100){
                    path = "cagekey";
                }
                JLabel jLabel = new JLabel(ImageLoader.getIcon(path));
                jLabel.setBorder(new LineBorder(Color.black, 3, false));
                if(id == 1){
                }
                this.add(jLabel, new GBCB(GridBagConstraints.RELATIVE, 0).insets(1, 2, 1, 2));
                keys.put(id, jLabel);
            }
        }
    }
}
