import java.util.ArrayList;

public class Level {
	 
	private int level;
	private ArrayList<EnemyShip> enemies;
	private ArrayList<Bonus> bonuses;
	private ArrayList<Obstacle> obstacles;
	private PlayerShip player;
	
	public Level(int level, PlayerShip player) {
		super();
		this.level = level;
		this.player = player;
		enemies = new ArrayList<>();
		bonuses = new ArrayList<>();
		obstacles = new ArrayList<>();
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public ArrayList<EnemyShip> getEnemies() {
		return enemies;
	}

	public void setEnemies(ArrayList<EnemyShip> enemies) {
		this.enemies = enemies;
	}

	public ArrayList<Bonus> getBonuses() {
		return bonuses;
	}

	public void setBonuses(ArrayList<Bonus> bonuses) {
		this.bonuses = bonuses;
	}

	public ArrayList<Obstacle> getObstacles() {
		return obstacles;
	}

	public void setObstacles(ArrayList<Obstacle> obstacles) {
		this.obstacles = obstacles;
	}

	public PlayerShip getPlayer() {
		return player;
	}

	public void setPlayer(PlayerShip player) {
		this.player = player;
	}
	
	
}
