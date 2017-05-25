package infrastructure.repository;

import api.exception.repository.AggregateNotActiveException;
import api.exception.repository.AggregateNotFoundException;
import domainmodel.suport.domain.AggregateRoot;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.lang.reflect.ParameterizedType;

public class GenericRepository<T extends AggregateRoot> {

    @PersistenceContext(unitName = "offensesPU")
    protected EntityManager entityManager;

    private Class<T> clazz;

    public GenericRepository() {
        this.clazz = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public T load(Long aggregateId) throws AggregateNotFoundException, AggregateNotActiveException {

        T aggregate = entityManager.find(clazz, aggregateId);

        if(aggregate == null) {
            throw new AggregateNotFoundException("Aggregate " + clazz.getCanonicalName() + " with id =" + aggregateId + " does not exist");
        }else if(!aggregate.isActive()) {
            throw new AggregateNotActiveException("Aggregate " + clazz.getCanonicalName() + " with id =" + aggregateId + " is not active");
        }
        return aggregate;

    }

    public void persist(T aggregate){
        entityManager.persist(aggregate);
    }

    public T merge(T aggregate){
        return entityManager.merge(aggregate);
    }

    public void delete(Long aggregateId) throws  AggregateNotFoundException, AggregateNotActiveException {
        T aggregate =  load(aggregateId);
        aggregate.setAggregateAsRemoved();
    }
}
