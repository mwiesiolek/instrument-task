package pl.mw.instrument.validation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.mw.instrument.context.ValidatorContext;

import javax.annotation.Resource;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * <p>Created by mwiesiolek on 12/01/2016.</p>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {ValidatorContext.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class BusinessDateValidatorTest {

    @Resource(name = "validator.date")
    private Validator validator;

    @Test
    public void shouldValidateLineSuccessfully() {

        //given
        String line = "INSTRUMENT1,01-Apr-2015,2.33";

        //when
        final boolean result = validator.isValid(line);

        //then
        assertTrue(result);
    }

    @Test
    public void validationShouldFail() {

        //given
        String line = "INSTRUMENT1,01-01-2015,2.33";

        //when
        final boolean result = validator.isValid(line);

        //then
        assertFalse(result);
    }

    @Test
    public void validationShouldFailDueToAbsentDate() {

        //given
        String line = "INSTRUMENT1,,2.33";

        //when
        final boolean result = validator.isValid(line);

        //then
        assertFalse(result);
    }

    @Test
    public void validationShouldFailDueToAbsentDateSecondCase() {

        //given
        String line = "INSTRUMENT1,2.33";

        //when
        final boolean result = validator.isValid(line);

        //then
        assertFalse(result);
    }

    @Test
    public void validBusinessDate(){

        //given
        String line = "INSTRUMENT1,13-Jan-2016,1.0";

        //when
        final boolean valid = validator.isValid(line);

        //then
        assertTrue(valid);
    }

    @Test
    public void invalidBusinessDate(){

        //given
        String line = "INSTRUMENT1,16-Jan-2016,1.0";

        //when
        final boolean valid = validator.isValid(line);

        //then
        assertFalse(valid);
    }
}