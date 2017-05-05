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
	public static int playerYPosition = 630;
	public static int ticks;
	public static int deathTicks;

	public static final int LARGE_INVADER1_X = 250;
	public static final int LARGE_INVADER1_Y = 250;

	public static final int LARGE_INVADER2_X = 500;
	public static final int LARGE_INVADER2_Y = 250;

	public static final int[] LARGE_INVADER_X = { 250, 500 };
	public static final int[] LARGE_INVADER_Y = { 250, 250 };
	public static LargeInvader[] largeInvaderRow1 = new LargeInvader[10];
	public static LargeInvader[] largeInvaderRow2 = new LargeInvader[10];
	public static LargeInvader[] largeInvaderRow3 = new LargeInvader[10];
	public static SmallInvader[] smallInvader = new SmallInvader[10];

	public static int playerScore = 0;
	public static boolean exit = false;
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		// tracyImage = new Image("textures/tracyDepot2/largeTracy1.png");
		player = new Player(playerXPosition, playerYPosition);
		ufo = new UFO(0,150);
		int xStart = 100;
		int yStart = 100;
		for (int i = 0; i < largeInvaderRow1.length; i++) {
			largeInvaderRow1[i] = new LargeInvader(xStart, yStart);
			xStart += 100;
		}
		for (int i = 0; i < largeInvaderRow2.length; i++) {
			largeInvaderRow2[i] = new LargeInvader(xStart, yStart);
			xStart += 100;
			yStart += 100;
		}
		for (int i = 0; i < largeInvaderRow3.length; i++) {
			largeInvaderRow3[i] = new LargeInvader(xStart, yStart);
			xStart += 100;
			yStart += 100;
		}
		
		xStart = 100;
		yStart = 400;
		for (int i = 0; i < smallInvader.length; i++) {
			smallInvader[i] = new SmallInvader(xStart, yStart);
			xStart += 100;
		}
		
	}

	float loops = 0;
	float xTrans = 0.2f;
	String direction = "right";
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		g.drawString("" + playerScore, 600, 0);
		if(UFO.countTicks == true){
			ticks++;
		}
		
		if(UFO.isDead == true){
			deathTicks++;
		}
		player.move(gc, g);
		player.shoot(gc, g);
		player.checkForCollisions(gc, g);
		UFO.move(gc, g);
//		for (int i = 0; i < largeAndSmallInvaders.length; i++) {
//			for (int j = 0; j < largeAndSmallInvaders )
//		}
		
		for (int i = 0; i < largeInvaderRow1.length; i++) {
			if (largeInvaderRow1[i].isDead == true) {
				largeInvaderRow1[i].largeInvaderAnimation.stop();
			} else {
				largeInvaderRow1[i].tryToShoot(gc, g);
				
				if (direction.equals("right")) {
					if (largeInvaderRow1[i].getX() > 1100) {
						for (int j = 0; j < largeInvaderRow1.length; j++) {
							largeInvaderRow1[j].animate(gc, g, largeInvaderRow1[j].getX() - 1, largeInvaderRow1[j].getY() + 50);
							smallInvader[j].animate(gc, g, smallInvader[j].getX() - 1, smallInvader[j].getY() + 50);
						}
						direction = "left";
					} else {
						largeInvaderRow1[i].animate(gc, g, largeInvaderRow1[i].getX() + xTrans, largeInvaderRow1[i].getY());
						
					}
				} else if (direction.equals("left")) {
					if (largeInvaderRow1[i].getX() < 100) {
						for (int j = 0; j < largeInvaderRow1.length; j++) {
							largeInvaderRow1[j].animate(gc, g, largeInvaderRow1[j].getX() + 1, largeInvaderRow1[j].getY() + 50);
							smallInvader[j].animate(gc, g, smallInvader[j].getX() + 1, smallInvader[j].getY() + 50);
						}
						direction = "right";
					} else {
						largeInvaderRow1[i].animate(gc, g, largeInvaderRow1[i].getX() - xTrans, largeInvaderRow1[i].getY());
					}
				}
			}
		}
		

		for (int i = 0; i < smallInvader.length; i++) {
			if (smallInvader[i].isDead == true) {
				smallInvader[i].smallInvaderAnimation.stop();
			} else {
				
				if (largeInvaderRow1[i].getY() >= Main.GAME_HEIGHT) {
					exit = true;
				}
				if (direction.equals("right")) {
					if (smallInvader[i].getX() > 1100) {
						for (int j = 0; j < smallInvader.length; j++) {
							largeInvaderRow1[j].animate(gc, g, largeInvaderRow1[j].getX() - 1, largeInvaderRow1[j].getY() + 50);
							smallInvader[j].animate(gc, g, smallInvader[j].getX() - 1, smallInvader[j].getY() + 50);
						}
						direction = "left";
					} else {
						smallInvader[i].animate(gc, g, smallInvader[i].getX() + xTrans, smallInvader[i].getY());
					}
				} else if (direction.equals("left")) {
					if (smallInvader[i].getX() < 100) {
						for (int j = 0; j < smallInvader.length; j++) {
							largeInvaderRow1[j].animate(gc, g, largeInvaderRow1[j].getX() + 1, largeInvaderRow1[j].getY() + 50);
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
