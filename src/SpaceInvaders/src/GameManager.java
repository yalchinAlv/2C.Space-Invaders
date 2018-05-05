
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
	
	private final Dimension DIMENSIONS = new Dimension(880, 550);
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
	
	
	
	public static enum STATE{
		MENU,
		GAME,
		HOWTOPLAY
	};
	
	public static STATE State = STATE.MENU;
	private Thread animator;
	
	public GameManager() {
		
		addKeyListener(new MyKeyAdapter());
		setFocusable(true);
		setSize(DIMENSIONS);
		
		
		gameInit();
		setDoubleBuffered(true);
	}
	

	public void gameInit() {
		
		deaths = 0;
		isGameOver = false;
		enemies = new ArrayList<>();
		
	
		menu = new Menu();
		this.addMouseListener(new InputManager());
		
		for (int i = 0; i < 5; i++)
			for (int j = 0; j < 7; j++)
				enemies.add(new EnemyShip(18 * j, 18 * i));
		
		player = new PlayerShip();
		player.setScreenDimension(DIMENSIONS);
		
		if (animator == null || isGameOver) {
			
			animator = new Thread(this);
			animator.start();
		}
	}
	
	public void playMusic(File Sound) {
		
		try
		{
			clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(Sound));
			clip.start();
			//Thread.sleep(clip.getMicrosecondLength()/1000);
				
		}
			
		catch(Exception e)
		{
			
		}
	}
	
public void loopMusic(File loopSound) {
		
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

        Font small = new Font("Helvetica", Font.BOLD, 16);
        FontMetrics metr = this.getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString("Score: " + deaths * 10, 0, metr.getHeight());
        
        if(State == STATE.HOWTOPLAY) {
        	g.drawImage(GUIDE.getImage(), 0, 0, DIMENSIONS.width, DIMENSIONS.height, this);
        	Rectangle menuButton = new Rectangle(WIDTH / 2 + 650, 50, 180, 40);
        	Font fnt2 = new Font("arial", Font.BOLD, 30);
        	g.setFont(fnt2);
    		g.setColor(Color.yellow);
        	g.drawString("   MENU", menuButton.x + 19,menuButton.y + 30 );
        	g2d.draw(menuButton);
        }
  
        
        if (!isGameOver) {
        	
        	if(State == STATE.GAME) {
        		
        		if(!isGameMusicOn)
        		{
        			clip.stop();
        			loopMusic(GameMusic);
        			isGameMusicOn = true;
        		}
        	
            drawEnemies(g);
            drawPlayer(g);
            drawBullet(g);
            drawBombing(g);
        }
        	else if (State == STATE.MENU) {
        		if(clip == null) 
        		{
        			loopMusic(Music);
        			//clip = null;
        		}
        		menu.render(g);
        		//music.play();
        	}
        }
        
  
        Toolkit.getDefaultToolkit().sync();
        g.dispose();
	}
	
	public void gameOver() {

        Graphics g = this.getGraphics();

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
    }
	
	public void animation() {
		
		if (deaths == 35) {
			
			isGameOver = true;
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
                        playMusic(Boom);
                        enemy.setDead(true);
                        deaths++;
                        bullet.setDead(true);
                    }
                }
            }

            int y = bullet.getY();
            y -= 4;

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
                    a2.setY(a2.getY() + 15);
                }
            }

            if (x <= 5 && direction != 1) {

            	direction = 1;

                Iterator i2 = enemies.iterator();

                while (i2.hasNext()) {

                    EnemyShip a = (EnemyShip) i2.next();
                    a.setY(a.getY() + 15);
                }
            }
        }

        Iterator it = enemies.iterator();

        while (it.hasNext()) {
            
            EnemyShip enemy = (EnemyShip) it.next();
            
            if (!enemy.isDead()) {

                int y = enemy.getY();

                if (y > 540) {
                    isGameOver = true;
                    message = "Invasion!";
                }

                enemy.move(direction);
            }
		}
        
        Random generator = new Random();

        for (EnemyShip enemy: enemies) {

            int fire = generator.nextInt(15);
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
                    playMusic(PlayerBoom);
                    player.setDead(true);
                    b.setDead(true);
                }
            }

            if (!b.isDead()) {
                
                b.setY(b.getY() + 1);
                
                if (b.getY() >= 540) {
                    b.setDead(true);
                }
            }
        }
        
        
	}
	
	@Override
	public void run() {
		

		long beforeTime, timeDiff, sleep;

        beforeTime = System.currentTimeMillis();

        while (!isGameOver) {

            repaint();
            animation();

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

        gameOver();	
	}
	
	private class MyKeyAdapter extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {

            player.keyReleased(e);
        }

        @Override
        public void keyPressed(KeyEvent e) {

            player.keyPressed(e);

            int x = player.getX();
            int y = player.getY();

            int key = e.getKeyCode();
            
            if(State == STATE.GAME) {

            if (key == KeyEvent.VK_SPACE) {
                
                if (!isGameOver) {
                    if (player.getBullet().isDead()) {
                    	Bullet b = new Bullet(x, y, 0);
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
