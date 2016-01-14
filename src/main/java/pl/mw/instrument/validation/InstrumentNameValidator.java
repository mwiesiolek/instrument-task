package pl.mw.instrument.validation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.regex.Pattern;

/**
 * <p>Created by mwiesiolek on 12/01/2016.</p>
 */
public class InstrumentNameValidator extends Validator {
    private static final Logger LOGGER = LogManager.getLogger();

    public static final String INSTRUMENT_NAME_REGEX = "(INSTRUMENT)[0-9]+";
    private static final Pattern instrumentNamePattern = Pattern.compile(INSTRUMENT_NAME_REGEX);
    public static final int INSTRUMENT_NAME_INDEX = 0;

    @Override
    public boolean isValid(final String line) {
        if (!super.isValid(line)) {
            return false;
        }

        final String[] values = line.split(Validator.DELIMITER);
        final String instrumentName = values[INSTRUMENT_NAME_INDEX];

        if (!instrumentNamePattern.matcher(instrumentName).matches()) {
            LOGGER.warn("Improper instrument name format: {}. Proper format: INSTRUMENT[0-9]+ {} In line: {}", instrumentName, System.lineSeparator(), line);
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return "InstrumentNameValidator";
    }
}
