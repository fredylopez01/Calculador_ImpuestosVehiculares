package co.edu.uptc.view;

import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PanelThree extends JPanel {
	private static final long serialVersionUID = 1L;
	private JButton calculate;
	private JLabel taxValue;
	
	public PanelThree(ActionListener listener) {
		initComponents(listener);
	}
	
	public void initComponents(ActionListener listener) {
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		calculate = new JButton("Calcular");
		calculate.setActionCommand("calculate");
		calculate.addActionListener(listener);
		this.add(calculate);
		taxValue = new JLabel();
		taxValue.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 15));
		this.add(taxValue);
	}

	public JLabel getTaxValue() {
		return taxValue;
	}

	public void setTaxValue(JLabel taxValue) {
		this.taxValue = taxValue;
	}
	
}
