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
    public static BufferedImage heart = loadImage("media/heart.png", 1, 1);
    public static ArrayList<BufferedImage> explosion = loadAnimation("media/explosion/image_part_0", 5, 5, 45);
    public static ImageIcon heartIcon = loadIcon("heart", 24, 24);
    public static BufferedImage bow = loadImage("media/bow.png", 1.5, 2);
    public static BufferedImage gun = loadImage("media/gun.png", 1.5, 1);
    public static BufferedImage noWeapon = loadImage("media/noweapon.png");
    public static BufferedImage bullet = loadImage("media/bullet.png", 1);
    public static BufferedImage bomb = loadImage("media/bomb.png", 1, 1);
    public static BufferedImage sword = loadImage("media/sword.png", 2.5);

    public static BufferedImage arrow = loadImage("media/arrow.png", 1);
    public static BufferedImage arrowItem = loadImage("media/arrow.png", 1);

    private static HashMap<String, ArrayList<BufferedImage>> animations = new HashMap<>();

    private ImageLoader(){

    }

    public static void loadResources(){
        animations.put("explosion", loadAnimation("media/explosion/image_part_0", 5, 45));
    }

    public static Animation getAnimation(String path){
        Animation animation;
        if(!animations.containsKey(path)){
            switch (path){
                case "arrow" -> animations.put(path, loadAnimation("media/arrow", 1));
                case "electricarrow" -> animations.put(path, loadAnimation("media/arrow", 1));
                case "bombarrow" -> animations.put(path, loadAnimation("media/arrow", 1));
                case "bullet" -> animations.put(path, loadAnimation("media/bullet", 1));
                case "bomb" -> animations.put(path, loadAnimation("media/bomb", 1));
                case "sword" -> animations.put(path, loadAnimation("media/sword", 2));
                case "noweapon" -> animations.put(path, loadAnimation("media/noweapon", 1));
                case "bow" -> animations.put(path, loadAnimation("media/bow", 2));
                case "gun" -> animations.put(path, loadAnimation("media/gun", 2));
            }

        }
        switch (path){
            case "explosion" -> animation = new Animation(animations.get(path), 1);
            default -> animation = new Animation(animations.get(path));
        }
        return animation;
    }

    public static Animation getProjectileItemAnimation(ProjectileType projectileType){
        Animation result = null;
        switch (projectileType){
            case ARROW -> result = new Animation(arrowItem);
            case ELECTRIC_ARROW -> result = new Animation(arrowItem);
            case BOMB_ARROW -> result = new Animation(arrowItem);
            case BULLET -> result = new Animation(arrowItem);
            case BOMB -> result = new Animation(arrowItem);
            default -> result = new Animation(heart);
        }
        return result;
    }

    public static ImageIcon getProjectileIcon(ProjectileType projectileType){
        ImageIcon result = null;
        switch (projectileType){
            case ARROW -> result = loadIcon("arrow", GameConstants.iconSize, GameConstants.iconSize);
            case ELECTRIC_ARROW -> result = loadIcon("arrow", GameConstants.iconSize, GameConstants.iconSize);
            case BOMB_ARROW -> result = loadIcon("bombarrow", GameConstants.iconSize, GameConstants.iconSize);
            case BULLET -> result = loadIcon("bullet", GameConstants.iconSize, GameConstants.iconSize);
            case BOMB -> result = loadIcon("bomb", GameConstants.iconSize, GameConstants.iconSize);
            default -> result = heartIcon;
        }
        return result;
    }

    public static ImageIcon getWeaponIcon(WeaponType weaponType){
        ImageIcon result = null;
        switch (weaponType){
            case SWORD -> result = loadIcon("sword", GameConstants.iconSize, GameConstants.tileSize);
            case BOW -> result = loadIcon("bow", GameConstants.iconSize, GameConstants.tileSize);
            case GUN -> result = loadIcon("gun", GameConstants.iconSize, GameConstants.tileSize);
            default -> result = heartIcon;
        }
        return result;
    }

    private static ArrayList<BufferedImage> loadAnimation(String firstImage, double width, double height, int length){
        ArrayList<BufferedImage> images = new ArrayList<>();
        for(int i=0; i<length; i++){
            images.add(ImageLoader.loadImage(firstImage+(i+1)+".png", width, height));
        }
        return images;
    }

    private static ArrayList<BufferedImage> loadAnimation(String firstImage, double maxDimension, int length){
        ArrayList<BufferedImage> images = new ArrayList<>();
        for(int i=0; i<length; i++){
            images.add(ImageLoader.loadImage(firstImage+(i+1)+".png", maxDimension));
        }
        return images;
    }

    private static ArrayList<BufferedImage> loadAnimation(String image, double maxDimension){
        ArrayList<BufferedImage> images = new ArrayList<>();
        images.add(ImageLoader.loadImage(image+".png", maxDimension));
        return images;
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

    public static ImageIcon loadIcon(String path, int width, int height){
        Image image = new ImageIcon(ImageLoader.class.getClassLoader().getResource("media/"+path+".png")).getImage();
        ImageIcon icon = new ImageIcon(image.getScaledInstance(width, height, Image.SCALE_DEFAULT));
        return icon;
    }

}
