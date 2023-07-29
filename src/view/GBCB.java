/*
 * Final Project
 * Maze
 * William Zhou
 * 2023-06-19
 * ICS4UI-4
 *
 * The GCBC is a builder class that creates GridBagConstraints for gridbaglayouts
 */

package view;

import java.awt.*;

public class GBCB extends GridBagConstraints{

    public GBCB(int x, int y){
        this.gridx = x;
        this.gridy = y;
        this.fill = GridBagConstraints.BOTH;
    }

    public GBCB width(int w){
        this.gridwidth = w;
        return this;
    }

    public GBCB height(int h){
        this.gridheight = h;
        return this;
    }

    public GBCB weightx(double w){
        this.weightx = w;
        return this;
    }

    public GBCB weighty(double w){
        this.weighty = w;
        return this;
    }

    public GBCB anchor(int a){
        this.anchor = a;
        return this;
    }

    public GBCB fill(int fill){
        this.fill = fill;
        return this;
    }

    public GBCB insets(int t, int l, int b, int r){
        this.insets = new Insets(t, l, b, r);
        return this;
    }
}
