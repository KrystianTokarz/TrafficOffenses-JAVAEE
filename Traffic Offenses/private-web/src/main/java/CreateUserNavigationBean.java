import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean(name = "createUserNavigationBean")
@RequestScoped
public class CreateUserNavigationBean {

    public String createPublicUser(){
        return "add-public-user.xhtml?faces-redirect=true";
    }

    public String createPrivateUser(){
        return "add-private-user.xhtml?faces-redirect=true";
    }

    public String createAdminUser(){
        return "add-admin-user.xhtml?faces-redirect=true";
    }

}
