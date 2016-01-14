package pl.mw.instrument.aggregator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import pl.mw.instrument.context.AggregatorContext;
import pl.mw.instrument.context.DaoContext;
import pl.mw.instrument.context.HibernateContextTest;
import pl.mw.instrument.dao.AbstractDao;
import pl.mw.instrument.entity.Instrument;

import javax.annotation.Resource;

import static org.junit.Assert.assertEquals;

/**
 * <p>Created by mwiesiolek on 13/01/2016.</p>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {AggregatorContext.class, DaoContext.class, HibernateContextTest.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Transactional
public class Instrument1AggregatorTest {

    @Resource(name = "aggregator.instrument1")
    private Aggregation<Double> aggregator;

    @Resource(name = "dao.instrument")
    private AbstractDao<Instrument> abstractDao;

    @Test
    public void testAggregationCase1() {

        //given
        String line1 = "INSTRUMENT1,13-Jan-2016,1.5";
        String line2 = "INSTRUMENT2,13-Jan-2016,1.5";
        String line3 = "INSTRUMENT1,13-Jan-2016,1.5";

        //when
        aggregator.proceed(line1);
        aggregator.proceed(line2);
        aggregator.proceed(line3);

        final Double result = aggregator.finish();

        //then
        assertEquals(1.5, result, 0.1);
    }

    @Test
    public void testAggregationCase2() {

        //given
        String line1 = "INSTRUMENT3,13-Jan-2016,1.5";
        String line2 = "INSTRUMENT2,13-Jan-2016,1.5";
        String line3 = "INSTRUMENT4,13-Jan-2016,1.5";

        //when
        aggregator.proceed(line1);
        aggregator.proceed(line2);
        aggregator.proceed(line3);

        final Double result = aggregator.finish();

        //then
        assertEquals(0.0, result, 0.1);
    }

    @Test
    @Rollback(true)
    public void testAggregationCase3() {

        //given
        String line1 = "INSTRUMENT3,13-Jan-2016,1.5";
        String line2 = "INSTRUMENT1,13-Nov-2014,1.5";
        String line3 = "INSTRUMENT4,13-Jan-2016,1.5";

        Instrument instrument = new Instrument(null, "INSTRUMENT1", 3.0);
        abstractDao.saveOrUpdate(instrument);

        //when
        aggregator.proceed(line1);
        aggregator.proceed(line2);
        aggregator.proceed(line3);

        final Double result = aggregator.finish();

        //then
        assertEquals(4.5, result, 0.1);
    }
}