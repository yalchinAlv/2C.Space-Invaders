import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Font;
import java.awt.Color;
import java.awt.Rectangle;

public class Menu{
	
	public Rectangle contButton = new Rectangle(GameManager.WIDTH / 2 + 575, 150, 250, 40);
	public Rectangle playButton = new Rectangle(GameManager.WIDTH / 2 + 320, 150, 250, 40);
	public Rectangle howToPlayButton = new Rectangle(GameManager.WIDTH / 2 + 320, 250, 250, 40);
	public Rectangle settingsButton = new Rectangle(GameManager.WIDTH / 2 + 320, 350, 250, 40);
	public Rectangle quitButton = new Rectangle(GameManager.WIDTH / 2 + 320, 450, 250, 40);
	
	
	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		
		Font fnt0 = new Font("arial", Font.BOLD, 50);
		g.setFont(fnt0);
		g.setColor(Color.red);
		g.drawString("SPACE INVADERS",GameManager.WIDTH / 2 + 230, 100);
		
		Font fnt1 = new Font("arial", Font.BOLD, 30);
		g.setFont(fnt1);
		
		if (!GameManager.getInstance().isGameOver()) {
			g.setColor(Color.green);
			g.drawString("    CONTINUE", contButton.x + 19,contButton.y + 30 );
			g2d.draw(contButton);
		}
		g.setColor(Color.red);
		g.drawString("       PLAY", playButton.x + 19,playButton.y + 30 );
		g2d.draw(playButton);
		g.drawString("HOW TO PLAY", howToPlayButton.x + 19,howToPlayButton.y + 30 );
		g2d.draw(howToPlayButton);
		g.drawString("    SETTINGS", settingsButton.x + 19,settingsButton.y + 30 );
		g2d.draw(settingsButton);
		g.drawString("        QUIT", quitButton.x + 19,quitButton.y + 30 );
		g2d.draw(quitButton);
		
	}

}