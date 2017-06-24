package infrastructure.api;

import domainmodel.domain.snapshot.Snapshot;
import domainmodel.domain.user.User;

import javax.ejb.Local;

@Local
public interface DrivingLicenseStatusManager {

    boolean checkDrivingLicenseIsStillActive(Snapshot snapshot,User user);

    void changeLicenseStatusToInactive(User user);


}
