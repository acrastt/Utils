package utils;

import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The utility class for JMH(Java Micro-benchmarking Harness).
 *
 * @author Bohan Du
 * @version 1.0
 * @since 1.0
 */
public class JMHUtils {

    private static final Logger log = LoggerFactory.getLogger(JMHUtils.class);


    private JMHUtils() {
    }

    /**
     * Run a JMH benchmark while saving the results in a json file.
     * The json file will be saved in the src directory with naming
     * "JMH" + caller class name + ".json".
     */
    public static void runWithJson() {
        String[] file = new Exception().getStackTrace()[1].getClassName().split("\\.");
        runWithJson(file[file.length - 1]);
    }

    /**
     * Run a JMH benchmark while saving the results in a json file.
     * The json file will be saved with specified directory and name.
     *
     * @param file the name of the json file to be saved
     */
    public static void runWithJson(String file) {
        try {
            String caller = new Exception().getStackTrace()[1].getClassName();
            new Runner(new OptionsBuilder()
                    .include(caller)
                    .resultFormat(ResultFormatType.JSON)
                    .result(file + ".json")
                    .build()).run();
        } catch (RunnerException e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * Run a JMH benchmark while saving the results in a csv file.
     * The json file will be saved in the src directory with naming
     * "JMH" + caller class name + ".csv".
     */
    public static void runWithCsv() {
        String[] file = new Exception().getStackTrace()[1].getClassName().split("\\.");
        runWithCsv(file[file.length - 1]);
    }

    /**
     * Run a JMH benchmark while saving the results in a csv file.
     * The csv file will be saved with specified directory and name.
     *
     * @param file the name of the csv file to be saved
     */
    public static void runWithCsv(String file) {
        try {
            String caller = new Exception().getStackTrace()[1].getClassName();
            new Runner(new OptionsBuilder()
                    .include(caller)
                    .resultFormat(ResultFormatType.CSV)
                    .result(file + ".csv")
                    .build()).run();
        } catch (RunnerException e) {
            log.error(e.getMessage(), e);
        }
    }
}
