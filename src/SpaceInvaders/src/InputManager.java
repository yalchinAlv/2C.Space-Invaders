import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;

public class InputManager implements MouseListener {
	
	boolean isMenu = true;
	boolean isSettings = false;
	boolean isMute = false;

	
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
				GameManager.State = GameManager.STATE.GAME;
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
		if(mx > GameManager.WIDTH / 2 + 320 && mx <= GameManager.WIDTH / 2 + 570)
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
		if(mx > GameManager.WIDTH / 2 + 320 && mx <= GameManager.WIDTH / 2 + 570 && isSettings == true)
		{
			if(my >= 250 && my <= 290) 
			{

				GameManager.State = GameManager.STATE.MUTEMUSIC;
				isMenu = false;
				isSettings = false;
				isMute = true;
				
				
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
		if(mx > GameManager.WIDTH / 2 + 320 && mx <= GameManager.WIDTH / 2 + 570 && isMute == true)
		{
			if(my >= 350 && my <= 390) 
			{

				GameManager.State = GameManager.STATE.SETTINGS;
				isMenu = false;
				isSettings = true;
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