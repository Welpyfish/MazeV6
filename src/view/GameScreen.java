/*
 * Final Project
 * Maze
 * William Zhou
 * 2023-06-19
 * ICS4UI-4
 *
 * The GameScreen class is where the gameplay is displayed
 */

package view;

import model.*;
import model.Character;
import model.item.*;
import model.weapon.Projectile;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class GameScreen extends JLayeredPane {
    private Camera camera;
    private Map map;
    private PauseScreen pauseScreen;

    public GameScreen(Map map, Camera camera, PauseScreen pauseScreen){
        super();
        setLayout(new BorderLayout());
        this.camera = camera;
        this.map = map;

        this.pauseScreen = pauseScreen;
        this.add(pauseScreen, BorderLayout.CENTER);
        this.setLayer(pauseScreen, POPUP_LAYER);
        this.setPauseScreen(false);

        setPreferredSize(new Dimension(1000, 720));
        setFocusable(true);
        setVisible(true);
    }

    // Set the pause screen to appear or not
    public void setPauseScreen(boolean paused){
        pauseScreen.setVisible(paused);
        if(paused){
            pauseScreen.requestFocusInWindow();
        }
    }

    // Draw
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g.create();
        draw(g2);
        g2.dispose();
    }

    // Draw game objects
    private void draw(Graphics2D g2){
        g2.translate(-camera.getX(), -camera.getY());
        g2.setFont(UIConstants.gameStatFont);
        AffineTransform reset = g2.getTransform();

        drawGrid(g2);

        for(TileObject gameElement : map.gameElements){
            g2.drawImage(gameElement.getCurrentImage(), gameElement.getIntX(), gameElement.getIntY(), null);
        }

        for(Item item : map.items){
            g2.drawImage(item.getCurrentImage(), item.getIntX()-item.getCurrentImage().getWidth()/2,
                    item.getIntY()-item.getCurrentImage().getHeight()/2, null);
            if(item.getItemType() == ItemType.PROJECTILE && item.getAmount()>1){
                g2.drawString(item.getAmount()+"",
                        item.getIntX()-item.getCurrentImage().getWidth()/2, item.getIntY());
            }
        }

        g2.setTransform(reset);
        AffineTransform transform = new AffineTransform();
        for(Character character : map.characters) {
            drawCharacter(g2, character);
            g2.setTransform(reset);
        }

        for(Projectile projectile : map.projectiles){
            transform = new AffineTransform();
            transform.rotate(projectile.getAngle(), projectile.getX(), projectile.getY());
            g2.transform(transform);
            g2.drawImage(projectile.getCurrentImage(), projectile.getIntX()-projectile.getCurrentImage().getWidth(),
                    projectile.getIntY()-projectile.getCurrentImage().getHeight()/2, null);
            g2.setTransform(reset);
        }

        for(VisualEffect effect : map.effects){
            g2.drawImage(effect.getCurrentImage(), effect.getIntX(), effect.getIntY(), null);
        }

        g2.translate(camera.getX(), camera.getY());
    }

    // Draw tiles
    private void drawGrid(Graphics2D g2){
        for(Tile[] tiles : map.tileMap){
            for(Tile tile : tiles){
                g2.drawImage(tile.getCurrentImage(), tile.getIntX(), tile.getIntY(), null);
            }
        }
    }

    // Draw a character and its weapon
    private void drawCharacter(Graphics2D g2, Character character){
        AffineTransform transform = new AffineTransform();
        g2.drawString(character.getHp()+" ", character.getIntX(), character.getIntY());
        g2.setColor(Color.RED);
        BufferedImage weaponImage = character.getWeapon().getCurrentImage();
        if(Math.cos(character.getWeapon().getAngle())<0){
            g2.drawImage(character.getCurrentImage(),
                    character.getIntX()+character.getCurrentImage().getWidth(),
                    character.getIntY(),
                    -character.getCurrentImage().getWidth(),
                    character.getCurrentImage().getHeight(), null);
            transform.rotate(character.getWeapon().getAngle(),
                    character.getWeapon().getX(),
                    character.getWeapon().getY());
            g2.transform(transform);
            g2.drawImage(weaponImage,
                    character.getWeapon().getIntX(),
                    character.getWeapon().getIntY() - weaponImage.getHeight() / 2 + weaponImage.getHeight(),
                    weaponImage.getWidth(),
                    -weaponImage.getHeight(), null);
        }else {
            g2.drawImage(character.getCurrentImage(),
                    character.getIntX(),
                    character.getIntY(),
                    null);
            transform.rotate(character.getWeapon().getAngle(),
                    character.getWeapon().getX(),
                    character.getWeapon().getY());
            g2.transform(transform);
            g2.drawImage(weaponImage,
                    character.getWeapon().getIntX(),
                    character.getWeapon().getIntY() - weaponImage.getHeight() / 2,
                    null);
        }
        g2.setColor(Color.BLACK);
    }
}
