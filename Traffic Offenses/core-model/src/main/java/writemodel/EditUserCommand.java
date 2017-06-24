package writemodel;

import domainmodel.domain.user.User;
import domainmodel.embaddable.DrivingLicense;

public class EditUserCommand implements Command{

    private User user;

    private DrivingLicense drivingLicense;

    public EditUserCommand(User user, DrivingLicense drivingLicense) {
        this.user = user;
        this.drivingLicense = drivingLicense;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public DrivingLicense getDrivingLicense() {
        return drivingLicense;
    }

    public void setDrivingLicense(DrivingLicense drivingLicense) {
        this.drivingLicense = drivingLicense;
    }
}
