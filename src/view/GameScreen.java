package view;

import model.*;
import model.Character;
import model.item.*;
import model.weapon.Projectile;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;

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

    public void setPauseScreen(boolean paused){
        pauseScreen.setVisible(paused);
        if(paused){
            pauseScreen.requestFocusInWindow();
        }
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g.create();
        draw(g2);
        g2.dispose();
    }

    private void draw(Graphics2D g2){
        g2.translate(-camera.getX(), -camera.getY());
        AffineTransform reset = g2.getTransform();
        drawGrid(g2);

        for(TileObject gameElement : map.gameElements){
            g2.drawImage(gameElement.getCurrentImage(), gameElement.getX(), gameElement.getY(), null);
        }

        for(Item item : map.items){
            g2.drawImage(item.getCurrentImage(), item.getX()-item.getCurrentImage().getWidth()/2,
                    item.getY()-item.getCurrentImage().getHeight()/2, null);
            if(item.getAmount()>1){
                g2.drawString(item.getAmount()+"",
                        item.getX()-item.getCurrentImage().getWidth()/2, item.getY());
            }
        }

        g2.setTransform(reset);
        AffineTransform transform = new AffineTransform();
        for(Character character : map.characters) {
            drawCharacter(g2, character);
            g2.setTransform(reset);
        }
        drawPlayer(g2, map.player);
        g2.setTransform(reset);

        for(Projectile projectile : map.projectiles){
            transform = new AffineTransform();
            transform.rotate(projectile.getAngle(), projectile.getX(), projectile.getY());
            g2.transform(transform);
            g2.drawImage(projectile.getCurrentImage(), projectile.getX()-projectile.getCurrentImage().getWidth(),
                    projectile.getY()-projectile.getCurrentImage().getHeight()/2, null);
            g2.setTransform(reset);
        }

        for(VisualEffect effect : map.effects){
            g2.drawImage(effect.getCurrentImage(), effect.getX(), effect.getY(), null);
        }

        for(Portal portal : map.portals){
            g2.setColor(portal.getColor());
            g2.fillRect(portal.getStart().getX(), portal.getStart().getY(), GameConstants.tileSize, GameConstants.tileSize);
            g2.fillRect(portal.getEnd().getX(), portal.getEnd().getY(), GameConstants.tileSize, GameConstants.tileSize);
        }

        g2.translate(camera.getX(), camera.getY());
    }

    private void drawGrid(Graphics2D g2){
        for(Tile[] tiles : map.tileMap){
            for(Tile tile : tiles){
                g2.drawImage(tile.getCurrentImage(), tile.getX(), tile.getY(), null);
            }
        }
    }

    private void drawPlayer(Graphics2D g2, Player player){
        AffineTransform transform = new AffineTransform();
        g2.drawString(player.getHp()+" ", player.getX(), player.getY());
        g2.setColor(Color.GREEN);
        g2.fillRect(player.getX(), player.getY(), GameConstants.tileSize, GameConstants.tileSize);
        transform.rotate(player.getWeapon().getAngle(),
                player.getWeapon().getX(),
                player.getWeapon().getY());
        g2.transform(transform);
        g2.drawImage(player.getWeapon().getCurrentImage(),
                player.getWeapon().getX(),
                player.getWeapon().getY() - player.getWeapon().getCurrentImage().getHeight() / 2,
                null);
        g2.setColor(Color.BLACK);
    }

    private void drawCharacter(Graphics2D g2, Character character){
        AffineTransform transform = new AffineTransform();
        g2.drawString(character.getHp()+" ", character.getX(), character.getY());
        g2.setColor(Color.RED);
        g2.fillRect(character.getX(), character.getY(), GameConstants.tileSize, GameConstants.tileSize);
        transform.rotate(character.getWeapon().getAngle(),
                character.getWeapon().getX(),
                character.getWeapon().getY());
        g2.transform(transform);
        g2.drawImage(character.getWeapon().getCurrentImage(),
                character.getWeapon().getX(),
                character.getWeapon().getY() - character.getWeapon().getCurrentImage().getHeight() / 2,
                null);
        g2.setColor(Color.BLACK);
    }
}
