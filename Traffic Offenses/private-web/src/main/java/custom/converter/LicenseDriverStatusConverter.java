package custom.converter;

import domainmodel.embaddable.DrivingLicense;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("custom.converter.LicenseDriverStatusConverter")
public class LicenseDriverStatusConverter implements Converter {

    private static final String LICENSE_ACTIVE = "ACTIVE";
    private static final String LICENSE_TEMPORARY_INACTIVE = "TEMPORARY_INACTIVE";
    private static final String LICENSE_PERMANENT_INACTIVE = "PERMANENT_INACTIVE";

    @Override
    public DrivingLicense.DrivingLicenseStatus getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {

        DrivingLicense.DrivingLicenseStatus drivingLicenseStatus = null;
        if (s.equals(LICENSE_ACTIVE)) {
            drivingLicenseStatus = DrivingLicense.DrivingLicenseStatus.ACTIVE;
        } else if (s.equals(LICENSE_TEMPORARY_INACTIVE)){
            drivingLicenseStatus = DrivingLicense.DrivingLicenseStatus.TEMPORARY_INACTIVE;
        }else if(s.equals(LICENSE_PERMANENT_INACTIVE)) {
            drivingLicenseStatus = DrivingLicense.DrivingLicenseStatus.PERMANENT_INACTIVE;
        }
        return drivingLicenseStatus;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        return o.toString();
    }
}
