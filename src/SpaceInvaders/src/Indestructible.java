import javax.swing.ImageIcon;

public class Indestructible extends Obstacle{

	protected Indestructible(double x, double y, double width, double height) {
		super(x, y, width, height);
		
		setImage(new ImageIcon("src/images/indestructible.png").getImage());
	}
}
