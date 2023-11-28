package co.edu.uptc.view;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PanelTwo extends JPanel {
	private static final long serialVersionUID = 1L;
	private JLabel title;
	private JCheckBox discount1;
	private JCheckBox discount2;
	private JCheckBox discount3;
	
	public PanelTwo(String discountOne, String discountTwo, String discountThree) {
		initComponents(discountOne, discountTwo, discountThree);
	}
	
	public void initComponents(String discountOne, String discountTwo, String discountThree) {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		title = new JLabel("DESCUENTOS\n Marque los descuentos a los que se acoje");
		this.add(title);
		discount1 = new JCheckBox(discountOne);
		this.add(discount1);
		discount2 = new JCheckBox(discountTwo);
		this.add(discount2);
		discount3 = new JCheckBox(discountThree);
		this.add(discount3);
	}
	
	public boolean[] responses() {
		boolean[] discounts = new boolean[3];
		if(discount1.isSelected()) {
			discounts[0] = true;
		}
		if(discount2.isSelected()) {
			discounts[1] = true;
		}
		if(discount3.isSelected()) {
			discounts[2] = true;
		}
		return discounts;
	}

	public JCheckBox getDiscount1() {
		return discount1;
	}

	public void setDiscount1(JCheckBox discount1) {
		this.discount1 = discount1;
	}

	public JCheckBox getDiscount2() {
		return discount2;
	}

	public void setDiscount2(JCheckBox discount2) {
		this.discount2 = discount2;
	}

	public JCheckBox getDiscount3() {
		return discount3;
	}

	public void setDiscount3(JCheckBox discount3) {
		this.discount3 = discount3;
	}
}
