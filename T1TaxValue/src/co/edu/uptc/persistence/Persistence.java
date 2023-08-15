package co.edu.uptc.persistence;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Properties;

import co.edu.uptc.model.Brand;
import co.edu.uptc.model.Discount;
import co.edu.uptc.model.Line;
import co.edu.uptc.model.Model;
import co.edu.uptc.model.Range;

public class Persistence {
	private String route;
	private Properties ratesFile;
	private Properties discountsFile;
	
	public Persistence(String route) {
		this.setRoute(route);
		ratesFile = new Properties();
		discountsFile = new Properties();
	}
	
	public void readDatesCSV(ArrayList<Brand> brands) {
		ArrayList<String> models = new ArrayList<>();
		FileReader file;
		try {
			file = new FileReader("data/Guia_CSV_324.csv");
			BufferedReader buffer = new BufferedReader(file);
			String line;
			while((line = buffer.readLine()) != null) {
				models.add(line);
			}
			models.stream().filter(x ->x.split(",")[2].equals("\"AUTOMOVIL\"")).forEach(x -> readLine(x, brands));
			file.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void readLine(String model, ArrayList<Brand> brands) {
		String[] modelString = model.split(",");
		int year = 1970;
		for(int i = 11; i <= 65; i++) {
			if(Integer.parseInt(modelString[i]) != 0) {
				readBrands(modelString[1].replace("\"", "") + "_" + modelString[5].replace("\"", "") + "_" + year + "_" + Integer.parseInt(modelString[i])*1000 + "\n", brands);
			}
			year++;
		}
	}
	
	public void readDates(ArrayList<Brand> brands) {
		FileReader file;
		try {
			file = new FileReader(route);
			BufferedReader buffer = new BufferedReader(file);
			String brand;
			while((brand = buffer.readLine()) != null) {
				readBrands(brand, brands);
			}
			file.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void readBrands(String modelLine, ArrayList<Brand> brands) {
		String[] modelString = modelLine.split("_");
		Brand brandSearch = brandSearch(brands, modelString[0]);
		if(brandSearch != null) {
			verifyLine(brandSearch, modelString);
		} else {
			createBrand(modelString, brands);
		}
	}
	
	public void verifyLine(Brand brandSearch, String[] modelString) {
		Line lineSearch = lineSearch(brandSearch.getLines(), modelString[1]);
		if(lineSearch != null) {
			Model modelSearch = modelSearch(lineSearch.getModels(), Integer.parseInt(modelString[2]));
			if(modelSearch == null) {
				Model model = new Model(Integer.parseInt(modelString[2]), Double.parseDouble(modelString[3]));
				lineSearch.addModel(model);
			}
		} else {
			Line line = new Line(modelString[1]);
			Model model = new Model(Integer.parseInt(modelString[2]), Double.parseDouble(modelString[3]));
			line.addModel(model);
			brandSearch.addLine(line);
		}
	}
	
	public void createBrand(String[] modelString, ArrayList<Brand> brands) {
		Brand brand = new Brand(modelString[0]);
		Line line = new Line(modelString[1]);
		Model model = new Model(Integer.parseInt(modelString[2]), Double.parseDouble(modelString[3]));
		line.addModel(model);
		brand.addLine(line);
		brands.add(brand);
	}
	
	public Brand brandSearch(ArrayList<Brand> brands, String name) {
		Brand brand = null;
		for(Brand i: brands) {
			if(i.getName().equals(name)) {
				brand = i;
			}
		}
		return brand;
	}
	
	public Line lineSearch(ArrayList<Line> lines, String name) {
		Line line = null;
		for(Line i: lines) {
			if(i.getName().equals(name)) {
				line = i;
			}
		}
		return line;
	}
	
	public Model modelSearch(ArrayList<Model> models, int year) {
		Model model = null;
		for(Model i: models) {
			if(i.getYear() == year) {
				model = i;
			}
		}
		return model;
	}
	
	public void createFileModels(ArrayList<Brand> brands){
		try {
			FileWriter file = new FileWriter(route);
			ArrayList<String> models = modelsString(brands);
			for(String i: models) {
				file.write(i + "\n");
			}
			file.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<String> modelsString(ArrayList<Brand> brands) {
		ArrayList<String> models = new ArrayList<String>();
		for(Brand i: brands) {
			ArrayList<Line> lines = i.getLines();
			for(Line j: lines) {
				for(Model k: j.getModels()) {
					models.add(i.getName() + "_" + j.getName() + "_" + k.getYear() + "_" + k.getValue());
				}
			}
		}
		return models;
	}
	
	public void createRatesFile(Map<String, Range> rates) {
		try {
			writeRates(rates);
			ratesFile.store(new FileWriter("src/co/edu/uptc/files/rates.properties"), "Rangos Calculador de impuestos");
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void writeRates(Map<String, Range> rates) {
		for(Object i: rates.keySet()) {
			ratesFile.put((String) i, rates.get(i).toString());
		}
	}
	
	public void readRatesFile(Map<String, Range> rates) {
		try {
			ratesFile.load(new FileReader("src/co/edu/uptc/files/rates.properties"));
			readRates(rates);
		} catch (FileNotFoundException e) {
			rates.put("1", new Range(0.015, 0, 52483000));
			rates.put("2", new Range(0.025, 52483000, 118033000));
			rates.put("3", new Range(0.035, 118033000, 1999999999));
			createRatesFile(rates);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void readRates(Map<String, Range> rates) {
		String value = null;
		String[] values = new String[3];
		for(Object i: ratesFile.keySet()) {
			value = (String) ratesFile.get(i);
			values = value.split(",");
			rates.put((String) i, new Range(Double.parseDouble(values[0]),Integer.parseInt(values[1]), Integer.parseInt(values[2])));
		}
	}
	
	public void createDiscountsFile(ArrayList<Discount> discounts) {
		try {
			writeDiscounts(discounts);
			discountsFile.store(new FileWriter("src/co/edu/uptc/files/discounts.properties"), "Descuentos Calculador de impuestos");
		} catch (IOException e) {
			System.out.println(e.getMessage());;
		}
	}
	
	public void writeDiscounts(ArrayList<Discount> discounts) {
		for(Discount i: discounts) {
			String[] discount = i.toString().split(",");
			discountsFile.put(discount[0], discount[1]);
		}
	}
	
	public void readDiscountsFile(ArrayList<Discount> discounts) {
		try {
			discountsFile.load(new FileReader("src/co/edu/uptc/files/discounts.properties"));
			readDiscounts(discounts);
		} catch (FileNotFoundException e) {
			discounts.add(new Discount("Pago Oportuno", 0.10));
			discounts.add(new Discount("Matriculado en Boyaca", 0.15));
			discounts.add(new Discount("Vehiculo Hibrido o Electrico", 0.25));
			createDiscountsFile(discounts);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void readDiscounts(ArrayList<Discount> discounts) {
		for(Object i: discountsFile.keySet()) {
			discounts.add(new Discount((String) i, Double.parseDouble((String) discountsFile.get(i))));
		}
	}

	public String getRoute() {
		return route;
	}

	public void setRoute(String route) {
		this.route = route;
	}

	public Properties getRatesFile() {
		return ratesFile;
	}

	public void setRatesFile(Properties ratesFile) {
		this.ratesFile = ratesFile;
	}

	public Properties getDiscounts() {
		return discountsFile;
	}

	public void setDiscounts(Properties discounts) {
		this.discountsFile = discounts;
	}
	
}
