package org.example.acrastt.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.ChainedOptionsBuilder;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.EnumSet;
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
     *
     * @see org.apache.logging.log4j.Logger
     */
    private static final Logger LOG
            = LogManager.getLogger(JMHBuilderFactory.class);

    /**
     * Do not use this(Why this is private)
     */
    private JMHBuilderFactory() {
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
            LOG.error(String.format("Error when running benchmark '%s'", clazz), e);
        }
    }

    /**
     * This runs the given JMH benchmark
     *
     * @param clazz class of the benchmark to be run
     * @see org.example.acrastt.utils.JMHBuilderFactory.JMHConfig
     */
    public static void runJMH(String clazz) {
        runJMH(clazz, null, JMHConfig.NONE);
    }


    /**
     * Returns the {@link org.openjdk.jmh.runner.options.ChainedOptionsBuilder}
     * for the specified parameters
     *
     * @param clazz   the class of the benchmark
     * @param result  the file name of the result to be stored,
     *                if not using results, leave it null or blank
     * @param configs the configuration of the JMH benchmark
     * @return the {@link org.openjdk.jmh.runner.options.ChainedOptionsBuilder}
     * for the specified parameters
     * @see org.example.acrastt.utils.JMHBuilderFactory.JMHConfig
     */
    public static ChainedOptionsBuilder getBuilder
    (String clazz, String result, JMHConfig... configs) {
        // Creates a set based on the configuration
        EnumSet<JMHConfig> configurationList = EnumSet.copyOf(List.of(configs));
        // Initialize the builder
        ChainedOptionsBuilder builder = new OptionsBuilder().include(clazz);
        // When there aren't any configurations
        if (configurationList.contains(JMHConfig.NONE) || configurationList.isEmpty()) {
            // If there are more than one configuration
            // and have the value "none"
            if (configurationList.size() > 1) {
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
                if (configurationList.contains(JMHConfig.JSON)
                        || configurationList.contains(JMHConfig.CSV)) {
                    // Log the exception
                    LOG.error("Result type detected," +
                            " but no result file specification found");
                }
            } else {
                // Result in file
                builder.result(result);
                // When the result format is JSON
                if (configurationList.contains(JMHConfig.JSON)) {
                    // Add JSON attribute
                    builder.resultFormat(ResultFormatType.JSON);
                }
                // When the result format is CSV
                if (configurationList.contains(JMHConfig.CSV)) {
                    // Throw an error if there are multiple result formats
                    if (configurationList.contains(JMHConfig.JSON)) {
                        LOG.warn("You can only choose one result format," +
                                " using JSON as result format");
                    }
                    // Add CSV attribute
                    builder.resultFormat(ResultFormatType.CSV);
                }
            }
            // When there's GC configuration
            if (configurationList.contains(JMHConfig.GC)) {
                // Add GC attribute
                builder.addProfiler("gc");
            }
        }
        return builder;
    }

    /**
     * Returns the {@link org.openjdk.jmh.runner.options.ChainedOptionsBuilder}
     * for the specified class
     *
     * @param clazz the class of the benchmark
     * @return the {@link org.openjdk.jmh.runner.options.ChainedOptionsBuilder}
     * for the specified parameters
     * @see org.example.acrastt.utils.JMHBuilderFactory.JMHConfig
     */
    public static ChainedOptionsBuilder getBuilder(String clazz) {
        return getBuilder(clazz, null, JMHConfig.NONE);
    }

    public static Options getOptions(String clazz) {
        return getOptions(clazz, null, JMHConfig.NONE);
    }

    /**
     * Returns the Options for the specified parameters
     *
     * @param clazz   the class of the benchmark
     * @param result  the file name of the result to be stored,
     *                if not using results, leave it null or blank
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
         * Use the GC profiler
         */
        GC,
        /**
         * Use none
         */
        NONE
    }
}