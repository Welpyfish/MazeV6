package model;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

// uml done
public class Animation {
    private ArrayList<BufferedImage> images;
    private int image;
    private int frameTime;
    private int frameCounter;
    private boolean playing;

    public Animation(ArrayList<BufferedImage> images, double time){
        this(images);
        this.frameTime = (int) (time*GameConstants.fps/images.size());
    }

    public Animation(ArrayList<BufferedImage> images) {
        this.images = images;
        image = 0;
        frameTime = 1;
        frameCounter = 0;
        playing = false;
    }

    public Animation(BufferedImage image){
        images = new ArrayList<>();
        images.add(image);
        this.image = 0;
        frameTime = 0;
        frameCounter = 0;
        playing = false;
    }

    public BufferedImage getImage(){
        return images.get(image);
    }

    public void update(){
        // Update if the animation is not a still image
        if(playing && images.size() > 1) {
            // Move to next image when the frame time is up
            if(frameCounter == frameTime) {
                frameCounter = 0;
                if (atEnd()) {
                    image = 0;
                } else {
                    image++;
                }
            }
            else{
                frameCounter++;
            }
        }
    }

    public void setFrame(int frame){
        image = frame;
        frameCounter = 0;
    }

    //may be removed
    public void reset(){
        playing = false;
        image = 0;
        frameCounter = 0;
    }

    public void play(){
        playing = true;
    }

    public void pause(){
        playing = false;
    }

    public boolean atEnd() {
        return image == images.size() - 1;
    }
}
