import java.awt.Image;

public class GameObject {
	
//	private boolean visible;
    private Image image;
    protected int x;
    protected int y;
    protected boolean dead;
    protected int dx;
    
    public GameObject() {
    	
    	dead = false;
//    	visible = true;
    }
    
//    public void die() {
//        
//        visible = false;
//    }
//
//    public boolean isVisible() {
//    
//        return visible;
//    }
//
//    protected void setVisible(boolean visible) {
//    
//        this.visible = visible;
//    }

    public void setImage(Image image) {
    
        this.image = image;
    }

    public Image getImage() {
    
        return image;
    }

    public void setX(int x) {
    
        this.x = x;
    }

    public void setY(int y) {
    
        this.y = y;
    }

    public int getY() {
    
        return y;
    }

    public int getX() {
    
        return x;
    }

    public void setDead(boolean dead) {
    
        this.dead = dead;
    }

    public boolean isDead() {
    
        return this.dead;
    }

}
