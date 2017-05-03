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

	public static final int[] LARGE_INVADER_X = { 250, 500 };
	public static final int[] LARGE_INVADER_Y = { 250, 250 };
	public static LargeInvader[] largeInvader = new LargeInvader[10];
	public static SmallInvader[] smallInvader = new SmallInvader[10];

	public static int playerScore = 0;
	public static boolean exit = false;
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		// tracyImage = new Image("textures/tracyDepot2/largeTracy1.png");
		player = new Player(playerXPosition, playerYPosition);
		/*
		 * largeInvader0 = new LargeInvader(LARGE_INVADER_X[0],
		 * LARGE_INVADER_Y[0]); largeInvader1 = new
		 * LargeInvader(LARGE_INVADER_Y[1], LARGE_INVADER_Y[1]);
		 */
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

	float loops = 0;
	float xTrans = 0.5f;
	String direction = "right";
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		player.move(gc, g);
		player.shoot(gc, g);
		
		for (int i = 0; i < largeInvader.length; i++) {
			if (largeInvader[i].isDead == true) {
				largeInvader[i].largeInvaderAnimation.stop();
			} else {
				largeInvader[i].tryToShoot(gc, g);
				
				if (direction.equals("right")) {
					if (largeInvader[i].getX() > 1100) {
						for (int j = 0; j < largeInvader.length; j++) {
							largeInvader[j].animate(gc, g, largeInvader[j].getX() - 1, largeInvader[j].getY() + 50);
						}
						direction = "left";
					} else {
						largeInvader[i].animate(gc, g, largeInvader[i].getX() + xTrans, largeInvader[i].getY());
					}
				} else if (direction.equals("left")) {
					if (largeInvader[i].getX() < 100) {
						for (int j = 0; j < largeInvader.length; j++) {
							largeInvader[j].animate(gc, g, largeInvader[j].getX() + 1, largeInvader[j].getY() + 50);
						}
						direction = "right";
					} else {
						largeInvader[i].animate(gc, g, largeInvader[i].getX() - xTrans, largeInvader[i].getY());
					}
				}
			}
		}

		for (int i = 0; i < smallInvader.length; i++) {
			if (smallInvader[i].isDead == true) {
				smallInvader[i].smallInvaderAnimation.stop();
			} else {
				
				if (largeInvader[i].getY() >= Main.GAME_HEIGHT) {
					exit = true;
				}
				if (direction.equals("right")) {
					if (smallInvader[i].getX() > 1100) {
						for (int j = 0; j < smallInvader.length; j++) {
							smallInvader[j].animate(gc, g, smallInvader[j].getX() - 1, smallInvader[j].getY() + 50);
						}
						direction = "left";
					} else {
						smallInvader[i].animate(gc, g, smallInvader[i].getX() + xTrans, smallInvader[i].getY());
					}
				} else if (direction.equals("left")) {
					if (smallInvader[i].getX() < 100) {
						for (int j = 0; j < smallInvader.length; j++) {
							smallInvader[j].animate(gc, g, smallInvader[j].getX() + 1, smallInvader[j].getY() + 50);
						}
						direction = "right";
					} else {
						smallInvader[i].animate(gc, g, smallInvader[i].getX() - xTrans, smallInvader[i].getY());
					}
				}
			}
		}
		/*
		 * if (largeInvader[9].getX() > 1100 && largeInvader[9].getY() == 250) {
		 * for (int i = 0; i < largeInvader.length; i++) {
		 * largeInvader[i].animate(gc, g, largeInvader[i].getX(),
		 * largeInvader[i].getY() + 50); } } else {
		 */

		// }
		loops += 1f;
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		if (exit == true) {
			sbg.enterState(Main.MENU_STATE);
		}
	}

	@Override
	public int getID() {
		return 1;
	}
}
