package infrastructure.api;

import domainmodel.domain.event.EventItemType;

import javax.ejb.Local;
import java.util.List;

@Local
public interface OffensesManager {

    List<EventItemType> getEventItemTypeList();
}
