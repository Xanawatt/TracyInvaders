package states;

import java.io.IOException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import org.newdawn.slick.util.ResourceLoader;

import characters.Player;
import main.Main;

public class WinState extends BasicGameState {
	public static Audio longMYFILES;

	@Override
	public void init(GameContainer arg0, StateBasedGame sbg) throws SlickException {
		try {
			longMYFILES = AudioLoader.getAudio("OGG", ResourceLoader.getResourceAsStream("longMyFiles.ogg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		g.drawString("YOU WIN!!!",(Main.GAME_WIDTH / 2) - 100, (Main.GAME_HEIGHT / 2) - 10);
		g.drawString("Accuracy: " +  Player.accuracyPercent * 100 + "%", (Main.GAME_WIDTH / 2) - 100, Main.GAME_HEIGHT / 2);
		g.drawString("Press Enter to play again", (Main.GAME_WIDTH / 2) - 100, (Main.GAME_HEIGHT / 2) + 10);
		longMYFILES.playAsSoundEffect(1.f, 1.f, false);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int arg2) throws SlickException {
		if (gc.getInput().isKeyDown(Input.KEY_ENTER)) {
			sbg.init(gc);
			sbg.enterState(Main.GAME_STATE, new FadeOutTransition(), new FadeInTransition());
		}
		
	}

	@Override
	public int getID() {
		return 4;
	}

}
