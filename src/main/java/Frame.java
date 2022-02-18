import java.util.ArrayList;

public class Frame {
	
	private Integer firstBallPinCount = null;
	
	private Integer secondBallPinCount = null;
	
	private Integer fillBallPinCount = null;
	
	
	/**
	 * Get number of pins knocked down by each roll in the frame as an ordered Integer ArrayList
	 * @return pins list
	 */
	public ArrayList<Integer> getFrameToIntegerList() {
		ArrayList<Integer> ballPinsList = new ArrayList<Integer>();
		
		if(this.firstBallPinCount != null) {
			ballPinsList.add(getFirstBallPinCount());
		}
		if(this.secondBallPinCount != null) {
			ballPinsList.add(getSecondBallPinCount());
		}
		if(this.fillBallPinCount != null) {
			ballPinsList.add(getFillBallPinCount());
		}
		return ballPinsList;
	}
	
	/**
	 * Set 1st, 2nd, or fill ball pin count to the number of pins knocked down by the current roll in the frame
	 * depending on which roll is next in the frame
	 * @param pins
	 */
	public void setCurrentBallPinCount(Integer pins) {
		if(this.firstBallPinCount == null) {
			setFirstBallPinCount(pins);
		}
		else if(this.secondBallPinCount == null) {
			setSecondBallPinCount(pins);
		}
		else if(this.fillBallPinCount == null) {
			setFillBallPinCount(pins);
		}
	}
	
	/**
	 * Get the pin count for the most recent roll stored in this frame
	 * @return most recent ball's pin count
	 */
	public Integer getRecentBallPinCount() {
		if(this.firstBallPinCount == null) {
			return null;
		}
		else if(this.secondBallPinCount == null) {
			return getFirstBallPinCount();
		}
		else if(this.fillBallPinCount == null) {
			return getSecondBallPinCount();
		}
		return getFillBallPinCount();
	}
	
	/**
	 * Return a boolean specifying whether rolls have already been recorded for the 1st and 2nd roll in this frame
	 * @return boolean
	 */
	public Boolean firstAndSecondRollComplete(){
		if(getFirstBallPinCount() != null && getSecondBallPinCount() != null) {
			return true;
		}
		return false;
	}
	
	/**
	 * @return the firstBallPinCount
	 */
	public Integer getFirstBallPinCount() {
		return firstBallPinCount;
	}

	/**
	 * @param firstBallPinCount the firstBallPinCount to set
	 */
	public void setFirstBallPinCount(Integer firstBallPinCount) {
		this.firstBallPinCount = firstBallPinCount;
	}

	/**
	 * @return the secondBallPinCount
	 */
	public Integer getSecondBallPinCount() {
		return secondBallPinCount;
	}

	/**
	 * @param secondBallPinCount the secondBallPinCount to set
	 */
	public void setSecondBallPinCount(Integer secondBallPinCount) {
		this.secondBallPinCount = secondBallPinCount;
	}

	/**
	 * @return the fillBallPinCount
	 */
	public Integer getFillBallPinCount() {
		return fillBallPinCount;
	}

	/**
	 * @param fillBallPinCount the fillBallPinCount to set
	 */
	public void setFillBallPinCount(Integer fillBallPinCount) {
		this.fillBallPinCount = fillBallPinCount;
	}

	/**
	 * @param firstBallPinCount
	 * @param secondBallPinCount
	 * @param fillBallPinCount
	 */
	public Frame(Integer firstBallPinCount, Integer secondBallPinCount, Integer fillBallPinCount) {
		this.firstBallPinCount = firstBallPinCount;
		this.secondBallPinCount = secondBallPinCount;
		this.fillBallPinCount = fillBallPinCount;
	}
	
	/**
	 * default constructor
	 */
	public Frame() {
	}
	

	
}
