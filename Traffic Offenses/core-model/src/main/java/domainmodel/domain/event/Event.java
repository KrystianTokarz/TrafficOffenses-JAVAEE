package domainmodel.domain.event;

import domainmodel.embaddable.UserData;
import domainmodel.suport.domain.AggregateRoot;

import javax.persistence.*;
import java.util.*;
@NamedQueries({
        @NamedQuery(
                name = "Event.findEventsWithVersion",
                query = "SELECT e FROM Event e where e.userData.userAggregateRootId = :userId and e.eventVersion = :version"
        ),
        @NamedQuery(
                name = "Event.findEventsAfterSelectedVersion",
                query = "SELECT e FROM Event e where e.userData.userAggregateRootId = :userId " +
                        "and e.eventVersion >= :version and e.aggregateStatus = 'ACTIVE'"
        )
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

    public Event(UserData userData,EventType eventType,Long eventVersion) {
        this.creationDate = new Date();
        this.userData = userData;
        this.eventType = eventType;
        this.eventVersion = eventVersion;
    }

    public UserData getUserData() {
        return userData;
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

    public Long getEventVersion(){
        return this.eventVersion;
    }

    public EventType getEventType() {
        return eventType;
    }

    public Integer getAllPoints(){
        Integer allPoints = 0;
        allPoints += eventItems
                    .stream()
                    .mapToInt(e -> e.getPoints()).sum();
        return allPoints;
    }

    public Integer getAllAmount(){
        Integer amount = 0;
        amount += eventItems
                .stream()
                .mapToInt(e -> e.getAmount()).sum();
        return amount;
    }

}
