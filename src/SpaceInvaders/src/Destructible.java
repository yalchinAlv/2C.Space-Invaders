
public class Destructible extends Obstacle{

	private int health;

	public Destructible(double x, double y, double width, double height, int health) {
		super(x, y, width, height);
		this.health = health;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}
}
