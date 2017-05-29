import api.commandmodel.UserAccountInteface;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.io.Serializable;

@ManagedBean(name = "logoutBean")
@SessionScoped
public class LogoutBean implements Serializable{

    @EJB
    private UserAccountInteface userAccount;

    public void logout() throws IOException{
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        externalContext.invalidateSession();
        externalContext.redirect(externalContext.getRequestContextPath() + "/index.xhtml");
    }

    public void createPersion(){
        userAccount.addUser();
    }
}
