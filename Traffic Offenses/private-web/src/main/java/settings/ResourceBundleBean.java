package settings;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.util.ResourceBundle;


@ManagedBean
@SessionScoped
public class ResourceBundleBean implements Serializable {

    @ManagedProperty("#{msg}")
    private ResourceBundle bundle;

    public void setBundle(ResourceBundle bundle) {
        this.bundle = bundle;
    }

    public ResourceBundle getBundle() {
        return bundle;
    }
}
