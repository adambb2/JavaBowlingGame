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
				nextFrameIfNotFinal();
			}
			else if (checkSpareFrame(pins)){
				setSpareFrame(pins);
				nextFrameIfNotFinal();
			}
			else if (checkOpenFrame(pins)){
				setOpenFrame(pins);
				nextFrameIfNotFinal();
			}
			else {
				//first ball roll was not a strike, continuing with current frame..
				getCurrentFrame().setFirstBallPinCount(pins);
			}		
		}
		catch(IllegalStateException ex) {
			System.out.println(ex.getMessage());
			throw ex;
		}
	}
	
	/**
	 * Method that calculates the score of the bowling game. Can only be called at the end of the 10 frame game.
	 * @return score
	 */
	public int score() {
		try {
			if(gameComplete() != true) {
				throw new IllegalStateException("Score cannot be taken until the end of the game"); 
			}
			return 1;
		}
		catch(IllegalStateException ex) {
			System.out.println(ex.getMessage());
			throw ex;
		}
	}
	
	
	/**
	 * Method that validates that the number of pins knocked down by the roll is not outside what is possible for a roll in the current frame
	 * @param pins
	 */
	private void validateRoll(Integer pins) {
		if(pins > maxPinCount) {
			throw new IllegalStateException("Pin count exceeds pins on the lane");
		}
		//Check that the current roll + previous roll in the current frame are not greater than 10 unless the last frame was a strike
		if(getCurrentFrame().getPreviousBallPinCount() != null && getCurrentFrame().getPreviousBallPinCount() != maxPinCount && (getCurrentFrame().getPreviousBallPinCount() + pins)  > maxPinCount) {
			throw new IllegalStateException("Pin count exceeds pins on the lane"); 
		}
		if(pins < 0) {
			throw new IllegalStateException("Negative roll is invalid");
		}
		if(gameComplete()) {
			throw new IllegalStateException("Cannot roll after game is over");
		}
	}
	
	private Frame getCurrentFrame() {
		return frameList.get(frameList.size()-1);
	}
	
	private Integer getCurrentFrameNumber() {
		return frameList.size();
	}
	
	private Boolean checkStrikeFrame(Integer pins) {
		//check for STRIKE
		if(pins == maxPinCount) {
			System.out.println("Rolled a STRIKE on frame " + getCurrentFrameNumber().toString() + "! Moving to next frame.");
			return true;
		}
		return false;
	}
	
	private void setStrikeFrame(Integer pins) {
		if(getCurrentFrameNumber() < maxFrameCount) {
			getCurrentFrame().setFirstBallPinCount(pins);
		}
		else {
			getCurrentFrame().setCurrentBallPinCount(pins);
		}
	}
	
	private Boolean checkSpareFrame(Integer pins) {
		//check for SPARE
		if(getCurrentFrame().getPreviousBallPinCount() != null && (getCurrentFrame().getPreviousBallPinCount() + pins) == maxPinCount) {
			System.out.println("Rolled a SPARE on frame " + getCurrentFrameNumber().toString() + "! Moving to next frame.");
			return true;
		}
		return false;
	}
	
	private void setSpareFrame(Integer pins) {
		if(getCurrentFrameNumber() < maxFrameCount) {
			getCurrentFrame().setSecondBallPinCount(pins);
		}
		else {
			getCurrentFrame().setCurrentBallPinCount(pins);
		}
	}
	
	private Boolean checkOpenFrame(Integer pins) {
		//check for OPEN
		if(getCurrentFrame().getPreviousBallPinCount() != null && (getCurrentFrame().getPreviousBallPinCount() + pins) < maxPinCount) {
			System.out.println("Rolled an OPEN on frame " + getCurrentFrameNumber().toString() + ". Moving to next frame.");
			return true;
		}
		return false;
	}
	
	//TODO: logic is the same as setSpareFrame method, consider consolidating into one method
	private void setOpenFrame(Integer pins) {
		if(getCurrentFrameNumber() < maxFrameCount) {
			getCurrentFrame().setSecondBallPinCount(pins);
		}
		else {
			getCurrentFrame().setCurrentBallPinCount(pins);
		}
	}
	
	private void nextFrameIfNotFinal() {
		if(getCurrentFrameNumber() < maxFrameCount) {
			frameList.add(new Frame());
		}
	}
	
	public Boolean fillBallEarned(){
		if(getCurrentFrameNumber() == maxFrameCount && getCurrentFrame().getFirstBallPinCount() != null && getCurrentFrame().getSecondBallPinCount() != null) {
			if((getCurrentFrame().getFirstBallPinCount() + getCurrentFrame().getSecondBallPinCount()) >= maxPinCount) {
				return true;
			}
		}
		return false;
	}
	
	//trying to roll after the frames are up, the fill ball has already been rolled, or trying to roll a fill ball if it wasn't earned
	public Boolean gameComplete() {
		if(getCurrentFrameNumber() > maxFrameCount || getCurrentFrame().getFillBallPinCount() != null || (getCurrentFrameNumber() == maxFrameCount && getCurrentFrame().firstAndSecondRollComplete() == true && fillBallEarned() == false)) {
			return true;
		}
		return false;
	}
	
	
	
	
}