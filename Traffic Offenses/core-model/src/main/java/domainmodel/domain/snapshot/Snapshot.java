package domainmodel.domain.snapshot;

import domainmodel.embaddable.UserData;
import domainmodel.suport.domain.AggregateRoot;

import java.util.Date;

public interface Snapshot {

     Integer getNumberOfPoints();

     Long getEventVersion();

     UserData getUserData();

     public Long getAggregateRootEntityId();

     public void setAggregateAsRemoved();


     public boolean isActive();

}
