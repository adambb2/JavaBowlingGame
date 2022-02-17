import java.util.ArrayList;

import java.util.List;



public class BowlingGame {
	
	Integer score = 0;
	
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
			else {
				
				List<Roll> rollList = getRollList();
				
				for(int rollIndex = 0; rollIndex < rollList.size(); rollIndex++){
				    score += rollList.get(rollIndex).getPinCount();
					//STRIKE first subsequent ball extra points
					if(rollIndex+1 <= rollList.size() && rollList.get(rollIndex).getPinCount() == maxPinCount){
						score += rollList.get(rollIndex+1).getPinCount();
					}
					//STRIKE second subsequent ball extra points
					if(rollIndex+2 <= rollList.size() && rollList.get(rollIndex).getPinCount() == maxPinCount){
						score += rollList.get(rollIndex+2).getPinCount();
					}
					//SPARE extra points
					if(rollIndex+2 <= rollList.size() && (rollList.get(rollIndex).getPinCount() + rollList.get(rollIndex+1).getPinCount()) == maxPinCount){
						//rolls equaling a SPARE must occur in the same frame
						if(rollList.get(rollIndex).getFrameNumber() == rollList.get(rollIndex+1).getFrameNumber()) {
							score += rollList.get(rollIndex+2).getPinCount();
						}
					}
				}
				
				return score;
			}
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
	
	private Boolean fillBallEarned(){
		if(getCurrentFrameNumber() == maxFrameCount && getCurrentFrame().getFirstBallPinCount() != null && getCurrentFrame().getSecondBallPinCount() != null) {
			if((getCurrentFrame().getFirstBallPinCount() + getCurrentFrame().getSecondBallPinCount()) >= maxPinCount) {
				return true;
			}
		}
		return false;
	}
	
	//trying to roll after the frames are up, the fill ball has already been rolled, or trying to roll a fill ball if it wasn't earned
	private Boolean gameComplete() {
		if(getCurrentFrameNumber() > maxFrameCount || getCurrentFrame().getFillBallPinCount() != null || (getCurrentFrameNumber() == maxFrameCount && getCurrentFrame().firstAndSecondRollComplete() == true && fillBallEarned() == false)) {
			return true;
		}
		return false;
	}
	
	private List<ArrayList<Integer>> getNestedFrameIntegerList(){
		List<ArrayList<Integer>> nestedFrameIntegerList = new ArrayList<ArrayList<Integer>>();
		
		for(int i=0; i<frameList.size(); i++) {
			nestedFrameIntegerList.add(frameList.get(i).getFrameToIntegerList());
		}
		
		return nestedFrameIntegerList;
	}
	
	private List<Roll> getRollList(){
		List<Roll> rollList = new ArrayList<Roll>();
		
		for(int frameNumber=1; frameNumber<frameList.size()+1; frameNumber++) {
			List<Integer> frameRolls = frameList.get(frameNumber).getFrameToIntegerList();
			for(int rollIndex=0; rollIndex<frameRolls.size(); rollIndex++) {
				Integer pinCount = frameRolls.get(rollIndex);
				rollList.add(new Roll(frameNumber, pinCount));
			}
		}
		return rollList;
	}
	
	
}