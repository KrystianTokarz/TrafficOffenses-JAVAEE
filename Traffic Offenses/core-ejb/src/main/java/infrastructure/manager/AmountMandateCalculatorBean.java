package infrastructure.manager;

import domainmodel.domain.event.EventItem;
import infrastructure.api.AmountMandateCalculator;

import javax.ejb.Stateless;
import java.util.Collection;
import java.util.List;

@Stateless
public class AmountMandateCalculatorBean implements AmountMandateCalculator{

    @Override
    public int calculateMandateAmount(Collection<EventItem> eventItems) {
        return  eventItems.stream()
                                .mapToInt(e -> e.getAmount())
                                .sum();
    }
}
