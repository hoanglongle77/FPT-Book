package FormValidate;

import javax.swing.InputVerifier;
import javax.swing.JComponent;

public class FormValidate extends InputVerifier {

	@Override
	public boolean verify(JComponent input) {
		return false;
	}

	
	
}
