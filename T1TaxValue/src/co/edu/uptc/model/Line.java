package co.edu.uptc.model;

import java.util.ArrayList;

public class Line {
	private String name;
	private ArrayList<Model> models;
	
	public Line(String name) {
		models = new ArrayList<Model>();
		this.name = name;
	}
	
	public boolean addModel(Model model) {
		boolean add = false;
		for(int i = 0; i < models.size(); i++) {
			if(models.get(i).getYear() == (model.getYear())) {
				add = true;
			}
		}
		if(!add) {
			models.add(model);
			add = true;
		}
		return add;
	}
	
	@Override
	public String toString() {
		return "-" + name + "-" + models;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<Model> getModels() {
		return models;
	}

	public void setModels(ArrayList<Model> models) {
		this.models = models;
	}
	
}
