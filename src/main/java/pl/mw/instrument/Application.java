package pl.mw.instrument;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import pl.mw.instrument.db.InitializeDb;

/**
 * <p>Created by mwiesiolek on 24.04.15.</p>
 */
@SpringBootApplication
public class Application {
    public static void main(String[] args) throws Exception {
        final ConfigurableApplicationContext ctx = SpringApplication.run(Application.class, args);

        final InitializeDb bean = ctx.getBean("db.init", InitializeDb.class);
        bean.init();
    }
}
