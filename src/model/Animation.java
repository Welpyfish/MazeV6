/*
 * Final Project
 * Maze
 * William Zhou
 * 2023-06-19
 * ICS4UI-4
 *
 * The Animation class represents a single animation or series of images
 */

package model;

import java.awt.image.BufferedImage;

public class Animation {
    private BufferedImage[] images;
    private int image;
    private int frameTime;
    private int frameCounter;
    private boolean playing;

    public Animation(BufferedImage[] images, double time){
        this(images);
        this.frameTime = (int) (time*Constants.fps/images.length);
    }

    public Animation(BufferedImage[] images) {
        this.images = images;
        image = 0;
        frameTime = 1;
        frameCounter = 0;
        playing = false;
    }

    public Animation(BufferedImage image){
        images = new BufferedImage[]{image};
        this.image = 0;
        frameTime = 0;
        frameCounter = 0;
        playing = false;
    }

    // Update the animation
    public void update(){
        // Update if the animation is not a still image
        if(playing && images.length > 1) {
            // Move to next image when the frame time is up
            if(frameCounter == frameTime) {
                frameCounter = 0;
                // Loop if at the end of the animation
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

    // Set the frame of the animation
    public void setFrame(int frame){
        image = frame;
        frameCounter = 0;
    }

    // Reset the animation
    public void reset(){
        playing = false;
        image = 0;
        frameCounter = 0;
    }

    // Play the animation
    public void play(){
        playing = true;
    }

    // Pause the animation
    public void pause(){
        playing = false;
    }

    // Return true if at the end of the animation
    public boolean atEnd() {
        return image == images.length - 1;
    }

    // Return the current image
    public BufferedImage getImage(){
        return images[image];
    }
}
