package model;

import model.weapon.ProjectileType;
import model.weapon.WeaponType;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class ImageLoader {
    private static ImageLoader instance;

    private static HashMap<String, BufferedImage[]> animations = new HashMap<>();
    private static HashMap<String, ImageIcon> icons = new HashMap<>();

    private ImageLoader(){

    }

    public static void loadResources(){
        animations.put("explosion", loadAnimation("explosion/image_part_0", 5, 5, 45));
    }

    public static Animation getAnimation(String path){
        Animation animation;
        if(!animations.containsKey(path)){
            switch (path){
                case "arrow", "electric_arrow", "bomb_arrow", "bullet", "bomb" ->
                        animations.put(path, loadAnimation(path, 1));
                case "sword", "spear", "bow", "gun", "throwing_spear" ->
                        animations.put(path, loadAnimation(path, 2));
                case "throw" -> animations.put(path, loadAnimation(path, 1));
                case "greatsword" -> animations.put(path, loadAnimation(path, 2.5));
                //case "throw" -> animations.put(path, new BufferedImage[]{null});
                case "heart1" -> animations.put(path, loadAnimation("heart", 1));
                case "heart3" -> animations.put(path, loadAnimation("heart", 1.5));
                case "heart5" -> animations.put(path, loadAnimation("heart", 2));
                case "wall", "tile" -> animations.put(path, loadAnimation(path, 1, 1, 1));
                case "end_portal" -> animations.put(path, loadAnimation(path, 2, 2, 1));
            }
        }
        switch (path){
            case "explosion" -> animation = new Animation(animations.get(path), 1);
            default -> animation = new Animation(animations.get(path));
        }
        return animation;
    }

    public static ImageIcon getIcon(String path){
        ImageIcon icon;
        if(!icons.containsKey(path)){
            switch (path){
                case "heart" -> icons.put(path, loadIcon(path, 20));
                default -> icons.put(path, loadIcon(path, GameConstants.iconSize));
            }
        }
        icon = icons.get(path);
        return icon;
    }

    private static BufferedImage[] loadAnimation(String firstImage, double width, double height, int length){
        BufferedImage[] images = new BufferedImage[length];
        for(int i=0; i<length; i++){
            images[i] = (ImageLoader.loadImage("media/"+firstImage+(i+1)+".png", width, height));
        }
        return images;
    }

    private static BufferedImage[] loadAnimation(String firstImage, double maxDimension, int length){
        BufferedImage[] images = new BufferedImage[length];
        for(int i=0; i<length; i++){
            images[i] = (ImageLoader.loadImage("media/"+firstImage+(i+1)+".png", maxDimension));
        }
        return images;
    }

    private static BufferedImage[] loadAnimation(String image, double maxDimension){
        return new BufferedImage[]{ImageLoader.loadImage("media/"+image+".png", maxDimension)};
    }

    private static BufferedImage loadImage(String path, double width, double height){
        BufferedImage img = null;
        try {
            BufferedImage original = ImageIO.read(ImageLoader.class.getClassLoader().getResource(path));
            int w = (int) (width*GameConstants.tileSize);
            int h = (int) (height*GameConstants.tileSize);
            img = new BufferedImage(w, h, original.getType());
            Graphics2D g2 = img.createGraphics();
            g2.drawImage(original, 0, 0, w, h, null);
            g2.dispose();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return img;
    }

    private static BufferedImage loadImage(String path, double maxDimension){
        BufferedImage img = null;
        try {
            BufferedImage original = ImageIO.read(ImageLoader.class.getClassLoader().getResource(path));
            double scaleFactor = maxDimension*GameConstants.tileSize/Math.max(original.getWidth(), original.getHeight());
            int w = (int) (scaleFactor*original.getWidth());
            int h = (int) (scaleFactor*original.getHeight());
            img = new BufferedImage(w, h, original.getType());
            Graphics2D g2 = img.createGraphics();
            g2.drawImage(original, 0, 0, w, h, null);
            g2.dispose();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return img;
    }

    public static BufferedImage loadImage(String path){
        BufferedImage img = null;
        try {
            img = ImageIO.read(ImageLoader.class.getClassLoader().getResource(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return img;
    }

    private static ImageIcon loadIcon(String path, double maxDimension){
        Image original = new ImageIcon(ImageLoader.class.getClassLoader().getResource("media/"+path+".png")).getImage();
        double scaleFactor = maxDimension/Math.max(original.getWidth(null), original.getHeight(null));
        int width = (int) (scaleFactor*original.getWidth(null));
        int height = (int) (scaleFactor*original.getHeight(null));
        ImageIcon icon = new ImageIcon(original.getScaledInstance(width, height, Image.SCALE_DEFAULT));
        return icon;
    }

}
