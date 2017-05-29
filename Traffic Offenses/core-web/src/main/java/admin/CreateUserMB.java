package admin;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.event.ValueChangeEvent;

@ManagedBean(name = "createUser")
@RequestScoped
public class CreateUserMB {

    private String userAccountType;


    public String getUserAccountType() {
        return userAccountType;
    }

    public void setUserAccountType(String userAccountType) {
        System.out.println(userAccountType);
        this.userAccountType = userAccountType;

    }
}
