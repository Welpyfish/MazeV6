package model;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class ImageLoader {
    private static ImageLoader instance;
    public static BufferedImage arrow = loadImage("media/arrow.png", 1);
    public static BufferedImage heart = loadImage("media/heart.png", 1, 1);
    public static ArrayList<BufferedImage> explosion = loadAnimation("media/explosion/image_part_0", 5, 5, 45);
    public static ImageIcon heartIcon = loadIcon("heart", 24, 24);
    public static BufferedImage bow = loadImage("media/bow.png", 1.5, 2);
    public static BufferedImage gun = loadImage("media/gun.png", 1.5, 1);
    public static BufferedImage hand = loadImage("media/noweapon.png", 2, 2);
    public static BufferedImage bullet = loadImage("media/bullet.png", 1.5, 1);
    public static BufferedImage bomb = loadImage("media/bomb.png", 1, 1);

    private ImageLoader(){

    }

    private static ArrayList<BufferedImage> loadAnimation(String firstImage, double width, double height, int length){
        ArrayList<BufferedImage> images = new ArrayList<>();
        for(int i=0; i<length; i++){
            images.add(ImageLoader.loadImage(firstImage+(i+1)+".png", width, height));
        }
        return images;
    }

    public static BufferedImage loadImage(String path, double width, double height){
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

    public static BufferedImage loadImage(String path, double maxDimension){
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

    public static ImageIcon loadIcon(String path, int width, int height){
        Image image = new ImageIcon(ImageLoader.class.getClassLoader().getResource("media/"+path+".png")).getImage();
        ImageIcon icon = new ImageIcon(image.getScaledInstance(width, height, Image.SCALE_DEFAULT));
        return icon;
    }

}
