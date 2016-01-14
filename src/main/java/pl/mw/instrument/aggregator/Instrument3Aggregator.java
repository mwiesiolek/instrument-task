package pl.mw.instrument.aggregator;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.mw.instrument.validation.InstrumentNameValidator;
import pl.mw.instrument.validation.Validator;
import pl.mw.instrument.validation.ValueValidator;

/**
 * <p>Find maximum value in INSTRUMENT3</p>
 * <p>Created by mwiesiolek on 13/01/2016.</p>
 */
@Component
public class Instrument3Aggregator extends Aggregation<Double> {

    private static final String INSTRUMENT = "INSTRUMENT3";

    private double max = 0;

    @Override
    public void proceed(final String line) {
        final String[] values = line.split(Validator.DELIMITER);

        if(!INSTRUMENT.equals(values[InstrumentNameValidator.INSTRUMENT_NAME_INDEX])){
            return;
        }

        Double value = Double.valueOf(values[ValueValidator.VALUE_INDEX]);
        if(value > max){
            max = value;
        }
    }

    @Override
    public Double finish() {
        return max;
    }
}
