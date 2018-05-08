import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class GameManager extends JPanel implements Runnable{

	File Laser = new File("src/audio/laser.WAV");
	File Boom = new File("src/audio/boom.WAV");
	File PlayerBoom = new File("src/audio/playerboom.WAV");
	File Music = new File("src/audio/aliendream.WAV");
	File GameMusic = new File("src/audio/rocker.WAV");
	Clip clip;

	//Media musicFile = new Media("src/audio/aliendream.WAV");
	//MediaPlayer music = new MediaPlayer(musicFile);

	public static final Dimension DIMENSIONS = new Dimension(880, 550);
	private final ImageIcon BG_IMAGE = new ImageIcon("src/images/bg.jpg");
	private final ImageIcon EXPLOSION = new ImageIcon("src/images/explosion.png");
	public  final ImageIcon GUIDE = new ImageIcon("src/images/howtoplay.jpg");

	private ArrayList<EnemyShip> enemies;
	private PlayerShip player;
	private Menu menu;
	private String message = "Game Over";
	private boolean isGameOver;
	private int deaths;
	private int direction = 1;
	private boolean isGameMusicOn = false;
	private boolean isMenuMusicOn = true;
	private boolean fromMute = false;
	private boolean fromMenu = true;
	private boolean isSoundOn = true;
	private boolean isMusicOn = true;
	private int level;
	private LevelScreen levelScreen;


	public static enum STATE{
		MENU,
		GAME,
		HOWTOPLAY,
		SETTINGS,
		MUTEMUSIC,
		MUTESOUND,
		GAMEOVER,
		LEVEL
	};

	public static STATE State = STATE.MENU;
	private Thread animator;
	
	private static GameManager instance = null;
	
	public static GameManager getInstance() {
		if (instance == null)
			instance = new GameManager();
		
		return instance;
	}

	private GameManager() {

		addKeyListener(new MyKeyAdapter());
		setFocusable(true);
		setSize(DIMENSIONS);
		setPreferredSize(DIMENSIONS);

		isGameOver = true;
		menu = new Menu();
		levelScreen = new LevelScreen();
		level = 1;
		State = STATE.MENU;
//		gameInit();
		
		if (animator == null || isGameOver) {

			animator = new Thread(this);
			animator.start();
		}
		
		setDoubleBuffered(true);
	}
	
	public boolean isGameOver() {
		return isGameOver;
	}
	
	public void setSoundMute(boolean mute) {
		isSoundOn = !mute;
	}
	
	public void setMusicMute(boolean mute) {
		isMusicOn = !mute;
		if (mute) {
			clip.stop();
			System.out.println("from mute sound");
		}
		else {
			if (clip != null)
				clip.stop();
			if (State == STATE.GAME)
				loopMusic(GameMusic);
			else 
				loopMusic(Music);
		}
	}
	public boolean isMusicOn() {
		return isMusicOn;
	}
	
	public void setLevel(int level) {
		this.level = level;
	}

	public void gameInit() {

//		if (this.getMouseListeners().length == 0)
//			this.addMouseListener(new InputManager());
		
		deaths = 0;
		isGameOver = false;
		enemies = new ArrayList<>();

		for (int i = 0; i < 5; i++)
			for (int j = 0; j < 7; j++)
				enemies.add(new EnemyShip(18 * j, 18 * i));

		player = new PlayerShip();
		player.setScreenDimension(DIMENSIONS);
	}

	public void playMusic(File Sound) {

		try
		{
			Clip soundClip = AudioSystem.getClip();
			soundClip.open(AudioSystem.getAudioInputStream(Sound));
			soundClip.start();

		}

		catch(Exception e)
		{

		}
	}

	public void loopMusic(File loopSound) {

		if (isMusicOn)
			try
			{
				clip = AudioSystem.getClip();
				clip.open(AudioSystem.getAudioInputStream(loopSound));
				clip.loop(clip.LOOP_CONTINUOUSLY);
				//Thread.sleep(clip.getMicrosecondLength()/1000);
	
			}
			catch(Exception e)
			{
	
			}
	}



	public void drawEnemies(Graphics g) {


		for (EnemyShip enemy : enemies) {

			if (!enemy.isDead())
				g.drawImage(enemy.getImage(), enemy.getX(), enemy.getY(), this);

		}

	}

	private void drawPlayer(Graphics g) {


		if (!player.isDead())
			g.drawImage(player.getImage(), player.getX(), player.getY(), this);

		if (player.isDead()) {
			isGameOver = true;
			State = STATE.GAMEOVER;
		}

	}

	private void drawBullet(Graphics g) {


		Bullet bullet = player.getBullet();
		if (!bullet.isDead())
			g.drawImage(bullet.getImage(), bullet.getX(), bullet.getY(), this);


	}

	private void drawBombing(Graphics g) {



		for (EnemyShip enemy : enemies) {

			Bullet bullet = enemy.getBomb();
			if (!bullet.isDead())
				g.drawImage(bullet.getImage(), bullet.getX(), bullet.getY(), this);
		}
	}



	@Override
	public void paintComponent(Graphics g) {

		Graphics2D g2d = (Graphics2D) g;
		super.paintComponent(g);

		g.setColor(Color.white);
		g.fillRect(0, 0, DIMENSIONS.width, DIMENSIONS.height);
		g.drawImage(BG_IMAGE.getImage(), 0, 0, DIMENSIONS.width, DIMENSIONS.height, this);
		g.setColor(Color.green);

		Font small = new Font("Helvetica", Font.BOLD, 22);
		FontMetrics metr = this.getFontMetrics(small);

		g.setColor(Color.white);
		g.setFont(small);
		
			

		if(State == STATE.HOWTOPLAY) {
			g.drawImage(GUIDE.getImage(), 0, 0, DIMENSIONS.width, DIMENSIONS.height, this);
			Rectangle menuButton = new Rectangle(WIDTH / 2 + 650, 50, 180, 40);
			Font fnt2 = new Font("arial", Font.BOLD, 30);
			g.setFont(fnt2);
			g.setColor(Color.yellow);
			g.drawString("   MENU", menuButton.x + 19,menuButton.y + 30 );
			g2d.draw(menuButton);
		}

		if (State == STATE.SETTINGS)
		{
			g.drawImage(BG_IMAGE.getImage(), 0, 0, DIMENSIONS.width, DIMENSIONS.height, this);

			Rectangle muteMusicButton = new Rectangle(WIDTH / 2 + 125, 175, 225, 40);
			Font fnt3 = new Font("arial", Font.BOLD, 30);
			g.setFont(fnt3);
			g.setColor(Color.green);
			g.drawString("MUTE MUSIC", muteMusicButton.x + 19, muteMusicButton.y + 30 );
			g2d.draw(muteMusicButton);


			/*Rectangle muteSoundButton = new Rectangle(WIDTH / 2 + 125, 300, 225, 40);
        	Font fnt4 = new Font("arial", Font.BOLD, 30);
        	g.setFont(fnt4);
    		g.setColor(Color.green);
        	g.drawString("MUTE SOUND", muteSoundButton.x + 10, muteSoundButton.y + 30 );
        	g2d.draw(muteSoundButton);*/

			Rectangle menuButton = new Rectangle(WIDTH / 2 + 650, 50, 180, 40);
			Font fnt5 = new Font("arial", Font.BOLD, 30);
			g.setFont(fnt5);
			g.setColor(Color.green);
			g.drawString("   MENU", menuButton.x + 19, menuButton.y + 30 );
			g2d.draw(menuButton);
		}

		else if(State == STATE.MUTEMUSIC)
		{
			g.drawImage(BG_IMAGE.getImage(), 0, 0, DIMENSIONS.width, DIMENSIONS.height, this);

			Rectangle muteMusicButton = new Rectangle(WIDTH / 2 + 450, 175, 270, 40);
			Font fnt3 = new Font("arial", Font.BOLD, 30);
			g.setFont(fnt3);
			g.setColor(Color.red);
			g.drawString("UNMUTE MUSIC", muteMusicButton.x + 19, muteMusicButton.y + 30 );
			g2d.draw(muteMusicButton);


			/*Rectangle muteSoundButton = new Rectangle(WIDTH / 2 + 450, 300, 270, 40);
        	Font fnt4 = new Font("arial", Font.BOLD, 30);
        	g.setFont(fnt4);
    		g.setColor(Color.red);
        	g.drawString("UNMUTE SOUND", muteSoundButton.x + 10, muteSoundButton.y + 30 );
        	g2d.draw(muteSoundButton);*/

			Rectangle menuButton = new Rectangle(WIDTH / 2 + 650, 50, 180, 40);
			Font fnt5 = new Font("arial", Font.BOLD, 30);
			g.setFont(fnt5);
			g.setColor(Color.green);
			g.drawString("   MENU", menuButton.x + 19, menuButton.y + 30 );
			g2d.draw(menuButton);
		}
		
		if (State == STATE.SETTINGS || State == STATE.MUTEMUSIC) {
			
			if (isSoundOn) {
				Rectangle muteSoundButton = new Rectangle(WIDTH / 2 + 125, 255, 225, 40);
				Font fnt3 = new Font("arial", Font.BOLD, 30);
				g.setFont(fnt3);
				g.setColor(Color.green);
				g.drawString("MUTE SOUND", muteSoundButton.x + 10, muteSoundButton.y + 30 );
				g2d.draw(muteSoundButton);
			}
			else {
				Rectangle unMuteSoundButton = new Rectangle(WIDTH / 2 + 450, 255, 270, 40);
				Font fnt3 = new Font("arial", Font.BOLD, 30);
				g.setFont(fnt3);
				g.setColor(Color.red);
				g.drawString("UNMUTE SOUND", unMuteSoundButton.x + 10, unMuteSoundButton.y + 30 );
				g2d.draw(unMuteSoundButton);
			}
		}
		
		if (State == STATE.MENU) {

			fromMute = false;

			if(clip == null) 
			{
				loopMusic(Music);
				//clip = null;
			}
			menu.render(g);
			//music.play();
		}

		if(State == STATE.MUTEMUSIC) {

			clip.stop();
			isMenuMusicOn = false;
			fromMute = true;
		}

		if(State == STATE.SETTINGS) {

			if(fromMute == true && isMenuMusicOn == false)
			{
				System.out.println("CASE 1");
				clip.start();
				isMenuMusicOn = true;
			}

			if(fromMute == false  && isMenuMusicOn == false)
			{
				System.out.println("CASE 2");
				State = STATE.MUTEMUSIC;

			}

			else if(fromMute == false && isMenuMusicOn == true)
			{
				clip.start();
			}
		}

//		if (!isGameOver) {

			if(State == STATE.GAME) {

//				if(!isGameMusicOn && isMenuMusicOn == true)
//				{
//					clip.stop();
//					loopMusic(GameMusic);
//					isGameMusicOn = true;
//				}
//
//				if (isGameMusicOn && isMenuMusicOn == false)
//				{
//					clip.stop();
//					isGameMusicOn = false;
//				}


				drawEnemies(g);
				drawPlayer(g);
				drawBullet(g);
				drawBombing(g);
				
				Rectangle pauseButton = new Rectangle(DIMENSIONS.width - 185, DIMENSIONS.height - 45, 180, 40);
				Font fnt5 = new Font("arial", Font.BOLD, 30);
				g.setFont(fnt5);
				g.setColor(Color.green);
				g.drawString("  PAUSE", pauseButton.x + 19, pauseButton.y + 30 );
				((Graphics2D) g).draw(pauseButton);
				
				g.drawString("Score: " + deaths * 10, 5, DIMENSIONS.height - 5);
				
				g.setColor(Color.yellow);
				g.drawString("Level: " + level, 200, DIMENSIONS.height - 5);
				
			}
			if (State == STATE.LEVEL) {
				levelScreen.render(g);
			}
//		}
		
		if (State == STATE.GAMEOVER) {
			gameOver(g);
		}

		Toolkit.getDefaultToolkit().sync();
		g.dispose();
	}

	public void gameOver(Graphics g) {

//		State = STATE.GAMEOVER;
//		Graphics g = this.getGraphics();

		g.setColor(Color.black);
		g.fillRect(0, 0, DIMENSIONS.width, DIMENSIONS.height);
		g.drawImage(BG_IMAGE.getImage(), 0, 0, DIMENSIONS.width, DIMENSIONS.height, this);


		g.setColor(Color.white);
		g.fillRect(45, DIMENSIONS.height / 4 - 40, DIMENSIONS.width - 90, 60);
		g.setColor(new Color(0, 32, 48));
		g.fillRect(50, DIMENSIONS.height / 4 - 35, DIMENSIONS.width - 100, 50);

		Font small = new Font("Helvetica", Font.BOLD, 30);
		FontMetrics metr = this.getFontMetrics(small);

		g.setColor(Color.white);
		g.setFont(small);
		g.drawString(message, (DIMENSIONS.width - metr.stringWidth(message)) / 2,
				DIMENSIONS.height / 4);

		g.drawString("Score: " + deaths * 10, 
				(DIMENSIONS.width - metr.stringWidth("Score: " + deaths * 10)) / 2,
				DIMENSIONS.height / 2);

		Rectangle menuButton = new Rectangle(DIMENSIONS.width / 2 + 10, DIMENSIONS.height - 150, 180, 40);
		Font fnt5 = new Font("arial", Font.BOLD, 30);
		g.setFont(fnt5);
		g.setColor(Color.green);
		g.drawString("   MENU", menuButton.x + 19, menuButton.y + 30 );
		((Graphics2D) g).draw(menuButton);

		Rectangle restartButton = new Rectangle(DIMENSIONS.width / 2 - 190, DIMENSIONS.height - 150, 180, 40);
		//    	Font fnt5 = new Font("arial", Font.BOLD, 30);
		g.setFont(fnt5);
		g.setColor(Color.green);
		g.drawString("RESTART", restartButton.x + 19, restartButton.y + 30 );
		((Graphics2D) g).draw(restartButton);
	}

	public void animation() {
		if(State == STATE.GAME) {
			if (deaths == 35) {

				isGameOver = true;
				State = STATE.GAMEOVER;
				message = "You won!";
			}

			player.move();

			Bullet bullet = player.getBullet();


			if (!bullet.isDead()) {

				int bulletX = bullet.getX();
				int bulletY = bullet.getY();

				for (EnemyShip enemy: enemies) {

					int enemyX = enemy.getX();
					int enemyY = enemy.getY();

					if (!enemy.isDead() && !bullet.isDead()) {
						if (bulletX >= (enemyX)
								&& bulletX <= (enemyX + enemy.getImage().getWidth(null))
								&& bulletY >= (enemyY)
								&& bulletY <= (enemyY + enemy.getImage().getHeight(null))) {

							enemy.setImage(EXPLOSION.getImage());
							
							if (isSoundOn)
								playMusic(Boom);
							
							enemy.setDead(true);
							deaths++;
							bullet.setDead(true);
						}
					}
				}

				int y = bullet.getY();
				y -= 4 * level;

				if (y < 0) {
					bullet.setDead(true);
				} else {
					bullet.setY(y);
				}
			}

			for (EnemyShip enemy : enemies) {

				int x = enemy.getX();

				if (x >= DIMENSIONS.getWidth() - 30 && direction != -1) {

					direction = -1;
					Iterator i1 = enemies.iterator();

					while (i1.hasNext()) {

						EnemyShip a2 = (EnemyShip) i1.next();
						a2.setY(a2.getY() + 30);
					}
				}

				if (x <= 5 && direction != 1) {

					direction = 1;

					Iterator i2 = enemies.iterator();

					while (i2.hasNext()) {

						EnemyShip a = (EnemyShip) i2.next();
						a.setY(a.getY() + 30);
					}
				}
			}

			Iterator it = enemies.iterator();

			while (it.hasNext()) {

				EnemyShip enemy = (EnemyShip) it.next();

				if (!enemy.isDead()) {

					int y = enemy.getY();

					if (y > 450) {
						isGameOver = true;
						State = STATE.GAMEOVER;
						message = "Invasion!";
					}

					enemy.move(direction * level);
				}
			}

			Random generator = new Random();

			for (EnemyShip enemy: enemies) {

				int fire = generator.nextInt(15 * level);
				Bullet b = enemy.getBomb();

				if (fire == 5 && !enemy.isDead() && b.isDead()) {

					b.setDead(false);
					b.setX(enemy.getX());
					b.setY(enemy.getY());
				}

				int bombX = b.getX();
				int bombY = b.getY();
				int playerX = player.getX();
				int playerY = player.getY();

				if (!player.isDead() && !b.isDead()) {

					if (bombX >= (playerX)
							&& bombX <= (playerX + 15)
							&& bombY >= (playerY)
							&& bombY <= (playerY + 10)) {

						player.setImage(EXPLOSION.getImage());
						
						if (isSoundOn)
							playMusic(PlayerBoom);
						
						player.setDead(true);
						b.setDead(true);
					}
				}

				if (!b.isDead()) {

					b.setY(b.getY() + level);

					if (b.getY() >= 500) {
						b.setDead(true);
					}
				}
			}
		}

	}

	@Override
	public void run() {


		long beforeTime, timeDiff, sleep;

		beforeTime = System.currentTimeMillis();

		while (true) {

			repaint();
			
//			if (!isGameOver)
			animation();
//			else
//				State = STATE.GAMEOVER;

			timeDiff = System.currentTimeMillis() - beforeTime;
			sleep = 13 - timeDiff;

			if (sleep < 0) {
				sleep = 2;
			}

			try {
				Thread.sleep(sleep);
			} catch (InterruptedException e) {
				System.out.println("interrupted");
			}

			beforeTime = System.currentTimeMillis();
		}
	}

	private class MyKeyAdapter extends KeyAdapter {

		@Override
		public void keyReleased(KeyEvent e) {

			player.keyReleased(e);
		}

		@Override
		public void keyPressed(KeyEvent e) {

			player.keyPressed(e, level);

			int x = player.getX();
			int y = player.getY();

			int key = e.getKeyCode();

			if(State == STATE.GAME) {

				if (key == KeyEvent.VK_SPACE) {

					if (!isGameOver) {
						if (player.getBullet().isDead()) {
							Bullet b = new Bullet(x, y, 0);
							
							if (isSoundOn)
								playMusic(Laser);
							
							b.setDead(false);
							player.setBullet(b);
						}
					}
				}
			}
		}
	}
}
