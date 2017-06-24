package settings;

import api.exception.command.CommandValidationException;
import api.exception.command.SynchronousCommandException;
import custom.code.MessageCode;
import error.codes.ErrorCode;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;


@ManagedBean
@SessionScoped
public class FacesContextMessageBean implements Serializable {

    @ManagedProperty("#{resourceBundleBean}")
    private ResourceBundleBean resourceBundleBean;

    private static final String INFORMATION_CODE ="information-code.";
    private static final String INFORMATION_CODE_SUMMARY ="information-code.summary";
    private static final String ERROR_SERVER_VALIDATION_CODE ="error-server-validation-code.";
    private static final String ERROR_SERVER_VALIDATION_CODE_SUMMARY ="error-server-validation-code.summary";
    private static final String ERROR_SERVER_EXCEPTION_CODE = "error-server-exception-code.";


    public void showCommandValidationError(CommandValidationException exception){
        List<ErrorCode> errorCodes = exception.getErrorCodes();
        errorCodes.forEach(e -> showServerValidationErrorMessage(FacesMessage.SEVERITY_ERROR,e));
    }

    public void showSynchronousCommandException(SynchronousCommandException exception){
        showServerExceptionErrorMessage(FacesMessage.SEVERITY_ERROR, exception.getErrorCode());
    }


    public void showMessage(FacesMessage.Severity severity,MessageCode messageCode){
        showMessageInformation(severity,messageCode);
    }

    private void showMessageInformation(FacesMessage.Severity severity, MessageCode messageCode){
        ResourceBundle bundle = resourceBundleBean.getBundle();
        String message = bundle.getString(INFORMATION_CODE + messageCode.name());
        String summary = bundle.getString(INFORMATION_CODE_SUMMARY);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity,summary,message));
    }

    private void showServerValidationErrorMessage(FacesMessage.Severity severity, ErrorCode errorCode){
        ResourceBundle bundle = resourceBundleBean.getBundle();
        String message = bundle.getString(ERROR_SERVER_VALIDATION_CODE + errorCode.name());
        String summary = bundle.getString(ERROR_SERVER_VALIDATION_CODE_SUMMARY);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity,summary,message));
    }

    private void showServerExceptionErrorMessage(FacesMessage.Severity severity, ErrorCode errorCode){
        ResourceBundle bundle = resourceBundleBean.getBundle();
        String message = bundle.getString(ERROR_SERVER_EXCEPTION_CODE + errorCode.name());
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity,null,message));
    }

    public void setResourceBundleBean(ResourceBundleBean resourceBundleBean) {
        this.resourceBundleBean = resourceBundleBean;
    }
}
