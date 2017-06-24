package custom.validator;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.LongRangeValidator;
import javax.faces.validator.ValidatorException;

@FacesValidator("privates.custom.validator.CustomMandateAmountRangeValidator")
public class CustomMandateAmountRangeValidator extends LongRangeValidator {

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        setMinimum((Integer) component.getAttributes().get("minimum"));
        setMaximum((Integer) component.getAttributes().get("maximum"));
        super.validate(context, component, value);
    }
}
