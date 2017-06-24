package domainmodel.suport.domain;

import javax.persistence.*;
import java.io.Serializable;

@MappedSuperclass
public abstract class AggregateRoot implements Serializable {


    public enum AggregateStatus{
        ACTIVE, INACTIVE
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long aggregateId;


    @Enumerated(EnumType.STRING)
    private AggregateStatus aggregateStatus = AggregateStatus.ACTIVE;


    public Long getAggregateRootEntityId(){
        return aggregateId;
    }

    public void setAggregateAsRemoved(){
        aggregateStatus = AggregateStatus.INACTIVE;
    }

    public void setAggregateAsActive(){
        aggregateStatus = AggregateStatus.ACTIVE;
    }

    public boolean isActive(){
        return aggregateStatus == AggregateStatus.ACTIVE;
    }

}
