package pl.mw.instrument.dao;

import org.apache.commons.lang3.Validate;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.springframework.stereotype.Repository;
import pl.mw.instrument.entity.Instrument;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * <p>Created by mwiesiolek on 12/01/2016.</p>
 */
@Repository
public class InstrumentDao extends AbstractDao<Instrument> {

    @Override
    public void saveOrUpdate(final Instrument entity) {
        Validate.notNull(entity);

        super.saveOrUpdate(entity);
    }

    @Override
    public Instrument find(final Serializable id) {
        Validate.notNull(id);

        return (Instrument) getSession().get(Instrument.class, id);
    }

    @Override
    public Set<Instrument> findAll(final int from, final int number) {
        Validate.isTrue(from >= 0, "from parameter should be greater or equal 0.", from);
        Validate.isTrue(number > 0, "number parameter should be greater than 0.", number);

        Criteria criteria = getSession().createCriteria(Instrument.class);
        criteria.setFirstResult(from);
        criteria.setMaxResults(number);
        criteria.addOrder(Order.desc("id"));

        return new LinkedHashSet<>(criteria.list());
    }

    @Override
    public Set<Instrument> findAllWith(final Criterion criterion) {
        Validate.notNull(criterion);

        Criteria criteria = getSession().createCriteria(Instrument.class);
        criteria.add(criterion);

        return new LinkedHashSet<>(criteria.list());
    }

    @Override
    public Set<Instrument> findAllWith(final Criterion criterion, final int from, final int number) {
        Validate.notNull(criterion);
        Validate.isTrue(from >= 0, "from parameter should be greater or equal 0.", from);
        Validate.isTrue(number > 0, "number parameter should be greater than 0.", number);

        Criteria criteria = getSession().createCriteria(Instrument.class);
        criteria.add(criterion);
        criteria.setFirstResult(from);
        criteria.setMaxResults(number);
        criteria.addOrder(Order.desc("id"));

        return new LinkedHashSet<>(criteria.list());
    }

    @Override
    public boolean delete(final Instrument entity) {
        Validate.notNull(entity);

        return super.delete(entity);
    }

    @Override
    public Long size() {
        return (Long) getSession().createCriteria(Instrument.class).setProjection(Projections.rowCount()).uniqueResult();
    }

    @Override
    public Long size(final Criterion criterion) {
        Validate.notNull(criterion);

        Criteria criteria = getSession().createCriteria(Instrument.class);
        criteria.add(criterion);
        criteria.addOrder(Order.desc("id"));

        return (Long) criteria.setProjection(Projections.rowCount()).uniqueResult();
    }

    @Override
    public boolean checkIfExists(final Criterion criterion) {
        Validate.notNull(criterion);

        Criteria criteria = getSession().createCriteria(Instrument.class);
        criteria.add(criterion);
        criteria.addOrder(Order.desc("id"));

        Long result = (Long) criteria.setProjection(Projections.rowCount()).uniqueResult();
        return result > 0;
    }

    @Override
    public void clearAll() {
        String stringQuery = "DELETE FROM Instrument";
        Query query = getSession().createQuery(stringQuery);
        query.executeUpdate();
    }

}
