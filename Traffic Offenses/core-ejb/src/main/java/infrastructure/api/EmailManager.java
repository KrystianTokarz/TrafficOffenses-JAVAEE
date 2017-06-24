package infrastructure.api;

import domainmodel.domain.event.Event;
import domainmodel.domain.event.EventItem;

import javax.ejb.Local;
import java.util.List;

@Local
public interface EmailManager {

    void sendEmail(Event event);
}
