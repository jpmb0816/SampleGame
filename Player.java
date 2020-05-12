import java.awt.Color;
import java.awt.Graphics2D;

public class Player {
	private Color color;
	private int x, y;
	private int width, height;
	private int velX, velY;
	private int speed;
	
	public Player(Color color) {
		this.color = color;
		x = 0;
		y = 0;
		width = GameConstants.TILE_SIZE;
		height = GameConstants.TILE_SIZE;
		velX = 0;
		velY = 0;
		speed = 2;
	}
	
	public void getInput(boolean[] keyStates) {
		if (keyStates[65]) {
			setVelX(-speed);
		}
		else if (keyStates[68]) {
			setVelX(speed);
		}
		else {
			setVelX(0);
		}

		if (keyStates[87]) {
			setVelY(-speed);
		}
		else if (keyStates[83]) {
			setVelY(speed);
		}
		else {
			setVelY(0);
		}
	}
	
	public void update() {
		setX(x + velX);
		setY(y + velY);
	}
	
	public void render(Graphics2D g2) {
		g2.setColor(color);
		g2.fillRect(x, y, width, height);
	}
	
	// Getters
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getVelX() {
		return velX;
	}
	
	public int getVelY() {
		return velY;
	}
	
	public int getSpeed() {
		return speed;
	}
	
	//Setters
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public void setWidth(int width) {
		this.width = width;
	}
	
	public void setHeight(int height) {
		this.height = height;
	}
	
	public void setVelX(int velX) {
		this.velX = velX;
	}
	
	public void setVelY(int velY) {
		this.velY = velY;
	}
	
	public void setSpeed(int speed) {
		this.speed = speed;
	}
}