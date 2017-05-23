package domainmodel.domain.event;

import domainmodel.embaddable.UserData;
import domainmodel.suport.domain.AggregateRoot;

import javax.persistence.*;
import java.util.*;
@NamedQueries({
        @NamedQuery(
                name = "Event.findAllEvents",
                query = "SELECT e FROM Event e where e.userData.userAggregateRootId = :userId"
        ),
        @NamedQuery(
                name = "Event.findEventsWithVersion",
                query = "SELECT e FROM Event e where e.userData.userAggregateRootId = :userId and e.eventVersion = :version"
        ),
})
@Entity
public class Event extends AggregateRoot {

    private Date creationDate;
    private Long eventVersion;
    private EventType eventType;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "event_id")
    private Set<EventItem> eventItems = new HashSet<EventItem>();

    @Embedded
    private UserData userData;

    public Event() {
    }

    public Event(UserData userData,EventType eventType) {
        this.creationDate = new Date();
        this.userData = userData;
        this.eventType = eventType;
    }


    public void addEventItem(EventItem item){
        eventItems.add(item);
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public Collection<EventItem> getEventItemList() {
        return Collections.unmodifiableCollection(eventItems);
    }

    public void setEventVersion(Long newEventVersion){
        this.eventVersion = newEventVersion;
    }

    public Long getEventVersion(){
        return this.eventVersion;
    }

    public EventType getEventItemType() {
        return eventType;
    }

    public Integer getAllPoints(){
        Integer allPoints = 0;
        allPoints += eventItems
                    .stream()
                    .mapToInt(e -> e.getPoints()).sum();
        return allPoints;
    }

}
