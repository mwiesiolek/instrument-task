package pl.mw.instrument.dao;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import pl.mw.instrument.context.DaoContext;
import pl.mw.instrument.context.HibernateContextTest;
import pl.mw.instrument.entity.Instrument;

import javax.annotation.Resource;

import java.util.Set;

import static org.junit.Assert.*;

/**
 * <p>Created by mwiesiolek on 12/01/2016.</p>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {DaoContext.class, HibernateContextTest.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class InstrumentDaoTest {

    @Resource(name = "dao.instrument")
    private AbstractDao<Instrument> dao;

    @Resource
    private SessionFactory sessionFactory;

    @Test
    @Transactional
    @Rollback(true)
    public void testInsert() {

        //given
        Instrument instrument = new Instrument(null, "name", 2.0);

        //when
        dao.saveOrUpdate(instrument);

        //then
        assertTrue(instrument.getId() > 0);
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testUpdate(){

        //given
        Instrument instrument = new Instrument(null, "name", 2.0);

        dao.saveOrUpdate(instrument);

        instrument.setName("changed-name");
        instrument.setMultiplier(3.0);

        //when
        dao.saveOrUpdate(instrument);

        //then
        final Instrument fromDB = dao.find(instrument.getId());

        assertNotNull(fromDB);
        assertEquals(instrument, fromDB);
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testFindMethod(){

        //given
        Instrument instrument = new Instrument(null, "name", 2.0);

        dao.saveOrUpdate(instrument);

        //when
        final Instrument fromDB = dao.find(instrument.getId());

        //then
        assertNotNull(fromDB);
        assertEquals(instrument, fromDB);
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testFindAllMethod(){

        //given
        Instrument instrument1 = new Instrument(null, "name1", 1.0);
        Instrument instrument2 = new Instrument(null, "name2", 2.0);

        dao.saveOrUpdate(instrument1);
        dao.saveOrUpdate(instrument2);

        //when
        final Set<Instrument> instruments1 = dao.findAll(0, 1);
        final Set<Instrument> instruments2 = dao.findAll(1, 1);

        //then
        assertNotNull(instruments1);
        assertEquals(1, instruments1.size());
        assertEquals(instrument2, instruments1.iterator().next());

        assertNotNull(instruments2);
        assertEquals(1, instruments2.size());
        assertEquals(instrument1, instruments2.iterator().next());
    }

    @Test
    @Transactional
    @Rollback(true)
    public void shouldNotFindAnyInstruments(){

        //given
        final SimpleExpression eq = Restrictions.eq("name", "absent");

        Instrument instrument1 = new Instrument(null, "name1", 1.0);
        Instrument instrument2 = new Instrument(null, "name2", 2.0);

        dao.saveOrUpdate(instrument1);
        dao.saveOrUpdate(instrument2);

        //when
        final Set<Instrument> instruments = dao.findAllWith(eq);

        //then
        assertNotNull(instruments);
        assertTrue(instruments.isEmpty());
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testFindAllWithMethod(){

        //given
        final String nameValue = "name1";
        final SimpleExpression eq = Restrictions.eq("name", nameValue);

        Instrument instrument = new Instrument(null, nameValue, 1.0);

        dao.saveOrUpdate(instrument);

        //when
        final Set<Instrument> instruments = dao.findAllWith(eq);

        //then
        assertNotNull(instruments);
        assertEquals(1, instruments.size());
        assertEquals(instrument, instruments.iterator().next());
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testFindAllWithLimitMethod(){

        //given
        final SimpleExpression eq = Restrictions.eq("name", "name1");

        Instrument instrument1 = new Instrument(null, "name1", 1.0);
        Instrument instrument2 = new Instrument(null, "name2", 2.0);

        dao.saveOrUpdate(instrument1);
        dao.saveOrUpdate(instrument2);

        //when
        final Set<Instrument> instruments = dao.findAllWith(eq, 0, 1);

        //then
        assertNotNull(instruments);
        assertEquals(1, instruments.size());
        assertEquals(instrument1, instruments.iterator().next());
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testDeleteMethod(){

        //given
        Instrument instrument = new Instrument(null, "name", 2.0);

        dao.saveOrUpdate(instrument);

        //when
        final boolean result = dao.delete(instrument);
        sessionFactory.getCurrentSession().flush();

        //then
        final Instrument fromDB = dao.find(instrument.getId());

        assertTrue(result);
        assertNull(fromDB);
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testSize() {

        //given
        Instrument instrument = new Instrument(null, "name", 2.0);

        dao.saveOrUpdate(instrument);

        //when
        final Long size = dao.size();

        //then
        assertEquals(Long.valueOf(1), size);
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testSizeWith() {

        //given
        final SimpleExpression eq = Restrictions.eq("name", "name");

        Instrument instrument = new Instrument(null, "name", 2.0);

        dao.saveOrUpdate(instrument);

        //when
        final Long size = dao.size(eq);

        //then
        assertEquals(Long.valueOf(1), size);
    }

    @Test
    @Transactional
    @Rollback(true)
    public void shouldReturn0() {

        //given
        final SimpleExpression eq = Restrictions.eq("name", "absent");

        Instrument instrument = new Instrument(null, "name", 2.0);

        dao.saveOrUpdate(instrument);

        //when
        final Long size = dao.size(eq);

        //then
        assertEquals(Long.valueOf(0), size);
    }

    @Test
    @Transactional
    @Rollback(true)
    public void checkIfExistsMethodShouldReturnTrue(){

        //given
        final SimpleExpression eq = Restrictions.eq("name", "name");

        Instrument instrument = new Instrument(null, "name", 2.0);

        dao.saveOrUpdate(instrument);

        //when
        final boolean result = dao.checkIfExists(eq);

        //then
        assertTrue(result);
    }

    @Test
    @Transactional
    @Rollback(true)
    public void checkIfExistsMethodShouldReturnFalse(){

        //given
        final SimpleExpression eq = Restrictions.eq("name", "absent");

        Instrument instrument = new Instrument(null, "name", 2.0);

        dao.saveOrUpdate(instrument);

        //when
        final boolean result = dao.checkIfExists(eq);

        //then
        assertFalse(result);
    }
}
