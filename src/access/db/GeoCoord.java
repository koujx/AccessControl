package access.db;

public class GeoCoord{
	private Integer id;
	private String points;
	private String description;
	private Double weight;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getPoints() {
		return points;
	}
	public void setPoints(String points) {
		this.points = points;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Double getWeight() {
		return weight;
	}
	public void setWeight(Double weight) {
		this.weight = weight;
	}
}