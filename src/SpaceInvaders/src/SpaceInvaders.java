import javax.swing.JFrame;

public class SpaceInvaders {

	public static void main(String[] args) {
		
		JFrame mainFrame = new JFrame("Space Invaders");
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.add(new GameManager());
		mainFrame.setSize(880, 550);
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setResizable(false);
		mainFrame.setVisible(true);
	}
}