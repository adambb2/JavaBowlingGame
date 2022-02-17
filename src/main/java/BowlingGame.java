import java.util.ArrayList;

import java.util.List;



public class BowlingGame {
	
	Integer score = 0;
	
	private Integer maxFrameCount = 10;
	
	private Integer maxPinCount = 10;
	
	private List<Frame> frameList = new ArrayList<Frame>();
	
	private List<Integer> pinList = new ArrayList<Integer>();
	
	
	
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
			pinList.add(pins);
			
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
				
				List<ArrayList<Integer>> nestedFrameIntegerList =  getNestedFrameIntegerList();
				
				for(int frameIndex = 0; frameIndex < nestedFrameIntegerList.size(); frameIndex++){
					ArrayList<Integer> currentFrame = nestedFrameIntegerList.get(frameIndex);
					for(int ballIndex = 0; ballIndex < currentFrame.size(); ballIndex++){
					    score += currentFrame.get(ballIndex);
						//strike extra points
						if(currentFrame.get(ballIndex) == maxPinCount && ballIndex+1 <= currentFrame.size()){
							score += pinList.get(i+1);
						}
						if(pinList.get(i) == maxPinCount && i+2 <= pinList.size()){
							score += pinList.get(i+2);
						}
						//spare extra points
						//TODO: this is going to incorrectly detect a spare when two frames such as this are side by side (3|6),(4|3) 
						if(i+1 <= pinList.size() && pinList.get(i) == maxPinCount && (pinList.get(i) + pinList.get(i+1)) == maxPinCount){
							score += pinList.get(i+1);
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
	
	//TODO: Method to get the next two non-null roll pin sum after a strike has occured
	
	//TODO: Method to get the next non-null roll pin after a spare has occured
	
//	private Map<Integer, List<Integer>> getFrameMapFromFrameList(){
//		Map<Integer, List<Integer>> frameMap = new HashMap<Integer, List<Integer>>();
//		
//		for(int i=0; i<frameList.size(); i++) {
//			frameMap.put(i+1, frameList.get(i).getFrameToList());
//		}
//		
//		return frameMap;
//	}
	
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