package domainmodel.domain.event;

import domainmodel.suport.domain.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "EVENT_ITEM")
public class EventItem  extends BaseEntity{

    private EventItemType eventItemType;
    private Integer points;

    public EventItem() {
    }

    public EventItem(EventItemType eventItemType, Integer points) {
        this.eventItemType = eventItemType;
        this.points = points;
    }

    public EventItemType getEventItemType() {
        return eventItemType;
    }

    public Integer getPoints() {
        return points;
    }
}
