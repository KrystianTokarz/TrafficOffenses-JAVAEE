import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.io.Serializable;

@ManagedBean(name = "authenticationBean")
@SessionScoped
public class AuthenticationBean implements Serializable{

    private static final String LOGOUT_REDIRECT_PAGE = "/index.xhtml";

    public void logout() throws IOException{
        ExternalContext externalContext = externalContext();
        externalContext.invalidateSession();
        externalContext.redirect(externalContext.getRequestContextPath() + LOGOUT_REDIRECT_PAGE);
    }

    public String getUserPesel() {
        ExternalContext externalContext = externalContext();
        return (String) externalContext.getRemoteUser();
    }


    private ExternalContext externalContext() {
        return FacesContext.getCurrentInstance().getExternalContext();
    }

}
