
public class Roll {

	private Integer frameNumber;
	
	private Integer pinCount;
	
	
	/**
	 * Constructor
	 * @param pinCount (number of pins knocked down by the roll)
	 * @param frameNumber in which the ball roll occurred
	 */
	public Roll(Integer frameNumber, Integer pinCount) {
		this.pinCount = pinCount;
		this.frameNumber = frameNumber;
	}

	/**
	 * @return the pinCount
	 */
	public Integer getPinCount() {
		return pinCount;
	}

	/**
	 * @param pinCount the pinCount to set
	 */
	public void setPinCount(Integer pinCount) {
		this.pinCount = pinCount;
	}

	/**
	 * @return the frameNumber
	 */
	public Integer getFrameNumber() {
		return frameNumber;
	}

	/**
	 * @param frameNumber the frameNumber to set
	 */
	public void setFrameNumber(Integer frameNumber) {
		this.frameNumber = frameNumber;
	}

	
}
