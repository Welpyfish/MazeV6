package model.item;

import model.Animation;
import model.ImageLoader;

public class HpItem extends Item {
    private int amount;
    public HpItem(int x, int y, int amount) {
        super(x, y, ImageLoader.getAnimation("heart"+amount));
        this.amount = amount;
    }

    @Override
    public int getAmount() {
        return amount;
    }
}
