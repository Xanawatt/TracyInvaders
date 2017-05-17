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
	public static int playerTicks;
	public static int invulnTicks;

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

	public static int playerLives = 3;
	public static Image lifeImage;

	public static double deadCount = 0;
	public boolean win = false;

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		playerXPosition = 610;
		playerYPosition = 630;
		ticks = 0;
		deathTicks = 0;
		playerTicks = 0;
		invulnTicks = 0;
		largeInvaderRow1 = new LargeInvader[10];
		largeInvaderRow2 = new LargeInvader[10];
		smallInvaderRow1 = new SmallInvader[10];
		smallInvaderRow2 = new SmallInvader[10];
		smallInvaderRow3 = new SmallInvader[10];

		largeInvader = new LargeInvader[2][10];
		smallInvader = new SmallInvader[3][10];

		playerScore = 0;
		exit = false;

		originalHighScore = 0;
		highScore = 0;
		highScoreString = "";

		fileName = "highScore.txt";

		line = null;

		playerLives = 3;
		Image lifeImage;

		deadCount = 0;
		win = false;

		// tracyImage = new Image("textures/tracyDepot2/largeTracy1.png");
		player = new Player(playerXPosition, playerYPosition);

		lifeImage = new Image("textures/tracyDepot2/player.png");
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
			while ((line = bufferedReader.readLine()) != null) {
				highScoreString += line;
			}
			bufferedReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("Unable to open file '" + fileName + "'");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Error reading file '" + fileName + "'");
			e.printStackTrace();
		}

		originalHighScore = Integer.parseInt(highScoreString);
		highScore = originalHighScore;
	}

	float loops = 0;
	float xTrans = 0.3f;
	String direction = "right";
	boolean allDead = false;

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {

		// g.drawString("DeadCount: " + deadCount, 0, 60);
		if (deadCount == 50) {
			win = true;
		}

		g.drawString("" + playerScore, 600, 0);

		g.drawString("High Score: " + highScore, 250, 0);
		if (playerScore > highScore) {
			highScore = playerScore;
		}

		g.drawString("Lives:" + playerLives, 0, 10);
		// g.drawString("" + playerTicks, 100, 100);

		if (UFO.countTicks == true) {
			ticks++;
		}

		if (Player.countTicks == true) {
			playerTicks++;
			if (playerTicks >= 250) {
				Player.deathPause = false;
				Player.countTicks = false;
			}
		}

		if (Player.invuln == true) {
			invulnTicks++;
			if (invulnTicks >= 100) {
				Player.invuln = false;
			}
		}

		if (UFO.isDead == true) {
			deathTicks++;
		}
		player.move(gc, g);
		player.shoot(gc, g);
		player.checkForCollisions(gc, g, sbg);
		UFO.move(gc, g);

		if (player.deathPause == false) {
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
						smallInvader[i][j].tryToShoot(gc, g);
						if (smallInvader[i][j].getY() > 600) {
							exit = true;
						}
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

		} else {
			g.drawString("Boom, you're dead boi", (Main.GAME_WIDTH / 2) - 100, Main.GAME_HEIGHT / 2);

		}

	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		if (win == true) {
			if (originalHighScore < highScore) {
				try {
					FileWriter fileWriter = new FileWriter(fileName);
					BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

					bufferedWriter.write(Integer.toString(highScore));
					bufferedWriter.close();
				} catch (IOException e) {
					System.out.println("Error writing to file '" + fileName + "'");
					e.printStackTrace();
				}
			}
			sbg.enterState(Main.WIN_STATE, new RotateTransition(), new EmptyTransition());
		}
		if (exit == true) {
			if (originalHighScore < highScore) {
				try {
					FileWriter fileWriter = new FileWriter(fileName);
					BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

					bufferedWriter.write(Integer.toString(highScore));
					bufferedWriter.close();
				} catch (IOException e) {
					System.out.println("Error writing to file '" + fileName + "'");
					e.printStackTrace();
				}
			}
			sbg.enterState(Main.GAMEOVER_STATE, new RotateTransition(), new EmptyTransition());
		}

	}

	@Override
	public int getID() {
		return 1;
	}
}
