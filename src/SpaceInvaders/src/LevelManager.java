import java.util.ArrayList;

public class LevelManager {
	
	private ArrayList<Level> levels;
	private int currentLevel;
	
	public LevelManager(PlayerShip player) {
		levels = new ArrayList<>();
		currentLevel = 0;
		
		Level level1 = new Level(1, player);
		Level level2 = new Level(2, player);
		Level level3 = new Level(3, player);
		
		levels.add(level1);
		levels.add(level2);
		levels.add(level3);
		
	}

	public ArrayList<Level> getLevels() {
		return levels;
	}

	public void setLevels(ArrayList<Level> levels) {
		this.levels = levels;
	}

	public int getCurrentLevel() {
		return currentLevel;
	}

	public void setCurrentLevel(int currentLevel) {
		this.currentLevel = currentLevel;
	}
}
