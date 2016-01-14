package pl.mw.instrument.context;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.mw.instrument.dao.AbstractDao;
import pl.mw.instrument.dao.InstrumentDao;
import pl.mw.instrument.entity.Instrument;

/**
 * <p>Created by mwiesiolek on 12/01/2016.</p>
 */
@Configuration
public class DaoContext {

    @Bean(name = "dao.instrument")
    public AbstractDao<Instrument> createInstrumentDao(){
        return new InstrumentDao();
    }
}
