
public class Frame {
	
	private Integer firstBallPinCount = null;
	
	private Integer secondBallPinCount = null; //second ball only occurs if a strike was NOT rolled on the first ball..  OR if it is the 10th frame
	
	private Integer fillBallPinCount = null; //a fill ball is given if a strike of spare occurs on the 10th frame

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