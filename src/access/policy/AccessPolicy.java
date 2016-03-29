package access.policy;

public class AccessPolicy{
	private String attributes;
	private String vectors;
	private int numOfAttributes;
	private int lengthOfVector;
	

	public String getAttributes() {
		return attributes;
	}
	public void setAttributes(String attributes) {
		this.attributes = attributes;
	}
	public String getVectors() {
		return vectors;
	}
	public void setVectors(String vectors) {
		this.vectors = vectors;
	}
	public int getNumOfAttributes() {
		return numOfAttributes;
	}
	public void setNumOfAttributes(int numOfAttributes) {
		this.numOfAttributes = numOfAttributes;
	}
	public int getLengthOfVector() {
		return lengthOfVector;
	}
	public void setLengthOfVector(int lengthOfVector) {
		this.lengthOfVector = lengthOfVector;
	}
}