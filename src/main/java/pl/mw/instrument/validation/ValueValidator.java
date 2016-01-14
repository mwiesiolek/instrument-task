package pl.mw.instrument.validation;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * <p>Created by mwiesiolek on 12/01/2016.</p>
 */
public class ValueValidator extends Validator {
    private static final Logger LOGGER = LogManager.getLogger();
    public static final int VALUE_INDEX = 2;

    @Override
    public boolean isValid(final String line) {
        if (!super.isValid(line)) {
            return false;
        }

        final String[] values = line.split(Validator.DELIMITER);
        final String value = values[VALUE_INDEX];

        if (!NumberUtils.isNumber(value)) {
            LOGGER.warn("Not a number: {}. In line: {}", value, line);
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return "ValueValidator";
    }
}
