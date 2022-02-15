import java.util.ArrayList;
import java.util.List;


public class BowlingGame {
	
	private Integer maxFrameCount = 10;
	
	private Integer maxPinCount = 10;
	
	private List<Frame> frameList = new ArrayList<Frame> ();
	
	
	/**
	 * default constructor, new game starts with the first Frame
	 */
	public BowlingGame() {
		frameList.add(new Frame());
	}

	/**
	 * Method that updates the bowling game frame history with the number of pins that are knocked down with each roll. This method must be called
	 * in the sequential order that the rolls occurred.
	 * @param pins
	 * @return void
	 */
	public void roll(Integer pins) {
		try {
			validateRoll(pins);
			
			if(checkStrikeFrame(pins)) {
				setStrikeFrame(pins);
				frameList.add(new Frame());
			}
			else if (checkSpareFrame(pins)){
				setSpareFrame(pins);
				frameList.add(new Frame());
			}
			else if (checkOpenFrame(pins)){
				setOpenFrame(pins);
				frameList.add(new Frame());
			}
			else {
				//first ball roll was not a strike, continuing with current frame..
				getCurrentFrame().setFirstBallPinCount(pins);
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
		//TODO: this should check that the 1stball + 2ndball in a frame are not greater than 10 (except on the 10th frame)
		if(pins  > maxPinCount) {
			throw new IllegalStateException("Pin count exceeds pins on the lane"); 
		}
		if(pins < 0) {
			throw new IllegalStateException("Negative roll is invalid");
		}
		if(frameList.size() > maxFrameCount) {
			throw new IllegalStateException("Maximum frame count exceeded. Game has already ended.");
		}
	}
	
	private Frame getCurrentFrame() {
		return frameList.get(frameList.size()-1);
	}
	
	private Integer getCurrentFrameNumber() {
		return frameList.size();
	}
	
	private Boolean checkStrikeFrame(Integer pins) {
		//check for STRIKE on the 1st ball roll
		if(pins == maxPinCount) {
			getCurrentFrame().setFirstBallPinCount(pins);
			System.out.println("Rolled a STRIKE on frame " + getCurrentFrameNumber().toString() + "! Moving to next frame.");
			return true;
		}
		return false;
	}
	
	private void setStrikeFrame(Integer pins) {
		getCurrentFrame().setFirstBallPinCount(pins);
	}
	
	private Boolean checkSpareFrame(Integer pins) {
		//check for SPARE on the 2nd ball roll
		if(getCurrentFrame().getFirstBallPinCount() != null && (getCurrentFrame().getFirstBallPinCount() + pins) == maxPinCount) {
			System.out.println("Rolled a SPARE on frame " + getCurrentFrameNumber().toString() + "! Moving to next frame.");
			return true;
		}
		return false;
	}
	
	private void setSpareFrame(Integer pins) {
		getCurrentFrame().setSecondBallPinCount(pins);
	}
	
	private Boolean checkOpenFrame(Integer pins) {
		//check for OPEN on the 2nd ball roll
		if(getCurrentFrame().getFirstBallPinCount() != null && (getCurrentFrame().getFirstBallPinCount() + pins) < maxPinCount) {
			System.out.println("Rolled an OPEN on frame " + getCurrentFrameNumber().toString() + ". Moving to next frame.");
			return true;
		}
		return false;
	}
	
	private void setOpenFrame(Integer pins) {
		getCurrentFrame().setSecondBallPinCount(pins);
	}
	
}