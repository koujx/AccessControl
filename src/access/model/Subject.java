package access.model;

public class Subject{
	private int uid;
	private String partment;
	private String position;
	private boolean vip;
	private Double trust;
	private int successLoginCount;
	private int failureLoginAttempts;
	
	public String subToString(){
		String vip = this.vip?"超级用户":"普通用户";
		return this.partment+","+this.position+","+vip+","+this.trust+","
				+this.successLoginCount+","+this.failureLoginAttempts;
	}
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public String getPartment() {
		return partment;
	}
	public void setPartment(String partment) {
		this.partment = partment;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public boolean isVip() {
		return vip;
	}
	public void setVip(boolean vip) {
		this.vip = vip;
	}
	public Double getTrust() {
		return trust;
	}
	public void setTrust(Double trust) {
		this.trust = trust;
	}
	public int getSuccessLoginCount() {
		return successLoginCount;
	}
	public void setSuccessLoginCount(int successLoginCount) {
		this.successLoginCount = successLoginCount;
	}
	public int getFailureLoginAttempts() {
		return failureLoginAttempts;
	}
	public void setFailureLoginAttempts(int failureLoginAttempts) {
		this.failureLoginAttempts = failureLoginAttempts;
	}

}