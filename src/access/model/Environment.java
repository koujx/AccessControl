package access.model;

public class Environment{
	private String ip;
	private String latitude;
	private String longitude;
	private boolean mobile;
	
	public String envToString(){
		String mobile = this.mobile?"有线网":"无线网";
		return this.ip+","+this.latitude+","+this.longitude+","+mobile;		
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
}