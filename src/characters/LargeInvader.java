package characters;

import javax.swing.text.AttributeSet.ColorAttribute;

import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.ShapeFill;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.fills.GradientFill;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.ShapeRenderer;
import org.newdawn.slick.geom.Transform;

import states.GameState;

public class LargeInvader {
	private Image LARGE_INVADER_FRAME1;
	private Image LARGE_INVADER_FRAME2;
	private Rectangle largeInvader;
	private final int LARGE_INVADER_WIDTH = 48;
	private final int LARGE_INVADER_HEIGHT = 32;
	public Animation largeInvaderAnimation;
	private final Image[] LARGE_INVADER_ANIMATION_FRAMES = new Image[2];
	public boolean isDead = false;
	private int x;
	private int y;

	public LargeInvader(int x, int y) throws SlickException {
		LARGE_INVADER_FRAME1 = new Image("textures/tracyDepot2/largeTracy1.png");
		LARGE_INVADER_FRAME2 = new Image("textures/tracyDepot2/largeTracy2.png");
		LARGE_INVADER_ANIMATION_FRAMES[0] = LARGE_INVADER_FRAME1.getScaledCopy(0.5f);
		LARGE_INVADER_ANIMATION_FRAMES[1] = LARGE_INVADER_FRAME2.getScaledCopy(0.5f);
		largeInvader = new Rectangle(x, y, LARGE_INVADER_WIDTH, LARGE_INVADER_HEIGHT);
		largeInvaderAnimation = new Animation(LARGE_INVADER_ANIMATION_FRAMES, 100);
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void animate(GameContainer gc, Graphics g, int x, int y) {
		if (isBeingShot() != true) {
			Color previousColor = g.getColor();
			g.setColor(Color.black);
			g.fill(largeInvader);
			g.drawAnimation(largeInvaderAnimation, x, y);
			g.setColor(previousColor);
		} else {

		}
	}

	public boolean isBeingShot() {
		if (GameState.player.projectile.getMinY() <= largeInvader.getMaxY()
				&& GameState.player.projectile.getMaxX() < largeInvader.getMaxX()
				&& GameState.player.projectile.getMinX() > largeInvader.getMinX()) {
			isDead = true;
			GameState.playerScore++;
			GameState.player.projectile.setLocation(1281, 721);
			GameState.player.spaceState = false;
			GameState.player.canShoot = true;
			return true;
		} else {
			return false;
		}

	}

}
