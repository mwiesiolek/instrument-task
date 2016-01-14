package pl.mw.instrument.aggregator;

import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;
import pl.mw.instrument.dao.AbstractDao;
import pl.mw.instrument.dao.InstrumentException;
import pl.mw.instrument.entity.Instrument;

import javax.annotation.Resource;
import java.util.Set;

/**
 * <p>Created by mwiesiolek on 13/01/2016.</p>
 */
public abstract class Aggregation<Type> {

    @Resource(name = "dao.instrument")
    private AbstractDao<Instrument> dao;


    /**
     * <p>aggregates value given in passed line.</p>
     *
     * @param line
     */
    public abstract void proceed(String line);

    /**
     * contains logic for final procedures
     */
    public abstract Type finish();

    protected double getMultiplier(final String instrumentName) {
        final SimpleExpression eq = Restrictions.eq("name", instrumentName);

        final Set<Instrument> instruments = dao.findAllWith(eq, 0, 1);
        if (instruments.size() > 1) {
            throw new InstrumentException("Should return no more than one record.");
        }

        if (!instruments.isEmpty()) {
            return instruments.iterator().next().getMultiplier();
        }

        return 1.0;
    }
}
