package model.item;

import model.Animation;
import model.ImageLoader;

public class HpItem extends Item {
    private int amount;
    public HpItem(int x, int y, int amount) {
        super(x, y);
        this.amount = amount;
        switch (amount){
            case 1 -> {
                setAnimation(new Animation(ImageLoader.heart));
            }
            case 5 -> {
                setAnimation(new Animation(ImageLoader.heart));
            }
            default -> {
                setAnimation(new Animation(ImageLoader.heart));
            }
        }
    }

    public int getAmount() {
        return amount;
    }
}
