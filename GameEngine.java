import java.awt.image.BufferStrategy;
import java.awt.RenderingHints;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Font;
import java.awt.Color;

public class GameEngine implements Runnable {
	private Thread gameThread;
	private GameWindow window;
	private boolean running;
	private boolean noFPSLimit;
	private int ups, fps;
	private Player player;
	
	public GameEngine() {
		window = new GameWindow();
		running = false;
		noFPSLimit = true;
		ups = 0;
		fps = 0;
		player = new Player(Color.RED);
	}
	
	@Override
	public void run() {
		BufferStrategy bs = window.getBufferStrategy();
		Graphics2D g2 = null;
		
		final int MAX_UPDATES_PER_SECOND = 60;
		final int MAX_FRAMES_PER_SECOND = 60;
		final double U_OPTIMAL_TIME = 1000000000 / MAX_UPDATES_PER_SECOND;
		final double F_OPTIMAL_TIME = 1000000000 / MAX_FRAMES_PER_SECOND;
		
		double uDeltaTime = 0, fDeltaTime = 0;
		int updates = 0, frames = 0;
		long startTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		
		while (running) {
			long currentTime = System.nanoTime();
			uDeltaTime += (currentTime - startTime) / U_OPTIMAL_TIME;
			
			if (!noFPSLimit) {
				fDeltaTime += (currentTime - startTime) / F_OPTIMAL_TIME;
			}
			
			startTime = currentTime;
			
			if (uDeltaTime >= 1) {
				getInput();
				update();
				updates++;
				uDeltaTime--;
			}
			
			if (noFPSLimit || fDeltaTime >= 1) {
				do {
					try {
						g2 = (Graphics2D) bs.getDrawGraphics();
						render(g2);
					} finally {
						g2.dispose();
					}
					bs.show();
				} while(bs.contentsLost());
				
				frames++;
				
				if (!noFPSLimit) {
					fDeltaTime--;
				}
			}
			
			if (System.currentTimeMillis() - timer >= 1000) {
				System.out.printf("UPS: %s, FPS: %s\n", ups, fps);
				ups = updates;
				fps = frames;
				frames = 0;
				updates = 0;
				timer += 1000;
			}
		}
		stop();
	}
	
	public synchronized void start() {
		if (gameThread == null) {
			running = true;
			gameThread = new Thread(this);
			gameThread.start();
		}
	}
	
	public void stop() {
		if (gameThread != null) {
			running = false;
			
			try {
				gameThread.join();
			}
			catch(Exception ex) {
				ex.printStackTrace();
			}
			
			window.setVisible(false);
			window.dispose();
		}
	}
	
	private void getInput() {
		player.getInput(window.getKeyStates());
	}
	
	private void update() {
		player.update();
	}
	
	private void render(Graphics2D g2) {
		for (int y = 0, height = window.getHeight(); y < height; y += GameConstants.TILE_SIZE) {
			for (int x = 0, width = window.getWidth(); x < width; x += GameConstants.TILE_SIZE) {
				g2.setColor(randomColor());
				g2.fillRect(x, y, GameConstants.TILE_SIZE, GameConstants.TILE_SIZE);
			}
		}
		
		player.render(g2);
		
		g2.setColor(Color.RED);
		g2.drawString("UPS: " + ups + " FPS: " + fps, 30, 50);
	}
	
	public Color randomColor() {
		final int r = (int) (Math.random() * 255);
		final int g = (int) (Math.random() * 255);
		final int b = (int) (Math.random() * 255);
		return new Color(r, g, b);
	}
	
	public int getUPS() {
		return ups;
	}
	
	public int getFPS() {
		return fps;
	}
}