package states;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class HighScoreState extends BasicGameState implements KeyListener {
	
	int highScore;
	String fileName = "highScore.txt";
	
	String line = null;
	public String input1 = null;
	public String input2 = null;
	public String input3 = null;
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
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

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		g.drawString("Enter Name", 500, 500);
		if (input1 != null && input2 != null && input3 != null) {
			g.drawString("Name: " + input1 + input2 + input3, 500, 510);
		}
		System.out.println("" + input1 + input2 + input3);
		
		gc.getInput().addKeyListener(this);
		System.out.print("sdjkasfsuokhsdfjl");
		
		if (input1 == null) {
			input1 = String.valueOf(gc.getInput().toString());
		} else if (input2 == null) {
			input2 = String.valueOf(gc.getInput());
		} else if (input3 == null) {
			input3 = String.valueOf(gc.getInput());
		}
		

	}

	
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {

	}

	@Override
	public int getID() {

		return 3;
	}

}
