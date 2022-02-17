
public class Roll {

	private Integer frameNumber;
	
	private Integer pinCount;
	
	
	/**
	 * @param pinCount
	 * @param frameNumber
	 */
	public Roll(Integer frameNumber, Integer pinCount) {
		super();
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
