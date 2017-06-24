package infrastructure.manager;

import domainmodel.domain.event.EventItemType;
import infrastructure.api.OffensesManager;

import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Stateless
public class OffensesManagerBean implements OffensesManager{

    public OffensesManagerBean() {
    }

    @Override
    public List<EventItemType> getEventItemTypeList(){
       return new ArrayList<EventItemType>(Arrays.asList(EventItemType.values()));
    }
}
