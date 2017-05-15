package states;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class HighScoreState extends BasicGameState {
	String fileName;
	int highScore;
	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		/*
		try {
			FileWriter fileWriter = new FileWriter(fileName);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			
			
			bufferedWriter.write(Integer.toString(highScore));
			bufferedWriter.close();
		} catch (IOException e) {
			System.out.println("Error writing to file '" + fileName + "'");
			e.printStackTrace();
		}
		*/
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
	
		
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		
		
	}

	@Override
	public int getID() {
		
		return 3;
	}

}
