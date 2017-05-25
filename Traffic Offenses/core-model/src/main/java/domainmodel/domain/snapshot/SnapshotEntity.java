package domainmodel.domain.snapshot;

import domainmodel.embaddable.UserData;
import domainmodel.suport.domain.AggregateRoot;

import javax.persistence.*;
import java.util.Date;

@NamedQueries({
        @NamedQuery(
              name = "Snapshot.findLastSnapshot",
              query = "SELECT s FROM SnapshotEntity s where s.userData.userAggregateRootId = :userID  ORDER BY s.creationDate DESC"
        ),
        @NamedQuery(
                name = "Snapshot.findSnapshotWithVersion",
                query = "SELECT s FROM SnapshotEntity s where s.userData.userAggregateRootId = :userID  and s.eventVersion = :version"
        )
})
@Entity
@Table(name = "SNAPSHOT")
public class SnapshotEntity extends AggregateRoot implements Snapshot {

    private Integer numberOfPoints;
    private Long eventVersion;
    private Date creationDate;

    @Embedded
    private UserData userData;

    public SnapshotEntity() {
    }

    public SnapshotEntity(Integer numberOfPoints, Long eventVersion, UserData userData) {
        this.numberOfPoints = numberOfPoints;
        this.eventVersion = eventVersion;
        this.userData = userData;
        this.creationDate = new Date();
    }

    public Integer getNumberOfPoints() {
        return numberOfPoints;
    }

    public Long getEventVersion() {
        return eventVersion;
    }

    public UserData getUserData() {
        return userData;
    }

}
