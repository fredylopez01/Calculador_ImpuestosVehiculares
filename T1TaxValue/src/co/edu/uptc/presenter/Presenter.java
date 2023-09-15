package co.edu.uptc.presenter;

import co.edu.uptc.model.Brand;
import co.edu.uptc.model.Discount;
import co.edu.uptc.model.Line;
import co.edu.uptc.model.Model;
import co.edu.uptc.model.Simulator;
import co.edu.uptc.persistence.Persistence;
import co.edu.uptc.view.View;

public class Presenter {
	private View viewTest;
	private Simulator simulatorTest;
	private Persistence persistence;
	
	public Presenter() {
		viewTest = new View();
		simulatorTest = new Simulator();
		persistence = new Persistence("data/data.txt");
		persistence.readDatesCSV(simulatorTest.getBrands());
//		persistence.readDates(simulatorTest.getBrands());
		persistence.readRatesFile(simulatorTest.getRates());
		persistence.readDiscountsFile(simulatorTest.getDiscounts());
	}
	
	public static void main (String [] args) {
		Presenter presenterTest = new Presenter();
		presenterTest.run();
	}
	
	public void run() {
		int option = 0;
		do {
			option = optionMenu();
			switch(option) {
				case 1:
					optionCreate();
					break;
				case 2:
					modifyValue();
					break;
				case 3:
					viewTest.showMessage(showBrands());
					break;
				case 4:
					showTaxValue();
					break;
				case 5:
					optionDiscounts();
					break;
				case 6:
					optionTaxes();
					break;
				case 7:
					searchBrand();
					break;
				case 8:
					saveDates();
					break;
				default:
					viewTest.showMessage("Opción incorrecta");
			}
		}
		while(option != 8);
	}
	
	public int optionMenu() {
		int option;
		option = viewTest.readInt("\nBIENVENIDO AL CALCULADOR DE IMPUESTOS \n\n" + "1. Crear nuevas marcas, lineas o modelos \n2. Modificar avaluo de un carro \n3. Mostrar base de datos"
				+ "\n4. Calcular Impuesto \n5.Ver y Modificar descuentos existentes \n6. Ver y Modificar impuestos existentes \n7. Buscar Marca \n8. Salir \n\nDigite Opción");
		return option;
	}
	
	public void optionCreate() {
		int option = 0;
		do {
			option = viewTest.readInt("Que nuevo aspecto desea crear \n1. Marca \n2. Linea \n3. Modelo \n4. Volver al menu principal");
			switch(option) {
			case 1:
				verifyCreateBrand();
				break;
			case 2:
				verifyCreateLine();
				break;
			case 3:
				verifyCreateModel();
				break;
			case 4:
				break;
			default:
				viewTest.showMessage("Opción incorrecta");
				break;
			}
		} while(option != 4);	
	}
	
	public void verifyCreateBrand() {
		if(simulatorTest.addBrand(createBrand())) {
			viewTest.showMessage("Marca creada correctamente");
		}
	}
	
	public void verifyCreateLine() {
		if(simulatorTest.addLine(createBrand(), createLine())) {
			viewTest.showMessage("Linea creada correctamente");
		} else {
			viewTest.showMessage("Linea no creada. Marca no existe en el sistema");
		}
	}
	
	public void verifyCreateModel() {
		if(simulatorTest.addModel(createBrand(), createLine(), createModel())) {
			viewTest.showMessage("Modelo creado correctamente");
		} else {
			viewTest.showMessage("Modelo no creado. Marca o linea no existente en el sistema");
		}
	}
	
	public Brand createBrand() {
		return new Brand(viewTest.readString("Ingrese el nombre de la marca"));
	}
	
	public Line createLine() {
		return new Line(viewTest.readString("Ingrese el nombre de la linea"));
	}
	
	public Model createModel() {
		int year = viewTest.readInt("Ingrese el año de modelo");
		int value = viewTest.readInt("Ingrese el avaluo del auto");
		return new Model(year, value);
	}
	
	public void modifyValue() {
		Model model = searchModel();
		if(model != null) {
			int value = viewTest.readInt("Ingrese el nuevo avaluo para este automivil");
			model.setValue(value);
			viewTest.showMessage("Avaluo moficado correctamente");
		}
	}
	
	public Model searchModel() {
		Model model = null;
		Brand brand = createBrand();
		brand = simulatorTest.searchBrand(brand);
		if(brand != null) {
			Line line = createLine();
			line = simulatorTest.searchLine(brand, line);
			if(line != null) {
				model = simulatorTest.searchValue(brand, line, viewTest.readInt("Ingrese el modelo(año) del vehiculo"));
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
	
	public String showBrands() {
		StringBuilder information = new StringBuilder();
		for(Brand i: simulatorTest.getBrands()) {
			information.append("\n******" + i.getName() + "******" + showLines(i));
		}
		return information.toString();
	}
	
	public String showLines(Brand brand) {
		StringBuilder information = new StringBuilder();
		for(Line i: brand.getLines()) {
				information.append("\n--Linea: " + i.getName() + showModels(i));
		}
		return information.toString();
	}
	
	public String showModels(Line line) {
		StringBuilder information = new StringBuilder();
		for(Model i: line.getModels()) {
				information.append("\n----Año: " + i.getYear() + "    Avaluo: " + i.getValue());
		}
		return information.toString();
	}
	
	public void showTaxValue() {
		Model model = searchModel();
		if(model != null) {
			Double taxValue = (double) simulatorTest.taxValue(model);
			Double discount = applyDiscounts(taxValue);
			if(discount > taxValue) {
				discount = taxValue;
			}
			viewTest.showMessage("Valor del avaluo de su vehiculo: " + model.getValue() + "\nValor parcial del impuesto: " + taxValue +
			 "\nValor del descuento: " + discount + "\nTotal a pagar: " + (taxValue - discount));
			
		}		
	}
	
	public Double applyDiscounts(Double taxValue) {
		boolean[] responses = new boolean[3];
		int response = 0;
		for(Discount i : simulatorTest.getDiscounts()) {
			responses[response] = viewTest.readBoolean("Aplica al descuento de: " + i.getName());
			response++;
		}
		return simulatorTest.calculateDiscount(taxValue, responses);
	}
	
	public void optionDiscounts() {
		int option = 0;
		do {
			option = viewTest.readInt("1. Ver tabla de descuentos \n2. Modificar Descuentos \n3.Volver al menu principal ");
			switch(option) {
			case 1:
				viewTest.showMessage(showDiscounts());
				break;
			case 2:
				modifyDiscounts();
				break;
			case 3:
				break;
			default:
				viewTest.showMessage("Opción incorrecta");
			}
		} while(option !=3);
	}
	
	public String showDiscounts() {
		StringBuilder discounts = new StringBuilder();
		for(int i = 0; i < simulatorTest.getDiscounts().size(); i++) {
			discounts.append("*****Descuento" + (i+1) + "***** \nNombre: " + simulatorTest.getDiscounts().get(i).getName() + 
					"\nPorcentaje: " + simulatorTest.getDiscounts().get(i).getPercent() + "\n\n");
		}
		return discounts.toString();
	}
	
	public void modifyDiscounts() {
		for(int i = 0; i < simulatorTest.getDiscounts().size(); i++) {
			simulatorTest.getDiscounts().get(i).setName(viewTest.readString("Nuevo nombre para el descuento " + (i+1)));
			simulatorTest.getDiscounts().get(i).setPercent(Double.parseDouble(viewTest.readString("Nuevo porcentaje para el descuento " + (i+1))));
		}
	}
	
	public void optionTaxes() {
		int option = 0;
		do {
			option = viewTest.readInt("1. Ver tabla de Impuestos \n2. Modificar Impuestos \n3.Volver al menu principal");
			switch(option) {
			case 1:
				viewTest.showMessage(showTaxes());
				break;
			case 2:
				modifyTaxes();
				break;
			case 3:
				break;
			default:
				viewTest.showMessage("Opción incorrecta");
			}
		}while(option != 3);
	}
	
	public String showTaxes() {
		String taxes = "";
		for(Object i: simulatorTest.getRates().keySet()) {
			taxes += "*****Impuesto" + i + "***** \nMinimo: " + simulatorTest.getRates().get(i).getMin()
					+ "\nMaximo: " + simulatorTest.getRates().get(i).getMax() + "\nPorcentaje: " +  simulatorTest.getRates().get(i).getPercent() + "\n\n";
		}
		return taxes;
	}
	
	public void modifyTaxes() {
		for(Object i: simulatorTest.getRates().keySet()) {
			simulatorTest.getRates().get(i).setMin(viewTest.readInt("Ingrese el nuevo valor para el minimo " + i));
			simulatorTest.getRates().get(i).setMax(viewTest.readInt("Nuevo maximo valor para el impuesto " + i));
			simulatorTest.getRates().get(i).setPercent(Double.parseDouble(viewTest.readString("Nuevo porcentaje para el impuesto " + i)));
		}
	}
	
	public void searchBrand() {
		Brand brandtest = createBrand();
		Brand brand = simulatorTest.searchBrand(brandtest);
		if(brand != null) {
			viewTest.showMessage(brand.getName() + showLines(brand));
		} else {
			viewTest.showMessage("La marca que ingreso no existe en el sistema");
		}
	}
	
	public void saveDates() {
		persistence.createFileModels(simulatorTest.getBrands());
		persistence.createDiscountsFile(simulatorTest.getDiscounts());
		persistence.createRatesFile(simulatorTest.getRates());
	}
	
}
