
public class DamageBonus extends Bonus {
	
	private int damage;
	private int duration;
	
	public DamageBonus(double x, double y, double width, double height, int dir, int speed, int damage, int duration) {
		super(x, y, width, height, dir, speed);
		this.damage = damage;
		this.duration = duration;
	}
	
	public int getDamage() {
		return damage;
	}
	
	public void setDamage(int damage) {
		this.damage = damage;
	}
	
	public int getDuration() {
		return duration;
	}
	
	public void setDuration(int duration) {
		this.duration = duration;
	}
}
