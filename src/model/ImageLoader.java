/*
 * Final Project
 * Maze
 * William Zhou
 * 2023-06-19
 * ICS4UI-4
 *
 * The ImageLoader class manages the loading and storage of all images and animations
 */

package model;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

public class ImageLoader {
    private static HashMap<String, BufferedImage[]> animations = new HashMap<>();
    private static HashMap<String, ImageIcon> icons = new HashMap<>();

    // ImageLoader does not need to be instantiated
    private ImageLoader(){}

    // Load time consuming resources
    public static void loadResources(){
        animations.put("player", loadAnimation("player/player", 1, 1, 4));
        animations.put("explosion", loadAnimation("explosion/image_part_0", 5, 5, 45));
        animations.put("end_portal", loadAnimation("end_portal/image_part_0", 2, 2, 11));
        animations.put("door", loadAnimation("door", 1, 1, 2));
    }

    // Return an Animation associated with the given path
    public static Animation getAnimation(String path){
        Animation animation;
        // Set the time of animations
        switch (path){
            case "player" -> animation = new Animation(animations.get(path), 0.4);
            case "explosion" -> animation = new Animation(animations.get(path), 1);
            case "end_portal" -> animation = new Animation(animations.get(path), 0.5);
            case "door" -> animation = new Animation(animations.get(path), 0);
            default -> {
                // Load the animation if it hasn't been previously loaded
                if(!animations.containsKey(path)){
                    switch (path){
                        case "sword", "spear", "bow", "gun", "throwing_spear" ->
                                animations.put(path, loadAnimation(path, 2, 2));
                        case "greatsword" -> animations.put(path, loadAnimation(path, 2.5, 1));
                        case "heart1" -> animations.put(path, loadAnimation("heart", 1, 1));
                        case "heart3" -> animations.put(path, loadAnimation("heart", 1.5, 1.5));
                        case "heart5" -> animations.put(path, loadAnimation("heart", 2, 2));
                        case "coin1" -> animations.put(path, loadAnimation("coin", 1, 1));
                        case "coin3" -> animations.put(path, loadAnimation("coin", 1.5, 1.5));
                        case "wall", "tile", "enemy" -> animations.put(path, loadAnimation(path, 1, 1, 1));
                        default -> animations.put(path, loadAnimation(path, 1, 1));
                    }
                }
                animation = new Animation(animations.get(path));
            }
        }
        return animation;
    }

    // Return an ImageIcon associated with the given path
    public static ImageIcon getIcon(String path){
        ImageIcon icon;
        if(!icons.containsKey(path)){
            switch (path){
                case "bomb" -> icons.put(path, loadIcon(path, GameConstants.iconSize, GameConstants.iconSize*0.5));
                case "heart", "coin" -> icons.put(path, loadIcon(path, 20, 20));
                case "key", "endkey", "cagekey" -> icons.put(path, loadIcon(path, 35, 35));
                case "controls" -> icons.put(path, loadIcon(path, 220, 220));
                default -> icons.put(path, loadIcon(path, GameConstants.iconSize, GameConstants.iconSize));
            }
        }
        icon = icons.get(path);
        return icon;
    }

    // Load an animation given the path, dimensions, length (number of frames)
    private static BufferedImage[] loadAnimation(String firstImage, double maxWidth, double maxHeight, int length){
        BufferedImage[] images = new BufferedImage[length];
        for(int i=0; i<length; i++){
            images[i] = (ImageLoader.loadImage("media/"+firstImage+(i+1)+".png", maxWidth, maxHeight));
        }
        return images;
    }

    // Load an animation with one image
    private static BufferedImage[] loadAnimation(String image, double maxWidth, double maxHeight){
        return new BufferedImage[]{ImageLoader.loadImage("media/"+image+".png", maxWidth, maxHeight)};
    }

    // Load an image given path and dimensions
    private static BufferedImage loadImage(String path, double maxWidth, double maxHeight){
        BufferedImage img = null;
        try {
            BufferedImage original = ImageIO.read(ImageLoader.class.getClassLoader().getResource(path));
            double scaleFactor = GameConstants.tileSize*Math.min(maxWidth/original.getWidth(), maxHeight/original.getHeight());
            int width = (int) (scaleFactor*original.getWidth());
            int height = (int) (scaleFactor*original.getHeight());
            img = new BufferedImage(width, height, original.getType());
            Graphics2D g2 = img.createGraphics();
            g2.drawImage(original, 0, 0, width, height, null);
            g2.dispose();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return img;
    }

    // Load an image without resizing
    public static BufferedImage loadImage(String path){
        BufferedImage img = null;
        try {
            img = ImageIO.read(ImageLoader.class.getClassLoader().getResource(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return img;
    }

    // Load an image icon with a max dimension
    private static ImageIcon loadIcon(String path, double maxWidth, double maxHeight){
        Image original = new ImageIcon(ImageLoader.class.getClassLoader().getResource("media/"+path+".png")).getImage();
        double scaleFactor = Math.min(maxWidth/original.getWidth(null), maxHeight/original.getHeight(null));
        int width = (int) (scaleFactor*original.getWidth(null));
        int height = (int) (scaleFactor*original.getHeight(null));
        ImageIcon icon = new ImageIcon(original.getScaledInstance(width, height, Image.SCALE_DEFAULT));
        return icon;
    }

}
