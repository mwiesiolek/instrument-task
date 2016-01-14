package pl.mw.instrument.context;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import pl.mw.instrument.processor.FileRunner;

/**
 * <p>Created by mwiesiolek on 13/01/2016.</p>
 */
@Configuration
public class ProcessorContext {
    @DependsOn(value = "db.init")
    @Bean(name = "filerunner", initMethod = "run")
    public Runnable createFileRunner() {
        return new FileRunner();
    }
}
