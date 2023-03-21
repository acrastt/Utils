package org.example.acrastt.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.ChainedOptionsBuilder;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

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

    /**
     * Logger used to print information
     */
    private static final Logger LOG
            = LogManager.getLogger(JMHBuilderFactory.class);

    private static final String ERROR_WHEN_RUNNING_BENCHMARK
            = "Error when running benchmark ";

    /**
     * Do not use this(Why this is private)
     */
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
            new Runner(getOptions(
                    clazz, result, JMHConfig.JSON)).run();
        } catch (
                RunnerException e) {
            LOG.error(ERROR_WHEN_RUNNING_BENCHMARK + clazz, e);
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
            new Runner(getOptions(
                    clazz, result, JMHConfig.CSV)).run();
        } catch (
                RunnerException e) {
            LOG.error(ERROR_WHEN_RUNNING_BENCHMARK + clazz, e);
        }
    }

    /**
     * This runs the given JMH benchmark with GC profiling
     * and saves the results in a JSON file.
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
            LOG.error(ERROR_WHEN_RUNNING_BENCHMARK + clazz, e);
        }
    }

    /**
     * This runs the given JMH benchmark with GC profiling
     * and saves the results in a CSV file.
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
            LOG.error(ERROR_WHEN_RUNNING_BENCHMARK + clazz, e);
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
            LOG.error(ERROR_WHEN_RUNNING_BENCHMARK + clazz, e);
        }
    }

    /**
     * This runs the given JMH benchmark
     *
     * @param clazz   class of the benchmark to be run
     * @param result  the file name of the result to be stored,
     *                if not using results, leave it blank("")
     * @param configs Configurations of the JMH benchmark
     * @see org.example.acrastt.utils.JMHBuilderFactory.JMHConfig
     */
    public static void runJMH
    (String clazz, String result, JMHConfig... configs) {
        try {
            // Runs the benchmark
            new Runner(getBuilder(clazz, result, configs).build()).run();
        } catch (
                RunnerException e) {
            LOG.error(ERROR_WHEN_RUNNING_BENCHMARK + clazz, e);
        }
    }


    /**
     * Returns the ChainedOptionsBuilder for the specified parameters
     *
     * @param clazz   the class of the benchmark
     * @param result  the file name of the result to be stored,
     *                if not using results, leave it null
     * @param configs the configuration of the JMH benchmark
     * @return the {@link org.openjdk.jmh.runner.options.ChainedOptionsBuilder}
     * for the specified parameters
     * @see org.example.acrastt.utils.JMHBuilderFactory.JMHConfig
     */
    public static ChainedOptionsBuilder getBuilder
    (String clazz, String result, JMHConfig... configs) {
        // Creates a set based on the configuration
        HashSet<JMHConfig> configsList = new HashSet<>(List.of(configs));
        // Initialize the builder
        ChainedOptionsBuilder builder = new OptionsBuilder().include(clazz);
        // When there aren't any configurations
        if (configsList.contains(JMHConfig.NONE) || configsList.isEmpty()) {
            // If there are more than one configuration
            // and have the value "none"
            if (configsList.size() > 1) {
                // Log the exception
                LOG.warn("Using 'none' as first priority: \n",
                        new IllegalArgumentException("Specified " +
                                "configuration 'none'," +
                                " but other configurations found."));
            }
        } else {
            // When there isn't a result file specification
            if (result == null || result.trim().equals("")) {
                // When there is no result file specified
                // and there are result configuration
                if (configsList.contains(JMHConfig.JSON)
                        || configsList.contains(JMHConfig.CSV)) {
                    // Log the exception
                    LOG.error("Result type detected," +
                            " but no result file specification found");
                }
            } else {
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
                        LOG.warn("You can only choose one result format," +
                                " using JSON as result format");
                    }
                    // Add CSV attribute
                    builder.resultFormat(ResultFormatType.CSV);
                }
            }
            // When there's GC configuration
            if (configsList.contains(JMHConfig.GC)) {
                // Add GC attribute
                builder.addProfiler("gc");
            }
        }
        return builder;
    }


    /**
     * Returns the Options for the specified parameters
     *
     * @param clazz   the class of the benchmark
     * @param result  the file name of the result to be stored,
     *                if not using results, leave it blank("")
     * @param configs the configuration of the JMH benchmark
     * @return the {@link org.openjdk.jmh.runner.options.Options}
     * for the specified parameters
     * @see org.example.acrastt.utils.JMHBuilderFactory.JMHConfig
     */
    public static Options getOptions
    (String clazz, String result, JMHConfig... configs) {
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
}