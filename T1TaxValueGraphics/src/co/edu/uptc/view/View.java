package co.edu.uptc.view;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class View extends JFrame {
	private static final long serialVersionUID = 1L;
	private PanelOne panel1;
	private PanelTwo panel2;
	private PanelThree panel3;
	
	public View(ActionListener listener, WindowListener wl, String discountOne, String discountTwo, String discountThree, String[] brands) {
		super("Calculador de Impuestos");
		this.setSize(550, 300);
		initComponents(discountOne, discountTwo, discountThree, listener, brands);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.addWindowListener(wl);
	}
	
	public void initComponents(String discountOne, String discountTwo, String discountThree, ActionListener lsitener, String[] brands) {
		panel1 = new PanelOne(lsitener, brands);
		this.getContentPane().add(panel1, BorderLayout.NORTH);
		panel2 = new PanelTwo(discountOne, discountTwo, discountThree);
		this.getContentPane().add(panel2, BorderLayout.CENTER);
		panel3 = new PanelThree(lsitener);
		this.getContentPane().add(panel3, BorderLayout.SOUTH);
	}
	
	public void updateValue(String value) {
		panel1.getResponse().setText("El avaluo de este vehículo es: " + value);
	}
	
	public void updateTaxValue( double taxValue) {
		panel3.getTaxValue().setText("El valor del impuesto a pagar es: " + String.valueOf(taxValue));
	}
	
	public String brand() {
		return panel1.getBrandResponse().getSelectedItem().toString();
	}
	
	public String line() {
		return panel1.getLineResponse().getSelectedItem().toString();
	}
	
	public String model() {
		return panel1.getModelResponse().getSelectedItem().toString();
	}
	
	public void updateLines(String[] linesString) {
		panel1.getLineResponse().setModel(new DefaultComboBoxModel<String>(linesString));
	}
	
	public void updateModels(String[] modelsString) {
		panel1.getModelResponse().setModel(new DefaultComboBoxModel<String>(modelsString));
	}
	
	public boolean[] responsesDiscounts() {
		return panel2.responses();
	}
	
	public int readInt(String message) {
		return Integer.parseInt(JOptionPane.showInputDialog(message));
	}
	
	public String readString(String message) {
		return JOptionPane.showInputDialog(message);
	}
	
	public void showMessage(String message) {
		JOptionPane.showMessageDialog(null, message);
	}
	
	public boolean readBoolean(String message) {
		if(JOptionPane.showConfirmDialog(null, message) == 0) {
			return true;
		}
		return false;
	}
	
}