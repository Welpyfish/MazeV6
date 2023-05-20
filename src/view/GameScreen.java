package view;

import model.*;
import model.item.*;
import model.weapon.Projectile;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;

public class GameScreen extends JPanel {
    private Camera camera;
    private Map map;

    public GameScreen(Map map, Camera camera){
        this.camera = camera;
        this.map = map;
        setPreferredSize(new Dimension(1000, 720));
        setFocusable(true);
        setVisible(true);
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g.create();
        draw(g2);
        g2.dispose();
    }

    private void draw(Graphics2D g2){
        g2.translate(-camera.getX(), -camera.getY());
        AffineTransform reset = g2.getTransform();
        drawGrid(g2);

        for(Item item : map.items){
            g2.drawImage(item.getCurrentImage(), item.getX()-item.getCurrentImage().getWidth()/2,
                    item.getY()-item.getCurrentImage().getHeight()/2, null);
            if(item instanceof ProjectileItem){
                g2.drawString(((ProjectileItem) item).getAmount()+"",
                        item.getX()-item.getCurrentImage().getWidth()/2, item.getY());
            }
        }

        drawPlayer(g2, map.player);
        g2.setTransform(reset);
        AffineTransform transform = new AffineTransform();
        for(Enemy enemy : map.enemies) {
            drawEnemy(g2, enemy);
            g2.setTransform(reset);
        }

        for(Projectile projectile : map.projectiles){
            transform = new AffineTransform();
            transform.rotate(projectile.angle, projectile.getX(), projectile.getY());
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
                g2.drawRect(tile.getX(), tile.getY(), GameConstants.tileSize, GameConstants.tileSize);
                if(tile.collider!=null){
                    g2.fillRect(tile.getX(), tile.getY(), GameConstants.tileSize, GameConstants.tileSize);
                }
            }
        }
    }

    private void drawPlayer(Graphics2D g2, Player player){
        g2.drawString(player.getHp()+" ", player.getX(), player.getY());
        g2.setColor(Color.GREEN);
        g2.fillRect(player.getX(), player.getY(), GameConstants.tileSize, GameConstants.tileSize);
        AffineTransform transform = new AffineTransform();
        transform.rotate(player.getWeapon().getAngle(),
                player.getWeapon().getX(),
                player.getWeapon().getY());
        g2.transform(transform);
        g2.drawImage(player.getWeapon().getCurrentImage(),
                player.getWeapon().getX(),
                player.getWeapon().getY()-player.getWeapon().getCurrentImage().getHeight()/2,
                null);
        g2.setColor(Color.BLACK);
    }

    private void drawEnemy(Graphics2D g2, Enemy enemy){
        AffineTransform transform = new AffineTransform();
        g2.drawString(enemy.getHp()+" ", enemy.getX(), enemy.getY());
        g2.setColor(Color.RED);
        g2.fillRect(enemy.getX(), enemy.getY(), GameConstants.tileSize, GameConstants.tileSize);
        transform.rotate(enemy.getWeapon().getAngle(),
                enemy.getWeapon().getX(),
                enemy.getWeapon().getY());
        g2.transform(transform);
        g2.drawImage(enemy.getWeapon().getCurrentImage(),
                enemy.getWeapon().getX(),
                enemy.getWeapon().getY()-enemy.getWeapon().getCurrentImage().getHeight()/2,
                null);
        g2.setColor(Color.BLACK);
    }
}
