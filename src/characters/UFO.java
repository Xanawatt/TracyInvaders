package characters;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;


import states.GameState;

public class UFO {
	
	private static Image UFO_IMAGE;
	private static Rectangle ufo;
	private final int UFO_WIDTH = 48;
	private final int UFO_HEIGHT = 32;
	public static boolean isDead = false;
	private int x;
	private int y;
	

	public UFO(int x, int y) throws SlickException{
		ufo = new Rectangle(x, y, UFO_WIDTH, UFO_HEIGHT);
		UFO_IMAGE = new Image("textures/tracyDepot2/smallTracy1.png");
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public static void move(GameContainer gc, Graphics g) {
		if (isBeingShot() != true) {
			Color previousColor = g.getColor();
			g.texture(ufo, UFO_IMAGE, true);
			g.setColor(previousColor);
		} 
		
		if(GameState.ticks >= 1000) {
			//Insert incremental move method call
			GameState.ticks = 0;
			
		} else {

		}
	}
	
	public static boolean isBeingShot() {
		if (GameState.player.projectile.getMinY() <= ufo.getMaxY()
				&& GameState.player.projectile.getMaxX() < ufo.getMaxX()
				&& GameState.player.projectile.getMinX() > ufo.getMinX()) {
			isDead = true;
			GameState.playerScore = GameState.playerScore + 100;
			GameState.player.projectile.setLocation(1281, 721);
			GameState.player.spaceState = false;
			GameState.player.canShoot = true;
			return true;
		} else {
			return false;
		}
	
	}
}
