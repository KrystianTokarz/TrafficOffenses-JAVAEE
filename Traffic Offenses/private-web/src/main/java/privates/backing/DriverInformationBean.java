package privates.backing;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;

@ManagedBean
@SessionScoped
public class DriverInformationBean implements Serializable{

    private String licenseNumber;

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public void clearData(){
        this.licenseNumber = "";
    }
}
