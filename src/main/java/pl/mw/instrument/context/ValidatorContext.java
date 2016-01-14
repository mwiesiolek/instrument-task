package pl.mw.instrument.context;

import com.google.common.collect.Lists;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.mw.instrument.validation.BusinessDateValidator;
import pl.mw.instrument.validation.CompositeValidator;
import pl.mw.instrument.validation.InstrumentNameValidator;
import pl.mw.instrument.validation.ValueValidator;
import pl.mw.instrument.validation.Validator;

import java.util.List;

/**
 * <p>Created by mwiesiolek on 12/01/2016.</p>
 */
@Configuration
public class ValidatorContext {

    @Bean(name = "validator.composite")
    public Validator createCompositeValidator() {
        List<Validator> validators = Lists.newArrayList(createInstrumentNameValidator(), createBusinessDateValidator(), createValueValidator());
        return new CompositeValidator(validators);
    }

    @Bean(name = "validator.instrumentname")
    public Validator createInstrumentNameValidator() {
        return new InstrumentNameValidator();
    }

    @Bean(name = "validator.date")
    public Validator createBusinessDateValidator() {
        return new BusinessDateValidator();
    }

    @Bean(name = "validator.value")
    public Validator createValueValidator() {
        return new ValueValidator();
    }
}
