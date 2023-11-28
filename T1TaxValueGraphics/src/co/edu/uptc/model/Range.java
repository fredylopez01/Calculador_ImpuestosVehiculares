package co.edu.uptc.model;

public class Range {
	private double percent;
	private int min;
	private int max;
	
	public Range(double percent, int min, int max) {
		this.percent = percent;
		this.min = min;
		this.max = max;
	}
	
	@Override
	public String toString() {
		return percent + "," + min + "," + max;
	}

	public double getPercent() {
		return percent;
	}

	public void setPercent(double percent) {
		this.percent = percent;
	}

	public int getMin() {
		return min;
	}

	public void setMin(int min) {
		this.min = min;
	}

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}
	
}
