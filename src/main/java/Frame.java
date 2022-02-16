
public class Frame {
	
	private Integer firstBallPinCount = null;
	
	private Integer secondBallPinCount = null; //second ball only occurs if a strike was NOT rolled on the first ball..  OR if it is the 10th frame
	
	private Integer fillBallPinCount = null; //a fill ball is given if a strike of spare occurs on the 10th frame
	
	/**
	 * Set 1st, 2nd, or fill ball pin count to the number of pins knocked down by the current roll in the frame.
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
	 * Method that gets the pin count for the latest roll has been stored in the frame
	 * @param pins
	 */
	public Integer getPreviousBallPinCount() {
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
