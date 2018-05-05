
public class LifeBonus extends Bonus {

	private int lifeBonus;

	public LifeBonus(double x, double y, double width, double height, int dir, int speed, int lifeBonus) {
		super(x, y, width, height, dir, speed);
		this.lifeBonus = lifeBonus;
	}

	public int getLifeBonus() {
		return lifeBonus;
	}

	public void setLifeBonus(int lifeBonus) {
		this.lifeBonus = lifeBonus;
	}
}
