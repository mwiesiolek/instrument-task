package pl.mw.instrument.validation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>Launch set of rules in order to check whether or not a line from file is valid</p>
 * <p>Created by mwiesiolek on 12/01/2016.</p>
 */
@Component
public class CompositeValidator extends Validator {
    private static final Logger LOGGER = LogManager.getLogger();

    private List<Validator> list;

    @Deprecated
    public CompositeValidator() {
        //needed by spring
    }

    public CompositeValidator(final List<Validator> list) {
        this.list = list;
    }

    @Override
    public boolean isValid(final String line) {

        for (Validator validator : list) {
            boolean result = validator.isValid(line);
            if (!result) {
                LOGGER.warn("The following validation failed: {}", validator.toString());
                return false;
            }
        }

        return true;
    }

    @Override
    public String toString() {
        return "CompositeValidator";
    }
}
