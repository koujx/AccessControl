package access.model;

public class Object{
	private int dataid;
	private int ownerid;
	private String partment;
	private String position;
	private String dataType;
	private String security;
	
	public int getDataid() {
		return dataid;
	}
	public void setDataid(int dataid) {
		this.dataid = dataid;
	}
	public int getOwnerid() {
		return ownerid;
	}
	public void setOwnerid(int ownerid) {
		this.ownerid = ownerid;
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
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public String getSecurity() {
		return security;
	}
	public void setSecurity(String security) {
		this.security = security;
	}
}