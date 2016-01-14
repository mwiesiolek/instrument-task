package pl.mw.instrument.context;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import pl.mw.instrument.db.InitializeDb;

/**
 * <p>Created by mwiesiolek on 13/01/2016.</p>
 */
@Configuration
public class DbContext {

    @DependsOn({"transactionManager", "datasource", "hibernate.sessionfactory"})
    @Bean(name = "db.init")
    public InitializeDb initializeDb() {
        return new InitializeDb();
    }
}
