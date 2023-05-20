package model;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Animation {
    protected ArrayList<BufferedImage> images;
    private int image;
    private boolean play;

    public Animation(ArrayList<BufferedImage> images) {
        this.images = images;
        image = 0;
        play = false;
    }

    public Animation(BufferedImage image){
        images = new ArrayList<>();
        images.add(image);
        this.image = 0;
        play = false;
    }

    public BufferedImage getImage(){
        return images.get(image);
    }

    public void update(){
        if(play) {
            if (image == images.size() - 1) {
                image = 0;
            } else {
                image++;
            }
        }
    }

    public void reset(){
        play = false;
        image = 0;
    }

    public void play(){
        play = true;
    }

    public void pause(){
        play = false;
    }

    public boolean atEnd() {
        return image == images.size() - 1;
    }
}
