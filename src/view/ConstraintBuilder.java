package view;

import java.awt.*;

public class ConstraintBuilder extends GridBagConstraints{

    public ConstraintBuilder(int x, int y){
        this.gridx = x;
        this.gridy = y;
        this.fill = VERTICAL;
//        this.weightx = 0.1;
//        this.weighty = 0.1;
    }

    public ConstraintBuilder width(int w){
        this.gridwidth = w;
        return this;
    }

    public ConstraintBuilder height(int h){
        this.gridheight = h;
        return this;
    }

    public ConstraintBuilder weightx(double w){
        this.weightx = w;
        return this;
    }

    public ConstraintBuilder weighty(double w){
        this.weighty = w;
        return this;
    }

    public ConstraintBuilder anchor(int a){
        this.anchor = a;
        return this;
    }

    public ConstraintBuilder fill(int fill){
        this.fill = fill;
        return this;
    }
}
