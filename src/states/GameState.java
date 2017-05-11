package states;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

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
import org.newdawn.slick.state.transition.EmptyTransition;
import org.newdawn.slick.state.transition.RotateTransition;

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
	public static SmallInvader[] smallInvaderRow1 = new SmallInvader[10];
	public static SmallInvader[] smallInvaderRow2 = new SmallInvader[10];
	public static SmallInvader[] smallInvaderRow3 = new SmallInvader[10];

	public static LargeInvader[][] largeInvader = new LargeInvader[2][10];
	public static SmallInvader[][] smallInvader = new SmallInvader[3][10];

	public static int playerScore = 0;
	public static boolean exit = false;
	
	public static int originalHighScore = 0;
	public static int highScore = 0;
	public static String highScoreString = "";
	
	String fileName = "highScore.txt";
	
	String line = null;

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		// tracyImage = new Image("textures/tracyDepot2/largeTracy1.png");
		player = new Player(playerXPosition, playerYPosition);
		ufo = new UFO(0, 50);

		int xStart = 100;
		int yStart = 100;
		for (int i = 0; i < largeInvader.length; i++) {
			for (int j = 0; j < largeInvader[i].length; j++) {
				largeInvader[i][j] = new LargeInvader(xStart, yStart);
				xStart += 100;
			}
			xStart = 100;
			yStart += 50;
		}

		xStart = 108;
		yStart = 200;
		for (int i = 0; i < smallInvader.length; i++) {
			for (int j = 0; j < smallInvader[i].length; j++) {
				smallInvader[i][j] = new SmallInvader(xStart, yStart);
				xStart += 100;
			}
			xStart = 108;
			yStart += 50;

		}
		try {
			FileReader fileReader = new FileReader(fileName);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			while((line = bufferedReader.readLine()) != null) {
				highScoreString += line;
			}
			bufferedReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("Unable to open file '" + fileName + "'");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Error reading file '" + fileName +"'");
			e.printStackTrace();	
		}
		
		originalHighScore = Integer.parseInt(highScoreString);
		highScore = originalHighScore;

		/*
		 * for (int i = 0; i < largeInvaderRow1.length; i++) {
		 * largeInvaderRow1[i] = new LargeInvader(xStart, yStart); xStart +=
		 * 100; }
		 * 
		 * yStart += 100; for (int i = 0; i < largeInvaderRow2.length; i++) {
		 * largeInvaderRow2[i] = new LargeInvader(xStart, yStart); xStart +=
		 * 100;
		 * 
		 * }
		 */

		/*
		 * xStart = 100; yStart = 400; for (int i = 0; i <
		 * smallInvaderRow1.length; i++) { smallInvaderRow1[i] = new
		 * SmallInvader(xStart, yStart); xStart += 100; } yStart += 100; for
		 * (int i = 0; i < smallInvaderRow2.length; i++) { smallInvaderRow2[i] =
		 * new SmallInvader(xStart, yStart); xStart += 100; } yStart+= 100; for
		 * (int i = 0; i < smallInvaderRow3.length; i++) { smallInvaderRow3[i] =
		 * new SmallInvader(xStart, yStart); xStart += 100; }
		 */

	}

	float loops = 0;
	float xTrans = 0.3f;
	String direction = "right";

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		g.drawString("" + playerScore, 600, 0);
		g.drawString("High Score: " + highScore, 250, 0);
		if (playerScore > highScore) {
			highScore = playerScore;
		}
		if (UFO.countTicks == true) {
			ticks++;
		}

		if (UFO.isDead == true) {
			deathTicks++;
		}
		player.move(gc, g);
		player.shoot(gc, g);
		player.checkForCollisions(gc, g, sbg);
		UFO.move(gc, g);

		for (int i = 0; i < largeInvader.length; i++) {
			for (int j = 0; j < largeInvader[i].length; j++) {
				if (largeInvader[i][j].isDead == true) {
					largeInvader[i][j].largeInvaderAnimation.stop();
				} else {
					largeInvader[i][j].tryToShoot(gc, g);
					if (direction.equals("right")) {
						if (largeInvader[i][j].getX() > 1100) {
							for (int k = 0; k < largeInvader.length; k++) {
								for (int l = 0; l < largeInvader[k].length; l++) {
									largeInvader[k][l].animate(gc, g, largeInvader[k][l].getX() - 1,
											largeInvader[k][l].getY() + 50);
								}
							}
							for (int k = 0; k < smallInvader.length; k++) {
								for (int l = 0; l < smallInvader[k].length; l++) {
									smallInvader[k][l].animate(gc, g, smallInvader[k][l].getX() - 1,
											smallInvader[k][l].getY() + 50);
								}
							}
							direction = "left";
						} else {
							largeInvader[i][j].animate(gc, g, largeInvader[i][j].getX() + xTrans,
									largeInvader[i][j].getY());

						}
					} else if (direction.equals("left")) {
						if (largeInvader[i][j].getX() < 100) {
							for (int k = 0; k < largeInvader.length; k++) {
								for (int l = 0; l < largeInvader[k].length; l++) {
									largeInvader[k][l].animate(gc, g, largeInvader[k][l].getX() + 1,
											largeInvader[k][l].getY() + 50);
								}
							}
							for (int k = 0; k < smallInvader.length; k++) {
								for (int l = 0; l < smallInvader[k].length; l++) {
									smallInvader[k][l].animate(gc, g, smallInvader[k][l].getX() + 1,
											smallInvader[k][l].getY() + 50);
								}
							}
							direction = "right";
						} else {
							largeInvader[i][j].animate(gc, g, largeInvader[i][j].getX() - xTrans,
									largeInvader[i][j].getY());
						}
					}
				}
			}
		}

		for (int i = 0; i < smallInvader.length; i++) {
			for (int j = 0; j < smallInvader[i].length; j++) {
				if (smallInvader[i][j].isDead == true) {
					smallInvader[i][j].smallInvaderAnimation.stop();
				} else {

					// smallInvader[i][j].tryToShoot(gc, g);


					//smallInvader[i][j].tryToShoot(gc, g);
					if (smallInvader[i][j].getY() > 600) {
						exit = true;
					}

					smallInvader[i][j].tryToShoot(gc, g);


					if (direction.equals("right")) {
						if (smallInvader[i][j].getX() > 1100) {
							for (int k = 0; k < smallInvader.length; k++) {
								for (int l = 0; l < smallInvader[k].length; l++) {
									smallInvader[k][l].animate(gc, g, smallInvader[k][l].getX() - 1,
											smallInvader[k][l].getY() + 50);
								}
							}
							for (int k = 0; k < largeInvader.length; k++) {
								for (int l = 0; l < largeInvader[k].length; l++) {
									largeInvader[k][l].animate(gc, g, largeInvader[k][l].getX() - 1,
											largeInvader[k][l].getY() + 50);
								}
							}
							direction = "left";
						} else {
							smallInvader[i][j].animate(gc, g, smallInvader[i][j].getX() + xTrans,
									smallInvader[i][j].getY());

						}
					} else if (direction.equals("left")) {
						if (smallInvader[i][j].getX() < 100) {
							for (int k = 0; k < smallInvader.length; k++) {
								for (int l = 0; l < smallInvader[k].length; l++) {
									smallInvader[k][l].animate(gc, g, smallInvader[k][l].getX() + 1,
											smallInvader[k][l].getY() + 50);
								}
							}
							for (int k = 0; k < largeInvader.length; k++) {
								for (int l = 0; l < largeInvader[k].length; l++) {
									largeInvader[k][l].animate(gc, g, largeInvader[k][l].getX() + 1,
											largeInvader[k][l].getY() + 50);
								}
							}
							direction = "right";
						} else {
							smallInvader[i][j].animate(gc, g, smallInvader[i][j].getX() - xTrans,
									smallInvader[i][j].getY());
						}
					}
				}
			}
		}
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		if (exit == true) {
			if (originalHighScore < highScore)
				
			sbg.enterState(Main.GAMEOVER_STATE, new RotateTransition(), new EmptyTransition());
		}
	}

	@Override
	public int getID() {
		return 1;
	}
}
