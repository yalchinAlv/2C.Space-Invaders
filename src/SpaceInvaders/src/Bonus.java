
public abstract class Bonus extends GameObject{

	private int dir;
	private int speed;
	
	protected Bonus(double x, double y, double width, double height, int dir, int speed) {
		super(x, y, width, height);
		this.dir = dir;
		this.speed = speed;
	}

	public int getDir() {
		return dir;
	}

	public void setDir(int dir) {
		this.dir = dir;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}
}
