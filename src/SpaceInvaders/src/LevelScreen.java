import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class LevelScreen {
	public Rectangle level1 = new Rectangle(GameManager.WIDTH / 2 + 100, 150, 200, 40);
	public Rectangle level2 = new Rectangle(GameManager.WIDTH / 2 + 100, 250, 200, 40);
	public Rectangle level3 = new Rectangle(GameManager.WIDTH / 2 + 100, 350, 200, 40);
	public Rectangle menu = new Rectangle(GameManager.WIDTH / 2 + 100, 450, 200, 40);
	
	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		
		Font fnt0 = new Font("arial", Font.BOLD, 50);
		g.setFont(fnt0);
		g.setColor(Color.red);
		g.drawString("SPACE INVADERS",GameManager.WIDTH / 2 + 230, 100);
		
		Font fnt1 = new Font("arial", Font.BOLD, 30);
		g.setFont(fnt1);
		g.drawString("  LEVEL 1", level1.x + 19,level1.y + 30 );
		g2d.draw(level1);
		g.drawString("  LEVEL 2", level2.x + 19,level2.y + 30 );
		g2d.draw(level2);
		g.drawString("  LEVEL 3", level3.x + 19,level3.y + 30 );
		g2d.draw(level3);
		g.drawString("    MENU", menu.x + 19,menu.y + 30 );
		g2d.draw(menu);
		
	}
}
