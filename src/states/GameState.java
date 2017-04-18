package states;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.RoundedRectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import characters.LargeInvader;
import characters.Player;
import main.Main;

public class GameState extends BasicGameState {
	public static Player player;
	public static LargeInvader largeInvader0;
	public static LargeInvader largeInvader1;
	public static int playerXPosition = 610;
	public static int playerYPosition = 500;

	public static final int LARGE_INVADER1_X = 250;
	public static final int LARGE_INVADER1_Y = 250;
	
	public static final int LARGE_INVADER2_X = 500;
	public static final int LARGE_INVADER2_Y = 250;
	
	public static final int[] LARGE_INVADER_X = {250, 500};
	public static final int[] LARGE_INVADER_Y = {250, 250};
	
	public static int playerScore = 0;
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		//tracyImage = new Image("textures/tracyDepot2/largeTracy1.png");
		player = new Player(playerXPosition, playerYPosition);
		largeInvader0 = new LargeInvader(LARGE_INVADER_X[0], LARGE_INVADER_Y[0]);
		largeInvader1 = new LargeInvader(LARGE_INVADER_Y[1], LARGE_INVADER_Y[1]);
		/*for (int i = 0; i < 30) {
		 * 
			sdfgh
		}*/
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		player.move(gc, g);
		player.shoot(gc, g);
		if (largeInvader0.isDead == true) {
			largeInvader0.largeInvaderAnimation.stop();
		} else {
			largeInvader0.animate(gc, g, LARGE_INVADER1_X, LARGE_INVADER1_Y);
		}
		if (largeInvader1.isDead == true) {
			largeInvader1.largeInvaderAnimation.stop();
		} else {
			largeInvader1.animate(gc, g, LARGE_INVADER2_X, LARGE_INVADER2_Y);
		}
		g.drawString(Integer.toString(playerScore), 640, 10);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		
		
	}

	@Override
	public int getID() {
		return 1;
	}
}
