import java.util.ArrayList;

import java.util.List;



public class BowlingGame {
	
	private Integer score = 0;
	
	private Integer maxFrameCount = 10;
	
	private Integer maxPinCount = 10;
	
	private List<Frame> frameList = new ArrayList<Frame>();
	
	
	/**
	 * default constructor, new game starts with the first Frame
	 */
	public BowlingGame() {
		frameList.add(new Frame());
	}

	/**
	 * Updates the Bowling Game's frame list with the number of pins that are knocked down with each roll. This method must be called
	 * in the sequential order that the rolls occurred. Validation checks are done to ensure the rolls and pin counts for each roll are possible
	 * @param pins
	 * @return void
	 */
	public void roll(Integer pins) {
		try {
			validateRoll(pins);
			
			if(checkStrikeRoll(pins)) {
				System.out.println("Rolled a STRIKE on frame " + getCurrentFrameNumber().toString() + "!");
				getCurrentFrame().setCurrentBallPinCount(pins);
				nextFrameIfNotFinal();
			}
			else if (checkSpareRoll(pins)){
				System.out.println("Rolled a SPARE on frame " + getCurrentFrameNumber().toString() + "!");
				getCurrentFrame().setCurrentBallPinCount(pins);
				nextFrameIfNotFinal();
			}
			else if (checkOpenRoll(pins)){
				System.out.println("Rolled an OPEN on frame " + getCurrentFrameNumber().toString() + "!");
				getCurrentFrame().setCurrentBallPinCount(pins);
				nextFrameIfNotFinal();
			}
			else {
				getCurrentFrame().setCurrentBallPinCount(pins);
			}		
		}
		catch(IllegalStateException ex) {
			System.out.println(ex.getMessage());
			throw ex;
		}
	}
	
	/**
	 * Calculates the score of the Bowling Game. Can only be called at the end of the 10 frame game.
	 * Score is equal to number of pins knocked down by each roll plus bonus points that are awarded for Strikes and Spares
	 * that occur within frames 1 to 9.
	 * @return score
	 */
	public int score() {
		try {
			if(gameComplete() != true) {
				throw new IllegalStateException("Score cannot be taken until the end of the game"); 
			}
			else {
				
				List<Roll> rollList = getRollList();
				
				for(int rollIndex = 0; rollIndex < rollList.size(); rollIndex++){
				    score += rollList.get(rollIndex).getPinCount();
				    //Bonus Points for STRIKEs and SPAREs are not given on the final frame
				    if(rollList.get(rollIndex).getFrameNumber() != maxFrameCount) {
				    	//STRIKE first subsequent ball extra points
						if(rollIndex+1 < rollList.size() && rollList.get(rollIndex).getPinCount() == maxPinCount){
							score += rollList.get(rollIndex+1).getPinCount();
						}
						//STRIKE second subsequent ball extra points
						if(rollIndex+2 < rollList.size() && rollList.get(rollIndex).getPinCount() == maxPinCount){
							score += rollList.get(rollIndex+2).getPinCount();
						}
						//SPARE subsequent ball extra points
						if(rollIndex+2 < rollList.size() && (rollList.get(rollIndex).getPinCount() + rollList.get(rollIndex+1).getPinCount()) == maxPinCount){
							//rolls equaling a SPARE must occur in the same frame
							if(rollList.get(rollIndex).getFrameNumber() == rollList.get(rollIndex+1).getFrameNumber()) {
								score += rollList.get(rollIndex+2).getPinCount();
							}
						}
				    }
				}
				
				System.out.println("Final Score: " + score.toString() + ".");
				return score;
			}
		}
		catch(IllegalStateException ex) {
			System.out.println(ex.getMessage());
			throw ex;
		}
	}
	
	
	/**
	 * Validates that the number of pins knocked down by the roll is not outside what is possible for a roll in the current frame
	 * exceptions are thrown if an invalid roll is detected
	 * @param pins
	 */
	private void validateRoll(Integer pins) {
		if(pins > maxPinCount) {
			throw new IllegalStateException("Pin count exceeds pins on the lane");
		}
		//Check that sum of (previous roll + current roll) in the current frame is not greater than 10 unless the pins reset prior to the previous roll being a STRIKE... OR a SPARE before the 10th frame Fill ball
		if(getCurrentFrame().getRecentBallPinCount() != null && (getCurrentFrame().getRecentBallPinCount() + pins)  > maxPinCount) {
			if(getCurrentFrame().getRecentBallPinCount() != maxPinCount && !(getCurrentFrameNumber() == maxFrameCount && getCurrentFrame().getFirstBallPinCount() != null && getCurrentFrame().getSecondBallPinCount() != null && (getCurrentFrame().getFirstBallPinCount() + getCurrentFrame().getSecondBallPinCount()) == maxPinCount)) {
				throw new IllegalStateException("Pin count exceeds pins on the lane"); 
			}
		}
		if(pins < 0) {
			throw new IllegalStateException("Negative roll is invalid");
		}
		if(gameComplete()) {
			throw new IllegalStateException("Cannot roll after game is over");
		}
	}
	
	/**
	 * @return current frame
	 */
	private Frame getCurrentFrame() {
		return frameList.get(frameList.size()-1);
	}
	
	/**
	 * @return current frame number
	 */
	private Integer getCurrentFrameNumber() {
		return frameList.size();
	}
	
	/**
	 * Returns true if all of the pins in the lane were knocked down, otherwise returns false
	 * @return Boolean
	 * @param pins
	 */
	private Boolean checkStrikeRoll(Integer pins) {
		if(pins == maxPinCount) {
			return true;
		}
		return false;
	}
	
	/**
	 * Checks if a spare occurred due to the previous roll plus the current roll knocking down 10 pins total
	 * @return Boolean
	 * @param pins
	 */
	private Boolean checkSpareRoll(Integer pins) {
		if(getCurrentFrame().getRecentBallPinCount() != null && (getCurrentFrame().getRecentBallPinCount() + pins) == maxPinCount) {
			return true;
		}
		return false;
	}
	
	/**
	 * Check if the current roll and the previous roll together would make an Open frame
	 * @return Boolean
	 * @param pins
	 */
	private Boolean checkOpenRoll(Integer pins) {
		if(getCurrentFrame().getRecentBallPinCount() != null && (getCurrentFrame().getRecentBallPinCount() + pins) < maxPinCount) {
			return true;
		}
		return false;
	}
	
	/**
	 * Adds a new frame to the top of the Bowling Game's frame list if the 10th frame has not been reached
	 */
	private void nextFrameIfNotFinal() {
		if(getCurrentFrameNumber() < maxFrameCount) {
			frameList.add(new Frame());
		}
	}
	
	/**
	 * Determines if a Fill ball was just earned on the 10th frame by either a Strike or Spare (all 10 pins were knocked down within the first two rolls)
	 * @return Boolean
	 */
	private Boolean fillBallEarned(){
		if(getCurrentFrameNumber() == maxFrameCount && getCurrentFrame().getFirstBallPinCount() != null && getCurrentFrame().getSecondBallPinCount() != null) {
			if((getCurrentFrame().getFirstBallPinCount() + getCurrentFrame().getSecondBallPinCount()) >= maxPinCount) {
				return true;
			}
		}
		return false;
	}
	
	
	/**
	 * Return true if the game is now complete. 
	 * If the 1st and 2nd rolls already occurred on the 10th frame then the game is over UNLESS a fill ball was earned. 
	 * If a fill ball was earned, the game is over if the 1st, 2nd and 3rd roll (fill ball) have occurred on the 10th frame.
	 * @return Boolean
	 */
	private Boolean gameComplete() {
		if(getCurrentFrameNumber() > maxFrameCount || getCurrentFrame().getFillBallPinCount() != null || (getCurrentFrameNumber() == maxFrameCount && getCurrentFrame().firstAndSecondRollComplete() == true && fillBallEarned() == false)) {
			return true;
		}
		return false;
	}
	
	/**
	 * Gets a list of Rolls from using the Frame list in the BowlingGame. Each Roll objects contains the number of pins knocked down
	 * by the Roll as well as the Frame Number that the Roll occurred in. The List<Roll> allows for simpler logic and indexing when calculating the
	 * the Bowling Game's score and determining additional points added by Strikes and Spares.
	 * @return list of Roll objects
	 */
	private List<Roll> getRollList(){
		List<Roll> rollList = new ArrayList<Roll>();
		
		for(int frameIndex=0; frameIndex<frameList.size(); frameIndex++) {
			List<Integer> frameRolls = frameList.get(frameIndex).getFrameToIntegerList();
			for(int rollIndex=0; rollIndex<frameRolls.size(); rollIndex++) {
				Integer pinCount = frameRolls.get(rollIndex);
				Integer frameNumber = frameIndex + 1;
				rollList.add(new Roll(frameNumber, pinCount));
			}
		}
		return rollList;
	}
	

	
	
}