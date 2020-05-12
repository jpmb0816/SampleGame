import javax.swing.JFrame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameWindow extends JFrame implements KeyListener {
	private boolean[] keyStates;
	
	public GameWindow() {
		this.setTitle(GameConstants.TITLE);
		this.setSize(GameConstants.WIDTH, GameConstants.HEIGHT);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		keyStates = new boolean[88];
		this.addKeyListener(this);
		
		this.setVisible(true);
		this.setFocusable(true);
		this.createBufferStrategy(2);
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		keyStates[e.getKeyCode()] = true;
		System.out.println("KeyCode: " + e.getKeyCode() + ", " + keyStates[e.getKeyCode()]);
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		keyStates[e.getKeyCode()] = false;
		System.out.println("KeyCode: " + e.getKeyCode() + ", " + keyStates[e.getKeyCode()]);
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		
	}
	
	// Getters
	public boolean[] getKeyStates() {
		return keyStates;
	}
}
