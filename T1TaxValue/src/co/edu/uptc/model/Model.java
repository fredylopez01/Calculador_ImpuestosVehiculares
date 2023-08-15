package co.edu.uptc.model;

public class Model {
	private int year;
	private double value;
	
	public Model(int year, double value) {
		this.year = year;
		this.value = value;
	}
	
	@Override
	public String toString() {
		return ";" + year + " " + value + ";";
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}
	
}
