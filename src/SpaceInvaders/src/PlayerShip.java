import java.awt.Dimension;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;

public class PlayerShip extends Ship {
	private final int START_Y = 480;
    private final int START_X = 270;
    
    private final String playerImg = "src/images/playerShip.png";
    private final String bulletImg = "src/images/shot.png";
    private int width;
    private Dimension screenDimension;
    private Bullet bullet;
    
    public PlayerShip() {
        
        ImageIcon ii = new ImageIcon(playerImg);

        width = ii.getImage().getWidth(null);

        setImage(ii.getImage());
        setX(START_X);
        setY(START_Y);
        
        bullet = new Bullet(START_X, START_Y, 0);
        bullet.setImage(new ImageIcon(bulletImg).getImage());
//        bullet.setDead(false);
    }
    
    public void setScreenDimension(Dimension d) {
    	
    	this.screenDimension = d;
    	
    }

    public void move() {
        
        x += dx;
        
        if (x <= 2) {
            x = 2;
        }
        
        if (x >= screenDimension.getWidth() - 2 * width) {
            x = (int) (screenDimension.getWidth() - 2 * width);
        }
        
//        bullet.setX(x);
    }
    
    public Bullet getBullet() {
    	return bullet;
    }
    
    public void setBullet(Bullet b) {
    	bullet = b;
    	bullet.setImage(new ImageIcon(bulletImg).getImage());
    	bullet.setDead(false);
    }

    public void keyPressed(KeyEvent e) {
        
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
        
            dx = -2;
        }

        if (key == KeyEvent.VK_RIGHT) {
        
            dx = 2;
        }
    }

    public void keyReleased(KeyEvent e) {
        
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
        
            dx = 0;
        }

        if (key == KeyEvent.VK_RIGHT) {
        
            dx = 0;
        }
    }
}