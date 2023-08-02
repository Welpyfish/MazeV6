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

        for(Sprite gameElement : map.gameElements){
            drawSprite(g2, reset, gameElement, (int) (gameElement.getX()+gameElement.getWidth()/2), (int) (gameElement.getY()+gameElement.getHeight()/2),
                    gameElement.getIntX(), gameElement.getIntY());
        }

        for(Item item : map.items){
            drawSprite(g2, reset, item, item.getIntX(), item.getIntY(),
                    item.getIntX()-item.getCurrentImage().getWidth()/2, item.getIntY()-item.getCurrentImage().getHeight()/2);

            if(item.getItemType() == ItemType.PROJECTILE && item.getAmount()>1){
                g2.drawString(item.getAmount()+"",
                        item.getIntX()-item.getCurrentImage().getWidth()/2, item.getIntY());
            }
        }

        for(Character character : map.characters) {
            g2.drawString(character.getHp()+" ", character.getIntX(), character.getIntY());
            g2.setColor(Color.RED);
            g2.drawRect(character.getIntX(), character.getIntY(), (int) character.getWidth(), (int) character.getHeight());
            drawSprite(g2, reset, character, character.getIntX(), character.getIntY(),
                    character.getIntX(), character.getIntY());
            drawSprite(g2, reset, character.getWeapon(), character.getWeapon().getIntX(),
                    character.getWeapon().getIntY(),
                    character.getWeapon().getIntX(), character.getWeapon().getIntY()- character.getWeapon().getCurrentImage().getHeight()/2);
            g2.setColor(Color.BLACK);
        }

        for(Projectile projectile : map.projectiles){
            drawSprite(g2, reset, projectile, projectile.getIntX(),
                    projectile.getIntY(),
                    projectile.getIntX()-projectile.getCurrentImage().getWidth(), projectile.getIntY()-projectile.getCurrentImage().getHeight()/2);
        }

        for(VisualEffect effect : map.effects){
            drawSprite(g2, reset, effect, (int) (effect.getX()+effect.getWidth()/2), (int) (effect.getY()+effect.getHeight()/2),
                    effect.getIntX(), effect.getIntY());
        }

        g2.translate(camera.getX(), camera.getY());
    }

    private void drawSprite(Graphics2D g2, AffineTransform reset, Sprite sprite, int cx, int cy, int x, int y){
        BufferedImage image = sprite.getCurrentImage();
        if(sprite.getAngle() != 0) {
            AffineTransform transform = new AffineTransform();
            transform.rotate(sprite.getAngle(), cx, cy);
            g2.transform(transform);
            if (Math.cos(sprite.getAngle()) >= 0) {
                g2.drawImage(image, x, y, null);
            } else {
                g2.drawImage(image, x, y + image.getHeight(), image.getWidth(), -image.getHeight(), null);
            }
            g2.setTransform(reset);
        }else{
            g2.drawImage(image, x, y, null);
        }
    }

    // Draw tiles
    private void drawGrid(Graphics2D g2){
        for(Tile[] tiles : map.tileMap){
            for(Tile tile : tiles){
                g2.drawImage(tile.getCurrentImage(), tile.getIntX(), tile.getIntY(), null);
            }
        }
    }
}
