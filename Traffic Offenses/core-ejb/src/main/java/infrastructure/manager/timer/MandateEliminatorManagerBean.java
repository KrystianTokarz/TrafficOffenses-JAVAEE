package infrastructure.manager.timer;

import cqrs.command.api.MandateEliminatorManager;
import cqrs.command.api.RemovalEventManager;
import domainmodel.domain.event.Event;
import utility.NextYearCalculator;

import javax.annotation.Resource;
import javax.ejb.*;
import java.util.Date;

@Stateless
public class MandateEliminatorManagerBean implements MandateEliminatorManager {

    @Resource
    private TimerService timerService;

    @EJB
    private RemovalEventManager removalEventsManager;

    @Override
    public void generateTimer(Event event){
        Date timerDate = NextYearCalculator.calculateNextYearDate();
        timerService.createSingleActionTimer(timerDate, generateTimerConfig(event.getAggregateRootEntityId()));
    }

    private TimerConfig generateTimerConfig(Long timerInfo){
        TimerConfig timerConfig = new TimerConfig();
        timerConfig.setInfo(timerInfo);
        return timerConfig;
    }

    @Timeout
    private void timerAction(Timer timer){
        Long eventId = (Long) timer.getInfo();
        removalEventsManager.createEventToDeletePointsAfterYear(eventId);
    }


}
