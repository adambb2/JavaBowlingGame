import java.util.Hashtable;
import java.util.Stack;

public class BowlingGame {
	
	private Integer maxFrameCount = 10;
	
	private Integer maxPinCount = 10;
	
	private Integer strikePinCount = 10;
	
	private Hashtable<Integer, String> frameDictionary = new Hashtable<Integer, String>();
	
	
	
	
	/**
	 * default constructor
	 */
	public BowlingGame() {
	}

	/**
	 * Method that updates the bowling game frame history with the number of pins that are knocked down with each roll. This method must be called
	 * in the sequential order that the rolls occured.
	 * @param pins
	 * @return void
	 */
	public void roll(Integer pins) {
		try {
			validateRoll(pins);
			if(pins == strikePinCount) {
				System.out.println("Rolled a STRIKE! Moving to next frame.");
			}	
		}
		catch(IllegalStateException ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	/**
	 * Method that calculates the score of the bowling game. Can only be called at the end of the 10 frame game.
	 * @return score
	 */
	public int score() {
		return 1;
	}
	
	
	/**
	 * Method that validates that the number of pins knocked down by the roll is not outside what is possible for a roll in the current frame
	 * @param pins
	 */
	private void validateRoll(Integer pins) {
		if(pins > maxPinCount) {
			throw new IllegalStateException("Pin count exceeds pins on the lane");
		}
		if(pins < 0) {
			throw new IllegalStateException("Negative roll is invalid");
		}
	}
	
	
	
}