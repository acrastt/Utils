package utils;

import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JMHUtils {

    private static final Logger log = LoggerFactory.getLogger(JMHUtils.class);


    private JMHUtils() {
    }

    public static void runWithJson() {
        String[] file = new Exception().getStackTrace()[1].getClassName().split("\\.");
        runWithJson(file[file.length - 1]);
    }

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
}
