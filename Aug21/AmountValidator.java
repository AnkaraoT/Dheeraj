package primeface;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("test.amtValidator")
public class AmountValidator implements Validator {

	private static final String AMT_PATTERN = "^[-+]?\\d{1,10}+(\\.{0,2}(\\d{0,3}))?$";

	private Pattern pattern;
	private Matcher matcher;

	public AmountValidator() {
		pattern = Pattern.compile(AMT_PATTERN);
	}

	/**
	 * 
	 * 
	 * 
	 <code>
	 1.-ve values not allowed 
	 2. <dot> . not allowed 
	 3. float format allowed
	 4. not allowed chars
	 </code>
	 */
	@Override
	public void validate(FacesContext context, UIComponent component,
			Object value) throws ValidatorException { 		
		 
		if (value == null || "".equals(value.toString().trim())) { 
			FacesMessage msg = new FacesMessage( 
					"Amount validation failed.", 
					"Invalid amount format" + value.toString() + "<empty value not allowed>"); 
			msg.setSeverity(FacesMessage.SEVERITY_ERROR); 
			throw new ValidatorException(msg); 
		} else	if (value.equals(".")) { 
			FacesMessage msg = new FacesMessage( 
					"Amount validation failed.", 
					"Invalid amount format" + value.toString() + "<dot not allowed>"); 
			msg.setSeverity(FacesMessage.SEVERITY_ERROR); 
			throw new ValidatorException(msg); 
		} else{ 
			matcher = pattern.matcher(value.toString()); 
			if (!matcher.matches()) { 
				FacesMessage msg = new FacesMessage( 
						"Amount validation failed.", 
						"Invalid amount format" + value.toString() + "<only allow digits and .(dot) >");  
				msg.setSeverity(FacesMessage.SEVERITY_ERROR); 
				throw new ValidatorException(msg); 
			} else if (new BigDecimal(value.toString()).doubleValue() < 0) { 
				FacesMessage msg = new FacesMessage( 
						"Amount validation failed.", 
						"Invalid amount format" + value.toString() + "<Negative Value cab't be accpeted>"); 
				msg.setSeverity(FacesMessage.SEVERITY_ERROR); 
				throw new ValidatorException(msg); 
			} 
		} 
	}
}
