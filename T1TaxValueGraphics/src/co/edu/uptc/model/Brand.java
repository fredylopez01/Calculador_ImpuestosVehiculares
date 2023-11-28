package co.edu.uptc.model;

import java.util.ArrayList;

public class Brand {
	private String name;
	private ArrayList<Line> lines;
	
	public Brand(String name) {
		lines = new ArrayList<Line>();
		this.name = name;
	}
	
	
	public boolean addLine(Line line) {
		boolean add = false;
		for(int i = 0; i < lines.size(); i++) {
			if(lines.get(i).getName().equals(line.getName())) {
				add = true;
			}
		}
		if(!add) {
			lines.add(line);
			add = true;
		}
		return add;
	}
	
	public boolean addModel(Line line, Model model) {
		boolean add = false;
		for(int i = 0; i < lines.size(); i++) {
			if(lines.get(i).getName().equals(line.getName()) && !add) {
				if(lines.get(i).addModel(model)) {
					add = true;
				}
			}
		}
		return add;
	}
	
	@Override
	public String toString() {
		return name+ ":" + lines;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public ArrayList<Line> getLines() {
		return lines;
	}
	
	public void setLines(ArrayList<Line> lines) {
		this.lines = lines;
	}
	
}
