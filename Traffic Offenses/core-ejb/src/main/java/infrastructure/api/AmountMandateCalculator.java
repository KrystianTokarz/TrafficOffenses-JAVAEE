package infrastructure.api;

import domainmodel.domain.event.EventItem;

import javax.ejb.Local;
import java.util.Collection;
import java.util.List;

@Local
public interface AmountMandateCalculator {

    int calculateMandateAmount(Collection<EventItem> eventItems);
}
