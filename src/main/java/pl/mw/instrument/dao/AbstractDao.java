package pl.mw.instrument.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import pl.mw.instrument.entity.Model;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Set;

/**
 * Created by mwiesiolek on 01/05/2015.
 */
public abstract class AbstractDao<Type extends Model> {

    @Resource
    private SessionFactory sessionFactory;

    public abstract Type find(Serializable id);

    public abstract Set<Type> findAll(int from, int number);

    public abstract Set<Type> findAllWith(Criterion criterion);

    public abstract Set<Type> findAllWith(Criterion criterion, int from, int number);

    public abstract Long size();

    public abstract Long size(Criterion criterion);

    public abstract boolean checkIfExists(Criterion criterion);

    public abstract void clearAll();

    protected Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    public void saveOrUpdate(Type entity) {
        getSession().saveOrUpdate(entity);
    }

    protected boolean delete(Type entity) {
        getSession().delete(entity);
        return true;
    }
}
