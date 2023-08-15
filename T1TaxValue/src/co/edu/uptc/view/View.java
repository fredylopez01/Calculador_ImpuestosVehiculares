package co.edu.uptc.view;

import javax.swing.JOptionPane;

public class View {
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