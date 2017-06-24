package domainmodel.domain.snapshot;

import domainmodel.embaddable.UserData;
import domainmodel.suport.domain.AggregateRoot;

import java.util.Date;

public interface Snapshot{

     Integer getNumberOfPoints();

     Long getEventVersion();

     UserData getUserData();

     Long getAggregateRootEntityId();

     void setAggregateAsRemoved();

     boolean isActive();

}
