package main;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import states.GameState;
import states.GameoverState;
import states.HighScoreState;
import states.MenuState;

public class Main extends StateBasedGame {
	public static AppGameContainer appgc;

	public static final int GAME_WIDTH = 1280;
	public static final int GAME_HEIGHT = 720;
	public static final String GAME_NAME = "Tracy Invaders";

	public static final int MENU_STATE = 0;
	public static final int GAME_STATE = 1;
	public static final int GAMEOVER_STATE = 2;
	public static final int HIGH_SCORE_STATE = 3;

	public Main(String name) {
		super(name);
		this.addState(new MenuState());
		this.addState(new GameState());
		this.addState(new GameoverState());
		this.addState(new HighScoreState());
	}

	@Override
	public void initStatesList(GameContainer arg0) throws SlickException {
		this.enterState(MENU_STATE);
	}

	public static void main(String[] args) {
		try {
			appgc = new AppGameContainer(new Main(GAME_NAME));
			appgc.setDisplayMode(GAME_WIDTH, GAME_HEIGHT, false);
			appgc.setTargetFrameRate(60);
			appgc.setShowFPS(false);
			appgc.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

}
