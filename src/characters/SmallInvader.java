package characters;

import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

import main.Main;
import states.GameState;

public class SmallInvader {
	private Image SMALL_INVADER_FRAME1;
	private Image SMALL_INVADER_FRAME2;
	public Rectangle smallInvader;
	private final int SMALL_INVADER_WIDTH = 32;
	private final int SMALL_INVADER_HEIGHT = 32;
	public Animation smallInvaderAnimation;
	private final Image[] SMALL_INVADER_ANIMATION_FRAMES = new Image[2];
	public boolean isDead = false;
	private float x;
	private int y;
	public Rectangle projectile;
	private Vector2f projectileVelocity;
	private Image PROJECTILE_IMAGE;
	private final int PROJECTILE_WIDTH = 2;
	private final int PROJECTILE_HEIGHT = 12;
	public boolean canShoot = true;
	public boolean spaceState = false;

	public SmallInvader(int x, int y) throws SlickException {
		SMALL_INVADER_FRAME1 = new Image("textures/tracyDepot2/smallTracy1.png");
		SMALL_INVADER_FRAME2 = new Image("textures/tracyDepot2/smallTracy2.png");
		SMALL_INVADER_ANIMATION_FRAMES[0] = SMALL_INVADER_FRAME1.getScaledCopy(0.5f);
		SMALL_INVADER_ANIMATION_FRAMES[1] = SMALL_INVADER_FRAME2.getScaledCopy(0.5f);
		smallInvader = new Rectangle(x, y, SMALL_INVADER_WIDTH, SMALL_INVADER_HEIGHT);
		smallInvaderAnimation = new Animation(SMALL_INVADER_ANIMATION_FRAMES, 100);
		this.x = x;
		this.y = y;
		
		PROJECTILE_IMAGE = new Image("textures/tracyDepot2/projectile.png");
		projectile = new Rectangle(1281, 721, PROJECTILE_WIDTH, PROJECTILE_HEIGHT);
		projectileVelocity = new Vector2f(0, -5);
		
	}

	public float getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void animate(GameContainer gc, Graphics g, float x, int y) {
		if (isBeingShot() != true) {
			Color previousColor = g.getColor();
			g.setColor(Color.black);
			g.fill(smallInvader);
			smallInvader.setLocation(x, y);
			g.drawAnimation(smallInvaderAnimation, x, y);
			g.setColor(previousColor);
		} else {

		}
		this.x = x;
		this.y = y;
	}

	public boolean isBeingShot() {
		if (GameState.player.projectile.getMinY() <= smallInvader.getMaxY()
				&& GameState.player.projectile.getMinY() >= smallInvader.getMinY()
				&& GameState.player.projectile.getMaxX() < smallInvader.getMaxX()
				&& GameState.player.projectile.getMinX() > smallInvader.getMinX()) {
			LargeInvader.myFILES.stop();
			LargeInvader.myFILES.playAsSoundEffect(1.f, 1.f, false);
			isDead = true;
			GameState.playerScore++;
			GameState.deadCount++;
			GameState.player.projectile.setLocation(1281, 721);
			GameState.player.spaceState = false;
			GameState.player.canShoot = true;
			return true;
		} else {
			return false;
		}

	}
	
	public void tryToShoot(GameContainer gc, Graphics g) {
		g.texture(projectile, PROJECTILE_IMAGE, true);
		if (Math.random() < .0005) {
			spaceState = true;
			if (canShoot == true) {
				projectile.setLocation(getX() + 25, getY());
				canShoot = false;
			}
		}

		if (spaceState == true) {

			if (projectile.getMaxY() < Main.GAME_HEIGHT) {
				projectile.setLocation(projectile.getX() + projectileVelocity.getX(),
						projectile.getY() + projectileVelocity.getY() * -1);
				canShoot = false;
			} else {
				// projectile.setLocation(player.getX() + 25, player.getY());
				projectile.setLocation(1281, 721);
				spaceState = false;
				canShoot = true;
			}
		}
	}
	

}
