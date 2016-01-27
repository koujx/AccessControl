package access.db;

public class User{
	private int uid;
	private String pwd;
	private String name;
	private String partment;
	private String position;
	private String keyPath;
	private boolean vip;
	private String ip;
	private String latitude;
	private String longitude;
	private boolean mobile;
	private String lastTime;
	private int successLoginCount;
	private int failureLoginAttempts;
	private int behaviorLevel;
	
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public String getKeyPath() {
		return keyPath;
	}
	public void setKeyPath(String keyPath) {
		this.keyPath = keyPath;
	}
	public boolean isVip() {
		return vip;
	}
	public void setVip(boolean vip) {
		this.vip = vip;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public boolean isMobile() {
		return mobile;
	}
	public void setMobile(boolean mobile) {
		this.mobile = mobile;
	}
	public int getBehaviorLevel() {
		return behaviorLevel;
	}
	public void setBehaviorLevel(int behaviorLevel) {
		this.behaviorLevel = behaviorLevel;
	}
	public int getFailureLoginAttempts() {
		return failureLoginAttempts;
	}
	public void setFailureLoginAttempts(int failureLoginAttempts) {
		this.failureLoginAttempts = failureLoginAttempts;
	}
	public int getSuccessLoginCount() {
		return successLoginCount;
	}
	public void setSuccessLoginCount(int successLoginCount) {
		this.successLoginCount = successLoginCount;
	}
	public String getLastTime() {
		return lastTime;
	}
	public void setLastTime(String lastTime) {
		this.lastTime = lastTime;
	}

}