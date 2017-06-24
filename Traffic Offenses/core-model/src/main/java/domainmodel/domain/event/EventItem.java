package domainmodel.domain.event;

import domainmodel.suport.domain.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "EVENT_ITEM")
public class EventItem  extends BaseEntity{

    private EventItemType eventItemType;
    private Integer amount;
    private Integer points;

    public EventItem() {
    }

    public EventItem(EventItemType eventItemType, Integer amount,Integer points) {
        this.eventItemType = eventItemType;
        this.amount = amount;
        this.points = points;
    }

    public EventItemType getEventItemType() {
        return eventItemType;
    }


    public Integer getAmount() {
        return amount;
    }

    public Integer getPoints(){
        return points;
    }

    public void setAmount(Integer amount){
        this.amount = amount;
    }
}
