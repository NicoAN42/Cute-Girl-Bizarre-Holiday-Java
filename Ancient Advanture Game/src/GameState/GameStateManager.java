package GameState;

import GameState.AcidState;
import GameState.Stage1;
import GameState.Stage2;
import GameState.Stage3;
import GameState.MenuState;

import Audio.AudioPlayer;
import main.Game;
import main.GamePanel;

public class GameStateManager {
	
	private Game[] game;
	private GameState[] gameStates;
	private int currentState;
	
	private PauseState pauseState;
	private boolean paused;
	
	public static final int NUMGAME = 16;
	public static final int NUMGAMESTATES = 16;
	public static final int MENUSTATE = 0;
	public static final int STAGE1 = 2;
	public static final int STAGE2 = 3;
	public static final int STAGE3 = 4;
	public static final int ACIDSTATE = 15;
	
	public GameStateManager() {
		
		AudioPlayer.init();
		
		gameStates = new GameState[NUMGAMESTATES];
		game = new Game[NUMGAME];
		pauseState = new PauseState(this);
		paused = false;
		
		currentState = MENUSTATE;
		loadState(currentState);
		
	}
	
	private void loadState(int state) {
		if(state == STAGE1)
			gameStates[state] = new Stage1(this);
		else if(state == STAGE2)
			gameStates[state] = new Stage2(this);
		else if(state == STAGE3)
			gameStates[state] = new Stage3(this);
		else if(state == ACIDSTATE)
			gameStates[state] = new AcidState(this);
	}
	
	private void unloadState(int state) {
		gameStates[state] = null;
	}
	
	public void setState(int state) {
		unloadState(currentState);
		currentState = state;
		loadState(currentState);
	}
	
	public void setPaused(boolean b) { paused = b; }
	
	public void update() {
		if(paused) {
			pauseState.update();
			return;
		}
		if(gameStates[currentState] != null) gameStates[currentState].update();
	}
	
	public void draw(java.awt.Graphics2D g) {
		if(paused) {
			pauseState.draw(g);
			return;
		}
		if(gameStates[currentState] != null) gameStates[currentState].draw(g);
		else {
			g.setColor(java.awt.Color.BLACK);
			g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
		}
	}
	
}