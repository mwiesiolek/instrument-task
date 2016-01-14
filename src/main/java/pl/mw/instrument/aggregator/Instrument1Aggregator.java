package pl.mw.instrument.aggregator;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.mw.instrument.validation.InstrumentNameValidator;
import pl.mw.instrument.validation.Validator;
import pl.mw.instrument.validation.ValueValidator;

/**
 * <p>Average value for INSTRUMENT1</p>
 * <p>Created by mwiesiolek on 13/01/2016.</p>
 */
@Component
public class Instrument1Aggregator extends Aggregation<Double> {

    private static final String INSTRUMENT = "INSTRUMENT1";
    private double sum = 0.0;
    private long elements = 0;

    @Override
    @Transactional
    public void proceed(final String line) {

        final String[] values = line.split(Validator.DELIMITER);

        final String instrumentName = values[InstrumentNameValidator.INSTRUMENT_NAME_INDEX];
        if (!INSTRUMENT.equals(instrumentName)) {
            return;
        }

        Double value = Double.valueOf(values[ValueValidator.VALUE_INDEX]);
        double multiplier = getMultiplier(instrumentName);
        sum += (value * multiplier);

        elements++;
    }

    @Override
    public Double finish() {
        if (elements == 0) {
            return 0.0;
        }

        return sum / (double) elements;
    }
}
