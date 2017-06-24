package writemodel;

import domainmodel.domain.event.EventItem;
import java.util.List;

public class CreateMandateCommand implements Command {

    private String drivingLicenseNumber;

    private List<EventItem> mandatePositions;


    public CreateMandateCommand(String drivingLicenseNumber, List<EventItem> mandatePositions) {
        this.drivingLicenseNumber = drivingLicenseNumber;
        this.mandatePositions = mandatePositions;
    }

    public String getDrivingLicenseNumber() {
        return drivingLicenseNumber;
    }

    public List<EventItem> getMandatePositions() {
        return mandatePositions;
    }
}
