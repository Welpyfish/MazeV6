package view;

import java.awt.*;

public class ConstraintBuilder extends GridBagConstraints{

    public ConstraintBuilder(int x, int y){
        this.gridx = x;
        this.gridy = y;
        this.fill = VERTICAL;
        this.anchor = WEST;
//        this.weightx = 0.1;
//        this.weighty = 0.1;
    }

    public ConstraintBuilder setW(int w){
        this.gridwidth = w;
        return this;
    }

    public ConstraintBuilder setH(int h){
        this.gridheight = h;
        return this;
    }

    public ConstraintBuilder setWeightX(double w){
        this.weightx = w;
        return this;
    }

    public ConstraintBuilder setWeightY(double w){
        this.weighty = w;
        return this;
    }

    public ConstraintBuilder setAnchor(int a){
        this.anchor = a;
        return this;
    }
}
