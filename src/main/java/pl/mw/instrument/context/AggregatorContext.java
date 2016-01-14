package pl.mw.instrument.context;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.mw.instrument.aggregator.Aggregation;
import pl.mw.instrument.aggregator.Instrument1Aggregator;
import pl.mw.instrument.aggregator.Instrument2Aggregator;
import pl.mw.instrument.aggregator.Instrument3Aggregator;

/**
 * <p>Created by mwiesiolek on 13/01/2016.</p>
 */
@Configuration
public class AggregatorContext {

    @Bean(name = "aggregator.instrument1")
    public Aggregation<Double> createInstrument1Aggregator() {
        return new Instrument1Aggregator();
    }

    @Bean(name = "aggregator.instrument2")
    public Aggregation<Double> createInstrument2Aggregator() {
        return new Instrument2Aggregator();
    }

    @Bean(name = "aggregator.instrument3")
    public Aggregation<Double> createInstrument3Aggregator() {
        return new Instrument3Aggregator();
    }
}
