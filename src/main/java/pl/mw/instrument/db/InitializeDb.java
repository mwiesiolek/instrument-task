package pl.mw.instrument.db;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.mw.instrument.dao.InstrumentDao;
import pl.mw.instrument.entity.Instrument;

import javax.annotation.Resource;

/**
 * <p>Created by mwiesiolek on 13/01/2016.</p>
 */
@Service
@Transactional
public class InitializeDb {
    private static final Logger LOGGER = LogManager.getLogger();

    @Resource(name = "dao.instrument")
    private InstrumentDao dao;

    public void init(){
        dao.clearAll();

        Instrument instrument1 = new Instrument(null, "INSTRUMENT1", 1.5);
        Instrument instrument2 = new Instrument(null, "INSTRUMENT2", 3.0);
        Instrument instrument3 = new Instrument(null, "INSTRUMENT3", 3.5);

        dao.saveOrUpdate(instrument1);
        if(instrument1.isNotPersistedInDb()){
            LOGGER.error("An error occurred while persisting given instrument: {} in db.", instrument1);
        }

        dao.saveOrUpdate(instrument2);
        if(instrument2.isNotPersistedInDb()){
            LOGGER.error("An error occurred while persisting given instrument: {} in db.", instrument2);
        }

        dao.saveOrUpdate(instrument3);
        if(instrument3.isNotPersistedInDb()){
            LOGGER.error("An error occurred while persisting given instrument: {} in db.", instrument3);
        }
    }
}
