package Main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import Handlers.Keys;

public class GamePanel extends JPanel implements Runnable, KeyListener{
	
	// dimensi
	public static final int WIDTH = 320;
	public static final int HEIGHT = 240;
	public static final int SCALE = 2;
	
	
	//thread
	private Thread thread;
	private boolean running;
	private int FPS = 60;
	private long targetTime = 1000 / FPS;
	
	// image
	private BufferedImage image;
	private Graphics2D g;
	
	public GamePanel() {
		super();
		setPreferredSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));
		setFocusable(true);
		requestFocus();
	}
	
	public void addNotify() {
		super.addNotify();
		if(thread == null) {
			thread = new Thread(this);
			addKeyListener(this);
			thread.start();
		}
	}
	
	public void init() {
		image = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
		g = (Graphics2D) g;
		running = true;
	}
	
	public void run() {
		init();
		
		long start;
		long elapsed;
		long wait;
		
		//loop game
		while(running) {
			
			start = System.nanoTime();
			
			update();
			draw();
			drawToScreen();
			
			elapsed = System.nanoTime() - start;
			wait = targetTime - elapsed/1000000;
			
			try {
				Thread.sleep(wait);
			}catch(Exception e) {
				e.printStackTrace();
			}
			
		}
	}
	
	private void update() {
		
	}
	private void draw() {
		
	}
	private void drawToScreen() {
		Graphics g2 = getGraphics();
		g2.drawImage(image, 0, 0, null);
		g2.dispose();
	}
	
	
	public void keyTyped(KeyEvent key) {
		
	}
	
	public void keyPressed(KeyEvent key) {
		
	}
	
	public void keyReleased(KeyEvent key) {
	
}


}
