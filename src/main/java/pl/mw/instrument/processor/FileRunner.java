package pl.mw.instrument.processor;

import org.apache.commons.lang3.time.StopWatch;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.stereotype.Component;
import pl.mw.instrument.aggregator.Aggregation;
import pl.mw.instrument.validation.Validator;

import javax.annotation.Resource;
import java.io.*;

/**
 * <p>Created by mwiesiolek on 13/01/2016.</p>
 */
@Component
public class FileRunner implements Runnable {
    private static final Logger LOGGER = LogManager.getLogger();

    @Resource(name = "validator.composite")
    private Validator compositeValidator;

    @Resource(name = "aggregator.instrument1")
    private Aggregation<Double> instrument1Aggregator;

    @Resource(name = "aggregator.instrument2")
    private Aggregation<Double> instrument2Aggregator;

    @Resource(name = "aggregator.instrument3")
    private Aggregation<Double> instrument3Aggregator;

    @Override
    public void run() {

        DefaultResourceLoader resourceLoader = new DefaultResourceLoader();
        final org.springframework.core.io.Resource resource = resourceLoader.getResource("classpath:/example_input.txt");

        try (FileReader fr = new FileReader(resource.getFile()); BufferedReader br = new BufferedReader(fr)){

            StopWatch stopWatch = new StopWatch();
            stopWatch.start();

            int lineNumber = 0;
            String line;
            while((line = br.readLine()) != null){
                final boolean valid = compositeValidator.isValid(line);
                if(!valid){
                    continue;
                }

                instrument1Aggregator.proceed(line);
                instrument2Aggregator.proceed(line);
                instrument3Aggregator.proceed(line);

                LOGGER.info("Lines proceeded: {}", ++lineNumber);
            }

            stopWatch.stop();
            LOGGER.info("Time spent: {}", stopWatch);

            final Double instrument1Result = instrument1Aggregator.finish();
            final Double instrument2Result = instrument2Aggregator.finish();
            final Double instrument3Result = instrument3Aggregator.finish();

            LOGGER.info("Result from Instrument1Aggregator (average value for instrument1): {}", instrument1Result);
            LOGGER.info("Result from Instrument2Aggregator (average value for instrument2 in november 2014: {}", instrument2Result);
            LOGGER.info("Result from Instrument3Aggregator (max value for instrument3): {}", instrument3Result);

        } catch (FileNotFoundException e) {
            LOGGER.error("File not found", e);
        } catch (IOException e) {
            LOGGER.error(e);
        }
    }
}
