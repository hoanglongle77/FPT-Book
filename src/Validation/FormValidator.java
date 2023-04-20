package Validation;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;

import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;

public class FormValidator {

	public static boolean validateString(JTextField textField) {
		String input = textField.getText();
		if (input.isEmpty()) {
			showError(textField, "This field cannot be empty");
			return false;
		}
		clearError(textField);

		return true;
	}

	public static void validateInteger(JTextField textField) {
		String input = textField.getText().trim();
		if (input.isEmpty())
			showError(textField, "This field is empty");
		else {
			try {
				Integer.parseInt(input);
			} catch (NumberFormatException e) {
				showError(textField, "Invalid input. Please enter a valid integer.");
			}
		}
	}

	public static void validateSpinner(JSpinner spinner, int min, int max) {
		int value = (int) spinner.getValue();
		if (value < min || value > max) {
			showError(spinner, "Value out of range. Please enter a value between " + min + " and " + max + ".");
		} else {
			clearError(spinner);
		}
	}

	private static void showError(JComponent component, String message) {
		JOptionPane.showMessageDialog(component, message, "Error", JOptionPane.ERROR_MESSAGE);
		component.requestFocusInWindow();
		component.setBackground(Color.RED);
	}

	public static void clearError(JComponent component) {
		component.setBackground(Color.WHITE);
	}
	
	public static boolean areAllComponentsValid(Container container) {
	    Component[] components = container.getComponents();
	    for (Component component : components) {
	        if (component instanceof JComponent) {
	            JComponent jComponent = (JComponent) component;
	            InputVerifier inputVerifier = jComponent.getInputVerifier();
	            if (inputVerifier != null && !inputVerifier.verify(jComponent)) {
	                return false;
	            }
	        }
	    }
	    return true;
	}

	public static InputVerifier getVerifier() {
		return new InputVerifier() {
			@Override
			public boolean verify(JComponent component) {
				if (component instanceof JTextField) {
					JTextField textField = (JTextField) component;
					if (textField.getName().equals("integer")) {
						validateInteger(textField);
					} else if (textField.getName().equals("string")) {
						validateString(textField);
					}
				} else if (component instanceof JSpinner) {
					JSpinner spinner = (JSpinner) component;
					validateSpinner(spinner, 1, 100);
				}
				return true;
			}
		};
	}

}
