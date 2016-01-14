package pl.mw.instrument.validation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.mw.instrument.context.ValidatorContext;

import javax.annotation.Resource;

import static org.junit.Assert.*;

/**
 * <p>Created by mwiesiolek on 12/01/2016.</p>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {ValidatorContext.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class ValueValidatorTest {

    @Resource(name = "validator.value")
    private Validator validator;

    @Test
    public void shouldValidateLineSuccessfullyCase1() {

        //given
        String line = "INSTRUMENT1,01-Apr-2015,2.33";

        //when
        final boolean result = validator.isValid(line);

        //then
        assertTrue(result);
    }

    @Test
    public void shouldValidateLineSuccessfullyCase2() {

        //given
        String line = "INSTRUMENT1,01-Apr-2015,222.33331";

        //when
        final boolean result = validator.isValid(line);

        //then
        assertTrue(result);
    }

    @Test
    public void shouldValidateLineSuccessfullyCase3() {

        //given
        String line = "INSTRUMENT1,01-Apr-2015,-222.33331";

        //when
        final boolean result = validator.isValid(line);

        //then
        assertTrue(result);
    }

    @Test
    public void shouldFailCase1() {

        //given
        String line = "INSTRUMENT1,01-Apr-2015,a222.33331";

        //when
        final boolean result = validator.isValid(line);

        //then
        assertFalse(result);
    }

    @Test
    public void shouldFailCase2() {

        //given
        String line = "INSTRUMENT1,01-Apr-2015,222.33331b";

        //when
        final boolean result = validator.isValid(line);

        //then
        assertFalse(result);
    }

    @Test
    public void shouldFailCase3() {

        //given
        String line = "INSTRUMENT1,01-Apr-2015,222.3c3331";

        //when
        final boolean result = validator.isValid(line);

        //then
        assertFalse(result);
    }

    @Test
    public void shouldFailCase4() {

        //given
        String line = "INSTRUMENT1,01-Apr-2015,aaaa";

        //when
        final boolean result = validator.isValid(line);

        //then
        assertFalse(result);
    }
}