package co.edu.uptc.model;

public class Discount {
	private String name;
	private double percent;
	
	public Discount(String name, double percent) {
		this.name = name;
		this.percent = percent;
	}
	
	public String toString() {
		return name + "," + percent; 
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPercent() {
		return percent;
	}

	public void setPercent(double d) {
		this.percent = d;
	}
	
}
