package pl.mw.instrument.aggregator;

import org.apache.commons.lang3.time.FastDateFormat;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.mw.instrument.validation.BusinessDateValidator;
import pl.mw.instrument.validation.InstrumentNameValidator;
import pl.mw.instrument.validation.Validator;
import pl.mw.instrument.validation.ValueValidator;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

/**
 * <p>Average value for INSTRUMENT2 and November 2014</p>
 * <p>Created by mwiesiolek on 13/01/2016.</p>
 */
@Component
public class Instrument2Aggregator extends Aggregation<Double> {

    private static final FastDateFormat DATE_FORMAT = FastDateFormat.getInstance("dd-MMM-yyyy");
    private static final String INSTRUMENT = "INSTRUMENT2";
    private static final int PROPER_YEAR = 2014;
    private static final int PROPER_MONTH = Calendar.NOVEMBER;
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

        try {
            Date date = DATE_FORMAT.parse(values[BusinessDateValidator.DATE_INDEX]);

            final Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);

            final int year = calendar.get(Calendar.YEAR);
            final int month = calendar.get(Calendar.MONTH);

            if (year != PROPER_YEAR || month != PROPER_MONTH) {
                return;
            }

            Double value = Double.valueOf(values[ValueValidator.VALUE_INDEX]);
            double multiplier = getMultiplier(instrumentName);
            sum += (value * multiplier);

            elements++;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Double finish() {
        if(elements == 0){
            return 0.0;
        }

        return sum / (double) elements;
    }
}
