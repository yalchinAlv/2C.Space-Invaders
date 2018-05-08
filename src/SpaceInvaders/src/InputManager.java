import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class InputManager implements MouseListener {
	
	boolean isMenu = true;
	boolean isSettings = false;
	boolean isMute = false;
	boolean isSoundMute = false;
	GameManager game = GameManager.getInstance();
	
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
		int mx = e.getX();
		int my = e.getY();
		
		//NOTE : HOW TO PLAY AND SETTINGS BUTTON CLICKS WILL BE ADDED SOON!
		
		//Play Button
		if(mx > GameManager.WIDTH / 2 + 320 && mx <= GameManager.WIDTH / 2 + 570 && isMenu == true)
		{
			if(my >= 150 && my <= 190)
			{
				//Pressed Play Button
//				if (game.isGameOver())
//					game.gameInit();
//				
//				GameManager.State = GameManager.STATE.GAME;
//				
//				if (game.isMusicOn())
//					game.setMusicMute(false);
//				
				GameManager.State = GameManager.STATE.LEVEL;
				System.out.println("pressed play");
				isMenu = false;
				isSettings = false;
				isMute = false;
			}
		}
		// Continue button
		if (isMenu && !game.isGameOver()) {
			Rectangle contButton = new Rectangle(GameManager.WIDTH / 2 + 575, 150, 250, 40);
			if (contButton.contains(mx, my)) {
				GameManager.State = GameManager.STATE.GAME;
				
				if (game.isMusicOn())
					game.setMusicMute(false);
				
				isMenu = false;
				isSettings = false;
				isMute = false;
			}
		}
		
		
		//HowToPlay Button
		if(mx > GameManager.WIDTH / 2 + 320 && mx <= GameManager.WIDTH / 2 + 570 && isMenu == true)
		{
			if(my >= 250 && my <= 290) 
			{
				//Pressed HowToPlay Button
				GameManager.State = GameManager.STATE.HOWTOPLAY;
				isMenu = false;
				isSettings = false;
				isMute = false;
				
				
			}
		}
		//Pressed Menu Button inside HowToPlay
		if(mx > GameManager.WIDTH / 2 + 650 && mx <= GameManager.WIDTH / 2 + 830 && GameManager.State == GameManager.STATE.HOWTOPLAY)
		{
			if(my >= 50 && my <= 90)
			{
				GameManager.State = GameManager.STATE.MENU;
				isMenu = true;
				isSettings = false;
				isMute = false;
				
				
			}
		}
		
		//Pressed Settings Button
		if(mx > GameManager.WIDTH / 2 + 320 && mx <= GameManager.WIDTH / 2 + 570 && isMenu)
		{
			if(my >= 350 && my <= 390) 
			{

				GameManager.State = GameManager.STATE.SETTINGS;
				isMenu = false;
				isSettings = true;
				isMute = false;
				
				
			}
		}
		
		//Pressed Menu Button inside Settings
		if(mx > GameManager.WIDTH / 2 + 650 && mx <= GameManager.WIDTH / 2 + 830 && isSettings == true)
		{
			if(my >= 50 && my <= 90) 
			{

				GameManager.State = GameManager.STATE.MENU;
				isMenu = true;
				isSettings = false;
				isMute = false;
				
				
			}
		}
		
		//Pressed Mute Music Button inside Settings
		if(mx > GameManager.WIDTH / 2 + 125 && mx <= GameManager.WIDTH / 2 + 350 && isSettings == true || isMute)
		{
			if(my >= 175 && my <= 215) 
			{
				GameManager.State = GameManager.STATE.MUTEMUSIC;
				isMenu = false;
				isSettings = false;
				isMute = true;
				game.setMusicMute(true);
			}
		}
		
		//Pressed Menu Button inside Mute Music
		if(mx > GameManager.WIDTH / 2 + 650 && mx <= GameManager.WIDTH / 2 + 830 && isMute == true)
		{
			if(my >= 50 && my <= 90) 
			{
				GameManager.State = GameManager.STATE.MENU;
				isMenu = true;
				isSettings = false;
				isMute = false;
			}
		}
		
		//Pressed UnMute Button inside Mute Music
		if(mx > GameManager.WIDTH / 2 + 450 && mx <= GameManager.WIDTH / 2 + 720 && isMute == true || isSettings)
		{
			if(my >= 175 && my <= 215) 
			{

				GameManager.State = GameManager.STATE.SETTINGS;
				isMenu = false;
				isSettings = true;
				isMute = false;
				game.setMusicMute(false);
				
			}
		}
		
		//Pressed Mute Sound 
		if (isSettings || isMute) {
			Rectangle muteSoundButton = new Rectangle(GameManager.WIDTH / 2 + 125, 255, 225, 40);
			Rectangle unMuteSoundButton = new Rectangle(GameManager.WIDTH / 2 + 450, 255, 225, 40);
			System.out.println("check mute sound");
			if (muteSoundButton.contains(mx, my)) {
				game.setSoundMute(true);
				System.out.println("setting sound");
			}
			else if (unMuteSoundButton.contains(mx, my)) {
				game.setSoundMute(false);
			}
			
		}
		
		
		// Restart and Menu in GAMEOVER
		if (GameManager.State == GameManager.STATE.GAMEOVER) {
			Rectangle menuButton = new Rectangle(GameManager.DIMENSIONS.width / 2 + 10, GameManager.DIMENSIONS.height - 150, 180, 40);
			Rectangle restartButton = new Rectangle(GameManager.DIMENSIONS.width / 2 - 190, GameManager.DIMENSIONS.height - 150, 180, 40);
			System.out.println("is in game over");
			if (menuButton.contains(mx, my)) {
				GameManager.State = GameManager.STATE.MENU;
				isMenu = true;
				isSettings = false;
				isMute = false;
				System.out.println("changed to menu");
				if (game.isMusicOn())
					game.setMusicMute(false);
			}
			else if (restartButton.contains(mx, my)) {
				if (game.isGameOver())
					game.gameInit();
				
				GameManager.State = GameManager.STATE.GAME;
				isMenu = false;
				isSettings = false;
				isMute = false;
			}
		}
		
		// Pause in Game
		if (GameManager.State == GameManager.STATE.GAME) {
			Rectangle pauseButton = new Rectangle(GameManager.DIMENSIONS.width - 185, GameManager.DIMENSIONS.height - 45, 180, 40);
			if (pauseButton.contains(mx, my)) {
				GameManager.State = GameManager.STATE.MENU;
				isMenu = true;
				isSettings = false;
				isMute = false;
				System.out.println("changed to menu");
				if (game.isMusicOn())
					game.setMusicMute(false);
			}
		}
		
		// Levels screen
		if (GameManager.State == GameManager.STATE.LEVEL) {
			Rectangle level1 = new Rectangle(GameManager.WIDTH / 2 + 100, 150, 200, 40);
			Rectangle level2 = new Rectangle(GameManager.WIDTH / 2 + 100, 250, 200, 40);
			Rectangle level3 = new Rectangle(GameManager.WIDTH / 2 + 100, 350, 200, 40);
			Rectangle menu = new Rectangle(GameManager.WIDTH / 2 + 100, 450, 200, 40);
			
			if (level1.contains(mx, my)) {
				game.setLevel(1);
				game.gameInit();
				GameManager.State = GameManager.STATE.GAME;
				System.out.println("from level 1");
				if (game.isMusicOn())
					game.setMusicMute(false);
			}
			if (level2.contains(mx, my)) {
				game.setLevel(2);
				game.gameInit();
				GameManager.State = GameManager.STATE.GAME;
				System.out.println("from level 2");
				if (game.isMusicOn())
					game.setMusicMute(false);
			}
			if (level3.contains(mx, my)) {
				game.setLevel(3);
				game.gameInit();
				GameManager.State = GameManager.STATE.GAME;
				
				if (game.isMusicOn())
					game.setMusicMute(false);
			}
			if (menu.contains(mx, my)) {
				GameManager.State = GameManager.STATE.MENU;
				isMenu = true;
				isSettings = false;
				isMute = false;
			}
		}
			
			
		//Quit Button
		if(mx > GameManager.WIDTH / 2 + 320 && mx <= GameManager.WIDTH / 2 + 570 && isMenu == true)
		{
			if(my >= 450 && my <= 490)
			{
				//Pressed Quit Button
				System.exit(1);
				
			}
		}
				
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}