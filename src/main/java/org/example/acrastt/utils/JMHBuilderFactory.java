package org.example.acrastt.utils;

import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.ChainedOptionsBuilder;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.List;

/**
 * The utility class for JMH.
 *
 * @author Bohan Du
 * @version 1.0.0
 * @since 1.0.0
 */
public final class JMHBuilderFactory {

    private static final Logger LOG = LoggerFactory.getLogger(JMHBuilderFactory.class);


    private JMHBuilderFactory() {
    }

    /**
     * This runs the given JMH benchmark and saves the results in a JSON file.
     *
     * @param clazz  the class to be called for the benchmark
     * @param result the name of the JSON file to be saved
     */
    public static void runWithJson(String clazz, String result) {
        try {
            // Runs the benchmark
            new Runner(getOptions(clazz, result, JMHConfig.JSON)).run();
        } catch (
                RunnerException e) {
            throw new JMHRuntimeException(e);
        }
    }

    /**
     * This runs the given JMH benchmark and saves the results in a CSV file.
     *
     * @param clazz  class of the benchmark to be run
     * @param result the name of the CSV file to be saved
     */
    public static void runWithCSV(String clazz, String result) {
        try {
            // Runs the benchmark
            new Runner(getOptions(clazz, result, JMHConfig.CSV)).run();
        } catch (
                RunnerException e) {
            throw new JMHRuntimeException(e);
        }
    }

    /**
     * This runs the given JMH benchmark with GC profiling and saves the results in a JSON file.
     *
     * @param clazz  class of the benchmark to be run
     * @param result the file name of the JSON result to be stored
     */
    public static void runWithGCAndJson(String clazz, String result) {
        try {
            // Runs the benchmark
            new Runner(getOptions(clazz, result, JMHConfig.GC, JMHConfig.JSON)).run();
        } catch (
                RunnerException e) {
            throw new JMHRuntimeException(e);
        }
    }

    /**
     * This runs the given JMH benchmark with GC profiling and saves the results in a CSV file.
     *
     * @param clazz  class of the benchmark to be run
     * @param result the file name of the CSV result to be stored
     */
    public static void runWithGCAndCSV(String clazz, String result) {
        try {
            // Runs the benchmark
            new Runner(getOptions(clazz, result, JMHConfig.GC, JMHConfig.CSV)).run();
        } catch (
                RunnerException e) {
            throw new JMHRuntimeException(e);
        }
    }

    /**
     * This runs the given JMH benchmark with GC profiling.
     *
     * @param clazz class of the benchmark to be run
     */
    public static void runWithGC(String clazz) {
        try {
            // Runs the benchmark
            new Runner(getOptions(clazz, "", JMHConfig.GC)).run();
        } catch (
                RunnerException e) {
            throw new JMHRuntimeException(e);
        }
    }

    /**
     * This runs the given JMH benchmark
     *
     * @param clazz   class of the benchmark to be run
     * @param result  the file name of the result to be stored, if not using results, leave it blank("")
     * @param configs Configurations of the JMH benchmark
     * @see org.example.acrastt.utils.JMHBuilderFactory.JMHConfig
     */
    public static void runJMH(String clazz, String result, JMHConfig... configs) {
        try {
            // Runs the benchmark
            new Runner(getBuilder(clazz, result, configs).build()).run();
        } catch (
                RunnerException e) {
            throw new JMHRuntimeException(e);
        }
    }


    /**
     * Returns the ChainedOptionsBuilder for the specified parameters
     *
     * @param clazz   the class of the benchmark
     * @param result  the file name of the result to be stored, if not using results, leave it blank("")
     * @param configs the configuration of the JMH benchmark
     * @return the {@link org.openjdk.jmh.runner.options.ChainedOptionsBuilder} for the specified parameters
     * @see org.example.acrastt.utils.JMHBuilderFactory.JMHConfig
     */
    public static ChainedOptionsBuilder getBuilder(String clazz, String result, JMHConfig... configs) {
        // Creates a set based on the configuration
        HashSet<JMHConfig> configsList = new HashSet<>(List.of(configs));
        // Initialize the builder
        ChainedOptionsBuilder builder = new OptionsBuilder().include(clazz);
        // When there are configurations
        if (!(configsList.contains(JMHConfig.NONE) || configsList.isEmpty())) {
            // When there is a result file specification
            if (!result.equals("")) {
                // Result in file
                builder.result(result);
                // When the result format is JSON
                if (configsList.contains(JMHConfig.JSON)) {
                    // Add JSON attribute
                    builder.resultFormat(ResultFormatType.JSON);
                }
                // When the result format is CSV
                if (configsList.contains(JMHConfig.CSV)) {
                    // Throw an error if there are multiple result formats
                    if (configsList.contains(JMHConfig.JSON)) {
                        throw new IllegalArgumentException("You can only choose one result format(JSON or CSV)");
                    }
                    // Add CSV attribute
                    builder.resultFormat(ResultFormatType.CSV);
                }
            } else {
                // When there is no result file specified and there are result configuration
                if (configsList.contains(JMHConfig.JSON) || configsList.contains(JMHConfig.CSV)) {
                    // Throw the exception
                    throw new IllegalArgumentException("Please specify result file");
                }
            }
            // When there's GC configuration
            if (configsList.contains(JMHConfig.GC)) {
                // Add GC attribute
                builder.addProfiler("gc");
            }
        } else {
            // If there are more than one configuration and have the value "none"
            if (configsList.size() > 1) {
                // Log the exception
                // Should it throw IllegalArgumentException or just log?
                LOG.error("Specified configuration 'none', but other configurations found.",
                        new IllegalArgumentException("Specified configuration 'none', but other configurations found."));
            }
        }
        return builder;
    }


    /**
     * Returns the Options for the specified parameters
     *
     * @param clazz   the class of the benchmark
     * @param result  the file name of the result to be stored, if not using results, leave it blank("")
     * @param configs the configuration of the JMH benchmark
     * @return the {@link org.openjdk.jmh.runner.options.Options} for the specified parameters
     * @see org.example.acrastt.utils.JMHBuilderFactory.JMHConfig
     */
    public static Options getOptions(String clazz, String result, JMHConfig... configs) {
        return getBuilder(clazz, result, configs).build();
    }

    /**
     * Class of JMH configurations
     */
    public enum JMHConfig {
        /**
         * Use the JSON result format
         */
        JSON,
        /**
         * Use the CSV result format
         */
        CSV,
        /**
         * Use the gc profiler
         */
        GC,
        /**
         * Use none
         */
        NONE
    }

    private static class JMHRuntimeException extends RuntimeException {

        public JMHRuntimeException() {
            super();
        }

        public JMHRuntimeException(String message) {
            super(message);
        }

        public JMHRuntimeException(String message, Throwable cause) {
            super(message, cause);
        }

        public JMHRuntimeException(Throwable cause) {
            super(cause);
        }

        protected JMHRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
            super(message, cause, enableSuppression, writableStackTrace);
        }
    }
}