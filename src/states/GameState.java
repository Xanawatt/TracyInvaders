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
import characters.SmallInvader;
import characters.UFO;
import main.Main;

public class GameState extends BasicGameState {
	public static Player player;
	public static UFO ufo;
	public static LargeInvader largeInvader0;
	public static LargeInvader largeInvader1;
	public static int playerXPosition = 610;
	public static int playerYPosition = 500;
	public static int ticks;
	public static int deathTicks;

	public static final int LARGE_INVADER1_X = 250;
	public static final int LARGE_INVADER1_Y = 250;

	public static final int LARGE_INVADER2_X = 500;
	public static final int LARGE_INVADER2_Y = 250;

	public static final int[] LARGE_INVADER_X = { 250, 500 };
	public static final int[] LARGE_INVADER_Y = { 250, 250 };
	public static LargeInvader[] largeInvader = new LargeInvader[10];
	public static SmallInvader[] smallInvader = new SmallInvader[10];

	public static int playerScore = 0;

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		// tracyImage = new Image("textures/tracyDepot2/largeTracy1.png");
		player = new Player(playerXPosition, playerYPosition);
		ufo = new UFO(0,150);
		/*largeInvader0 = new LargeInvader(LARGE_INVADER_X[0], LARGE_INVADER_Y[0]);
		largeInvader1 = new LargeInvader(LARGE_INVADER_Y[1], LARGE_INVADER_Y[1]);*/
		int xStart = 100;
		int yStart = 250;
		for (int i = 0; i < largeInvader.length; i++) {
			largeInvader[i] = new LargeInvader(xStart, yStart);
			xStart += 100;
		}
		xStart = 100;
		yStart = 400;
		for (int i = 0; i < smallInvader.length; i++) {
			smallInvader[i] = new SmallInvader(xStart, yStart);
			xStart += 100;
		}
		
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		if(UFO.countTicks == true){
			ticks++;
		}
		
		if(UFO.isDead == true){
			deathTicks++;
		}
		player.move(gc, g);
		player.shoot(gc, g);
		UFO.move(gc, g);
		
		for (int i = 0; i < largeInvader.length; i++) {
			if (largeInvader[i].isDead == true) {
				largeInvader[i].largeInvaderAnimation.stop();
			} else {
				largeInvader[i].animate(gc, g, largeInvader[i].getX(), largeInvader[i].getY());
			}
		}
		
		for (int i = 0; i < smallInvader.length; i++) {
			if (smallInvader[i].isDead == true) {
				smallInvader[i].smallInvaderAnimation.stop();
			} else {
				smallInvader[i].animate(gc, g, smallInvader[i].getX(), smallInvader[i].getY());
			}
			g.drawString(Integer.toString(playerScore), 640, 10);
		}
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {

	}

	@Override
	public int getID() {
		return 1;
	}
}
