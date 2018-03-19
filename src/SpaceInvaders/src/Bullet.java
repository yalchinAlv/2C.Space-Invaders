public class Bullet extends GameObject {
	 	
	    private final int H_SPACE = 6;
	    private final int V_SPACE = 1;
	    private int dir;
//	    private boolean destroyed;
	    
	    public Bullet() {
	    }

	    public Bullet(int x, int y, int dir) {
	        
//	        setDestroyed(true);
	    	setDead(true);
	        
	        setX(x + H_SPACE);
	        setY(y - V_SPACE);
	        
	        this.dir = dir;
	    }
	    
	    public int getDir() {
	    	
	    	return dir;
	    }
	    
//	    public void setDestroyed(boolean destroyed) {
//	        
//            this.dead = destroyed;
//        }
//
//        public boolean isDestroyed() {
//        
//            return dead;
//        }
}
