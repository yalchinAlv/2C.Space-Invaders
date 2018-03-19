import javax.swing.ImageIcon;

public class EnemyShip extends Ship {
	 
		private Bullet bomb;
	    private final String enemyImg = "src/images/enemyShip.png";
	    private final String bombImg = "src/images/bomb.png";
	    private int direction;

	    public EnemyShip(int x, int y) {

	        this.x = x;
	        this.y = y;

	        bomb = new Bullet(x, y, 1);
	        bomb.setImage(new ImageIcon(bombImg).getImage());
	        
	        ImageIcon ii = new ImageIcon(enemyImg);
	        setImage(ii.getImage());
	        direction = 1;
	    }
	    
	    public int getDir() {
	    	return direction;
	    }
	    
	    public void setDir(int dir) {
	    	direction = dir;
	    }

	    public void move(int direction) {
	        
	        this.x += direction;
	    }

	    public Bullet getBomb() {
	        
	        return bomb;
	    }

}
