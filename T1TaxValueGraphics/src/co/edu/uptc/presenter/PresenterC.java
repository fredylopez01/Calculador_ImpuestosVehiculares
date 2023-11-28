package co.edu.uptc.presenter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.FileNotFoundException;
import java.io.IOException;

import co.edu.uptc.model.Brand;
import co.edu.uptc.model.Line;
import co.edu.uptc.model.Model;
import co.edu.uptc.model.Simulator;
import co.edu.uptc.persistence.Persistence;
import co.edu.uptc.persistence.PersistenceJson;
import co.edu.uptc.view.View;

public class PresenterC implements ActionListener, WindowListener {
	private View viewTest;
	private Simulator simulatorTest;
	private Persistence persistence;
	private PersistenceJson persistenceJson;
	
	public PresenterC() {
		simulatorTest = new Simulator();
		persistence = new Persistence("data/data.txt");
		persistenceJson = new PersistenceJson();
		try {
			persistenceJson.readDates(simulatorTest.getBrands());
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		persistence.readDatesCSV(simulatorTest.getBrands());
//		persistence.readDates(simulatorTest.getBrands());
		persistence.readRatesFile(simulatorTest.getRates());
		persistence.readDiscountsFile(simulatorTest.getDiscounts());
		viewTest = new View(this, this, "Matriculado en Boyaca", "Pago Oportuno", "Vehiculo Hibrido o Electrico", brands());
	}

	public static void main(String[] args) {
		new PresenterC();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getActionCommand();
		String comand = source.toString();
		Model model = null;
		if(comand.equals("brand")) {
			viewTest.updateLines(lines());
			String[] models = {"Elija una linea"};
			viewTest.updateModels(models);
		} else if(comand.equals("line")) {
			viewTest.updateModels(models());
		}else if(comand.equals("search")) {
			model = search();
			if(model!=null) {
				double value = model.getValue();
				viewTest.updateValue(String.valueOf(value));
			}
		} else if(comand.equals("calculate")){
			model = search();
			if(model!=null) {
				double taxValue = simulatorTest.taxValue(model);
				double taxValueT = calculate(taxValue);
				viewTest.updateTaxValue(taxValueT);
			}
		}
	}
	
	public double calculate(double taxValue) {
		boolean[] responsesDiscounts = viewTest.responsesDiscounts();
		double discount = simulatorTest.calculateDiscount(taxValue, responsesDiscounts);
		return (taxValue - discount);
	}
	
	public Model search() {
		String brand = viewTest.brand();
		String line = viewTest.line();
		String modelS = viewTest.model();
		return searchModel(brand, line, modelS);
	}
	
	public Model searchModel(String nameBrand, String nameLine, String yearModel) {
		Model model = null;
		Brand brand = new Brand(nameBrand);
		brand = simulatorTest.searchBrand(brand);
		if(brand != null) {
			Line line = new Line(nameLine);
			line = simulatorTest.searchLine(brand, line);
			if(line != null) {
				model = simulatorTest.searchValue(brand, line, Integer.parseInt(yearModel));
				if(model == null) {
					viewTest.showMessage("El modelo no existe en el sistema");
				}
			} else {
				viewTest.showMessage("La linea que ingreso no existe en el sistema");
			}
		} else {
			viewTest.showMessage("La marca que ingreso no existe en el sistema");
		}
		return model;
	}
	
	public String[] brands() {
		String[] brands = new String[simulatorTest.getBrands().size()];
		for(int i = 0;i < brands.length; i++) {
			brands[i] = simulatorTest.getBrands().get(i).getName();
		}
		return brands;
	}
	
	public String[] lines() {
		String[] lines = null;
		String nameBrand = viewTest.brand();
		Brand brand = new Brand(nameBrand);
		brand = simulatorTest.searchBrand(brand);
		if(brand != null) {
			lines = new String[brand.getLines().size()];
			for(int i = 0; i < lines.length; i++) {
				lines[i] = brand.getLines().get(i).getName();
		
			}
		}
		return lines;
	}
	
	public String[] models() {
		String[] models = null;
		String nameBrand = viewTest.brand();
		Brand brand = new Brand(nameBrand);
		brand = simulatorTest.searchBrand(brand);
		if(brand != null) {
			String nameLine = viewTest.line();
			Line line = new Line(nameLine);
			line = simulatorTest.searchLine(brand, line);
			if(line != null) {
				models = new String[line.getModels().size()];
				for(int i = 0;i < models.length; i++) {
					models[i] = String.valueOf(line.getModels().get(i).getYear());
				}
			} else {
				viewTest.showMessage("La linea que ingreso no existe en el sistema");
			}
		} else {
			viewTest.showMessage("La marca que ingreso no existe en el sistema");
		}
		return models;
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		try {
			System.out.println("Información Guardada Correctamente");
			persistenceJson.writeDates(simulatorTest.getBrands());
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
