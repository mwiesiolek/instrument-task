package pl.mw.instrument.validation;

import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * <p>Preconditions:</p>
 * <ul>
 * <li>Given line cannot be null</li>
 * </ul>
 * <p>Created by mwiesiolek on 12/01/2016.</p>
 */
public abstract class Validator {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final int NUMBER_OF_VALUES = 3;
    public static final String DELIMITER = ",";

    /**
     * Checks whether or not a line from an input file is valid.
     *
     * @param line
     * @return 'true' if valid 'false' otherwise
     */
    public boolean isValid(String line) {
        Validate.notNull(line);
        return validateNumberOfValues(line);
    }

    protected boolean validateNumberOfValues(String line) {
        final String[] values = line.split(Validator.DELIMITER);
        final int length = values.length;

        if (length != NUMBER_OF_VALUES) {
            LOGGER.warn("Wrong number of values ({}, it must be exactly {}) in given line: {}", length, NUMBER_OF_VALUES, line);
            return false;
        }

        return true;
    }
}
