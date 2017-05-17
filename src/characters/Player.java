package characters;

import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.EmptyTransition;
import org.newdawn.slick.state.transition.RotateTransition;

import main.Main;
import states.GameState;

public class Player {
	private Image PLAYER_IMAGE;
	private Image PROJECTILE_IMAGE;
	public Rectangle player;
	public Rectangle projectile;
	private Vector2f projectileVelocity;
	private final int PLAYER_WIDTH = 52;
	private final int PLAYER_HEIGHT = 32;
	private final int PROJECTILE_WIDTH = 2;
	private final int PROJECTILE_HEIGHT = 12;
	public int shotCount;
	public static float accuracyPercent;
	public static boolean countTicks = false;
	public static boolean invuln = false;
	public static boolean deathPause = false;
	public boolean canShoot = true;
	public boolean spaceState = false;
	boolean doneColliding = false;

	public Player(int x, int y) throws SlickException {
		PLAYER_IMAGE = new Image("textures/tracyDepot2/player.png");
		PROJECTILE_IMAGE = new Image("textures/tracyDepot2/projectile.png");
		player = new Rectangle(x, y, PLAYER_WIDTH, PLAYER_HEIGHT);
		projectile = new Rectangle(1281, 721, PROJECTILE_WIDTH, PROJECTILE_HEIGHT);
		projectileVelocity = new Vector2f(0, -10);
	}

	public void move(GameContainer gc, Graphics g) {
		g.texture(player, PLAYER_IMAGE.getScaledCopy(0.5f), true);
		if (gc.getInput().isKeyDown(Input.KEY_A)) {
			if (player.getMinX() > 0) {
				player.setX(player.getX() - 6.0f);
				GameState.playerXPosition -= 6;
			}
		} else if (gc.getInput().isKeyDown(Input.KEY_D)) {
			if (player.getMaxX() < Main.GAME_WIDTH) {
				player.setX(player.getX() + 6.0f);
				GameState.playerXPosition += 6;
			}
		}

	}

	public void enterTransition(GameContainer gc, Graphics g, StateBasedGame sbg) {
		for (int i = 0; i < GameState.largeInvader.length; i++) {
			for (int j = 0; j < GameState.largeInvader[i].length; j++) {
				GameState.largeInvader[i][j].isDead = true;
			}
		}

		for (int i = 0; i < GameState.smallInvader.length; i++) {
			for (int j = 0; j < GameState.smallInvader[i].length; j++) {
				GameState.smallInvader[i][j].isDead = true;
			}
		}
		// sbg.enterState(2, new RotateTransition(), new EmptyTransition());
		GameState.exit = true;
	}

	public void checkForCollisions(GameContainer gc, Graphics g, StateBasedGame sbg) {
		for (int i = 0; i < GameState.smallInvader.length; i++) {
			for (int j = 0; j < GameState.smallInvader[i].length; j++) {
				if (player.intersects(GameState.smallInvader[i][j].smallInvader)
						&& GameState.smallInvader[i][j].isDead == false) {
					// g.drawString("Collision", 0, 0);
					enterTransition(gc, g, sbg);
				}
			}
		}

		for (int i = 0; i < GameState.largeInvader.length; i++) {
			for (int j = 0; j < GameState.largeInvader[i].length; j++) {
				if (player.intersects(GameState.largeInvader[i][j].largeInvader)
						&& GameState.largeInvader[i][j].isDead == false && invuln == false) {
					// g.drawString("Collision", 0, 0);
					// Insert collision mechanics
					killPlayer(g);
				}
			}
		}

		for (int i = 0; i < GameState.smallInvader.length; i++) {
			for (int j = 0; j < GameState.smallInvader[i].length; j++) {
				if (player.intersects(GameState.smallInvader[i][j].projectile) && invuln == false) {
					// g.drawString("Collision", 0, 0);
					// Insert collision mechanics
					killPlayer(g);
				}
			}
		}

		for (int i = 0; i < GameState.largeInvader.length; i++) {
			for (int j = 0; j < GameState.largeInvader[i].length; j++) {
				if (player.intersects(GameState.largeInvader[i][j].projectile) && invuln == false) {
					// g.drawString("Collision", 0, 0);
					// Insert collision mechanics
					killPlayer(g);
				}
			}
		}
	}

	public void shoot(GameContainer gc, Graphics g) {
		g.texture(projectile, PROJECTILE_IMAGE, true);
		// projectile.setLocation(projectile.getX() + projectileVelocity.getX(),
		// projectile.getY() + projectileVelocity.getY());

		if (gc.getInput().isKeyPressed(Input.KEY_SPACE)) {
			spaceState = true;
			if (canShoot == true) {
				projectile.setLocation(player.getX() + 25, player.getY());
				shotCount++;
				//accuracyPercent = deadCount/shotCount
				canShoot = false;
			}
		}

		if (spaceState == true) {

			if (projectile.getMaxY() > 0) {
				projectile.setLocation(projectile.getX() + projectileVelocity.getX(),
						projectile.getY() + projectileVelocity.getY());
				canShoot = false;
			} else {
				// projectile.setLocation(player.getX() + 25, player.getY());
				projectile.setLocation(1281, 721);
				spaceState = false;
				canShoot = true;
			}
		}

	}

	public void killPlayer(Graphics g) {
		if (GameState.playerLives >= 1) {
			moveProjectiles(g);
			deathPause = true;
			GameState.playerLives--;
			GameState.playerTicks = 0;
			countTicks = true;
		}
		if (GameState.playerLives == 0) {
			GameState.exit = true;
		}

	}

	public void moveProjectiles(Graphics g) {
		for (int i = 0; i < GameState.largeInvader.length; i++) {
			for (int j = 0; j < GameState.largeInvader[i].length; j++) {
				GameState.largeInvader[i][j].projectile.setLocation(-10, 0);
			}

		}
		for (int i = 0; i < GameState.smallInvader.length; i++) {
			for (int j = 0; j < GameState.smallInvader[i].length; j++) {
				GameState.smallInvader[i][j].projectile.setLocation(-10, 0);
			}

		}

	}

}
