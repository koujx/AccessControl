package access.db;

public class IpReg {
	private Integer id;
	private String name;
	private String partment;
	private String regexp;
	private String description;
		
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	public String getRegexp() {
		return regexp;
	}
	public void setRegexp(String regexp) {
		this.regexp = regexp;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
