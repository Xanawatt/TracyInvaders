package states;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import characters.Player;

public class GameoverState extends BasicGameState {

	@Override
	public void init(GameContainer arg0, StateBasedGame sbg) throws SlickException {
		
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		g.drawString("GAME OVER!!!", 250, 250);
		g.drawString("" + Player.accuracyPercent, 10, 10);
		
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int arg2) throws SlickException {
		
		
	}

	@Override
	public int getID() {
		return 2;
	}

}
