package pl.mw.instrument.validation;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * <p>Created by mwiesiolek on 12/01/2016.</p>
 */
@Component
public class BusinessDateValidator extends Validator {
    private static final Logger LOGGER = LogManager.getLogger();

    private static final String DATE_REGEX = "[0-9]{2}-[A-Za-z]{3}-[0-9]{4}";
    private static final Pattern datePatter = Pattern.compile(DATE_REGEX);
    public static final int DATE_INDEX = 1;
    private static final FastDateFormat DATE_FORMAT = FastDateFormat.getInstance("dd-MMM-yyyy");
    private static final int[] dayOfWeeks = new int[] {Calendar.MONDAY, Calendar.TUESDAY, Calendar.WEDNESDAY, Calendar.THURSDAY, Calendar.FRIDAY};

    @Override
    public boolean isValid(final String line) {
        if (!super.isValid(line)) {
            return false;
        }
        final String[] values = line.split(Validator.DELIMITER);
        final String date = values[DATE_INDEX];

        if (!datePatter.matcher(date).matches()) {
            LOGGER.warn("Wrong date format: {}. Proper format: dd-MMM-yyyy. Occurred in line: {}", date, line);
            return false;
        }

        try
        {
            final Date parsedDate = DATE_FORMAT.parse(date);

            final Calendar calendar = Calendar.getInstance();
            calendar.setTime(parsedDate);

            final int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
            if(!ArrayUtils.contains(dayOfWeeks, dayOfWeek)){
                LOGGER.warn("Not a business date: {}", date);
                return false;
            }

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        return true;
    }

    @Override
    public String toString() {
        return "BusinessDateValidator";
    }
}
