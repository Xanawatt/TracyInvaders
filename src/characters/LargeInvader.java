package characters;

import java.io.IOException;

import javax.swing.text.AttributeSet.ColorAttribute;

import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.ShapeFill;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.fills.GradientFill;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.ShapeRenderer;
import org.newdawn.slick.geom.Transform;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.util.ResourceLoader;

import main.Main;
import states.GameState;

public class LargeInvader {
	private Image LARGE_INVADER_FRAME1;
	private Image LARGE_INVADER_FRAME2;
	public Rectangle largeInvader;
	private final int LARGE_INVADER_WIDTH = 48;
	private final int LARGE_INVADER_HEIGHT = 32;
	public Animation largeInvaderAnimation;
	private final Image[] LARGE_INVADER_ANIMATION_FRAMES = new Image[2];
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
	public static Audio myFILES;
	public LargeInvader(int x, int y) throws SlickException {
		LARGE_INVADER_FRAME1 = new Image("textures/tracyDepot2/largeTracy1.png");
		LARGE_INVADER_FRAME2 = new Image("textures/tracyDepot2/largeTracy2.png");
		LARGE_INVADER_ANIMATION_FRAMES[0] = LARGE_INVADER_FRAME1.getScaledCopy(0.5f);
		LARGE_INVADER_ANIMATION_FRAMES[1] = LARGE_INVADER_FRAME2.getScaledCopy(0.5f);
		largeInvader = new Rectangle(x, y, LARGE_INVADER_WIDTH, LARGE_INVADER_HEIGHT);
		largeInvaderAnimation = new Animation(LARGE_INVADER_ANIMATION_FRAMES, 100);
		this.x = x;
		this.y = y;
		
		PROJECTILE_IMAGE = new Image("textures/tracyDepot2/projectile.png");
		projectile = new Rectangle(1281, 721, PROJECTILE_WIDTH, PROJECTILE_HEIGHT);
		projectileVelocity = new Vector2f(0, 5);
		try {
			myFILES = AudioLoader.getAudio("OGG", ResourceLoader.getResourceAsStream("myFiles.ogg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
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
			g.fill(largeInvader);
			largeInvader.setLocation(x, y);
			g.drawAnimation(largeInvaderAnimation, x, y);
			g.setColor(previousColor);
		}
		this.x = x;
		this.y = y;
	}

	public boolean isBeingShot() {
		if (GameState.player.projectile.getMinY() <= largeInvader.getMaxY()
				&& GameState.player.projectile.getMinY() >= largeInvader.getMinY()
				&& GameState.player.projectile.getMaxX() < largeInvader.getMaxX()
				&& GameState.player.projectile.getMinX() > largeInvader.getMinX()) {
			//LargeInvader.myFILES.stop();
			myFILES.playAsSoundEffect(1.f, 1.f, false);
			isDead = true;

			GameState.playerScore = GameState.playerScore + 5;

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

}
