import javax.swing.JFrame;

public class SpaceInvaders {

	public static void main(String[] args) {
		
		JFrame mainFrame = new JFrame("Space Invaders");
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		GameManager game = GameManager.getInstance();
		game.addMouseListener(new InputManager());
		mainFrame.add(game);
		
//		mainFrame.setSize(880, 550);
		mainFrame.pack();
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setResizable(false);
		mainFrame.setVisible(true);
	}
}