package co.edu.uptc.view;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PanelOne extends JPanel {
	private static final long serialVersionUID = 1L;
	private JLabel brand;
	private JComboBox<String> brandResponse;
	private JLabel line;
	private JComboBox<String> lineResponse;
	private JLabel model;
	private JComboBox<String> modelResponse;
	private JButton search;
	private JLabel response;
	
	public PanelOne(ActionListener listener, String[] brands) {
		this.setSize(200, 200);
		initComponents(listener, brands);
	}
	
	public void initComponents(ActionListener listener, String[] brands) {
		this.setLayout(new GridLayout(4, 2));
		brand = new JLabel("Marca:");
		this.add(brand);
		brandResponse = new JComboBox<String>(brands);
		brandResponse.setActionCommand("brand");
		brandResponse.addActionListener(listener);
		this.add(brandResponse);
		line = new JLabel("Linea:");
		this.add(line);
		lineResponse = new JComboBox<String>();
		lineResponse.addItem("Elija una Marca");
		lineResponse.setActionCommand("line");
		lineResponse.addActionListener(listener);
		this.add(lineResponse);
		model = new JLabel("Modelo:");
		this.add(model);
		modelResponse = new JComboBox<String>();
		lineResponse.addItem("Elija una Linea");
		modelResponse.setActionCommand("model");
		modelResponse.addActionListener(listener);
		this.add(modelResponse);
		search = new JButton("Buscar");
		search.setActionCommand("search");
		search.addActionListener(listener);
		this.add(search);
		response = new JLabel();
		response.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 15));
		this.add(response);
	}

	public JComboBox<String> getBrandResponse() {
		return brandResponse;
	}

	public void setBrandResponse(JComboBox<String> brandResponse) {
		this.brandResponse = brandResponse;
	}

	public JComboBox<String> getLineResponse() {
		return lineResponse;
	}

	public void setLineResponse(JComboBox<String> lineResponse) {
		this.lineResponse = lineResponse;
		this.lineResponse.addItemListener(null);
	}

	public JComboBox<String> getModelResponse() {
		return modelResponse;
	}

	public void setModelResponse(JComboBox<String> modelResponse) {
		this.modelResponse = modelResponse;
	}

	public JLabel getResponse() {
		return response;
	}

	public void setResponse(JLabel response) {
		this.response = response;
	}
}
