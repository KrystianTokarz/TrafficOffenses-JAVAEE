package settings;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.io.Serializable;

@ManagedBean(name = "authenticationBean")
@SessionScoped
public class AuthenticationBean implements Serializable{

    private static final String PRIVATE_ROLE = "ROLE_PRIVATE";
    private static final String ADMINISTRATOR_ROLE = "ROLE_ADMINISTRATOR";
    private static final String LOGOUT_REDIRECT_PAGE = "/index.xhtml";

    public void logout() throws IOException{
        ExternalContext externalContext = externalContext();
        externalContext.invalidateSession();
        externalContext.redirect(externalContext.getRequestContextPath() + LOGOUT_REDIRECT_PAGE);
    }

    private ExternalContext externalContext() {
        return FacesContext.getCurrentInstance().getExternalContext();
    }

    public boolean isInPrivateRole(){
       return externalContext().isUserInRole(PRIVATE_ROLE);
    }

    public boolean isInAdministratorRole(){
        return externalContext().isUserInRole(ADMINISTRATOR_ROLE);
    }
}
