package org.example.acrastt.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.openjdk.jmh.infra.Blackhole;
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
public final class JMHUtils {

    /**
     * Logger used to print information
     *
     * @see org.apache.logging.log4j.Logger
     */
    private static final Logger LOG
            = LogManager.getLogger(JMHUtils.class);

    /**
     * Blackhole used for Blackhole-related methods
     */
    private static Blackhole bh;
    private static final Object obj = new Object();

    /**
     * Should not be called.
     */
    private JMHUtils() {
        throw new IllegalAccessError();
    }

    /**
     * Gets the singleton Blackhole instance
     *
     * @return the Blackhole
     */
    public static Blackhole getBlackhole() {
        // Lazy initialization
        synchronized (obj) {
            if (bh == null) {
                bh = new Blackhole("Today's password is swordfish. I understand" +
                        " instantiating Blackholes directly is dangerous.");
            }
        }
        // Return the Blackhole instance
        return bh;
    }

    /**
     * Evaporates the given Blackhole
     *
     * @param bhToEvaporate the Blackhole to evaporate
     */
    public static void evaporate(@NotNull Blackhole bhToEvaporate) {
        // Evaporates the specified Blackhole
        bhToEvaporate.evaporate("Yes, I am Stephen Hawking," +
                " and know a thing or two about black holes.");
    }

    /**
     * Evaporates the singleton Blackhole instance
     */
    public static void evaporate() {
        synchronized (obj) {
            if (bh != null) {
                // Evaporates the singleton Blackhole
                bh.evaporate("Yes, I am Stephen Hawking," +
                        " and know a thing or two about black holes.");
            }
        }
    }

    /**
     * This runs the given JMH benchmark
     *
     * @param clazz   class of the benchmark to be run
     * @param configs Configurations of the JMH benchmark
     * @see JMHUtils.JMHConfig
     */
    public static void runJMH
    (String clazz, JMHConfig... configs) {
        runJMH(clazz, null, configs);
    }


    /**
     * This runs the given JMH benchmark
     *
     * @param clazz   class of the benchmark to be run
     * @param result  the file name of the result to be stored,
     *                if not using results, leave it blank("")
     * @param configs Configurations of the JMH benchmark
     * @see JMHUtils.JMHConfig
     */
    public static void runJMH
    (String clazz, String result, JMHConfig... configs) {
        try {
            // Runs the benchmark
            new Runner(getOptions(clazz, result, configs)).run();
        } catch (
                RunnerException e) {
            LOG.error(String.format("Error when running benchmark '%s'", clazz), e);
        }
    }

    /**
     * Returns the {@link org.openjdk.jmh.runner.options.ChainedOptionsBuilder}
     * for the specified parameters
     *
     * @param clazz   the class of the benchmark
     * @param configs the configuration of the JMH benchmark
     * @return the {@link org.openjdk.jmh.runner.options.ChainedOptionsBuilder}
     * for the specified parameters
     * @see JMHUtils.JMHConfig
     */
    public static ChainedOptionsBuilder getBuilder
    (String clazz, JMHConfig... configs) {
        return getBuilder(clazz, null, configs);
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
     * @see JMHUtils.JMHConfig
     */
    @Contract("null, _, _ -> fail")
    public static @Nullable ChainedOptionsBuilder getBuilder
    (String clazz, String result, JMHConfig... configs) {
        if (clazz == null || clazz.isBlank()) {
            throw new IllegalArgumentException(String.format("Invalid argument clazz: '%s'", clazz));
        }
        // Creates a set based on the configuration
        EnumSet<JMHConfig> configurationList = EnumSet.copyOf(List.of(configs));
        // Initialize the builder
        ChainedOptionsBuilder builder = new OptionsBuilder().include(clazz);
        // When there aren't any configurations
        if (configurationList.contains(JMHConfig.NONE)
                || configurationList.isEmpty()) {
            // If there are more than one configuration
            // and have the value "none"
            if (!configurationList.isEmpty()) {
                // Log the exception
                LOG.warn("Using 'none' as first priority: ",
                        new IllegalArgumentException("Specified " +
                                "configuration 'none'," +
                                " but other configurations found."));
            }
        } else {
            // When there isn't a result file specification
            if (result == null || result.isBlank()) {
                // When there is no result file specified
                // and there are result configuration
                if (configurationList.contains(JMHConfig.JSON)
                        || configurationList.contains(JMHConfig.CSV)) {
                    // Log the exception
                    LOG.error("Result type detected," +
                            " but no result file specification found");
                    return null;
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
                        LOG.error("You can only choose one result format");
                        return null;
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
     * Returns the {@link org.openjdk.jmh.runner.options.Options}
     * for the specified parameters
     *
     * @param clazz   the class of the benchmark
     * @param configs the configuration of the JMH benchmark
     * @return the {@link org.openjdk.jmh.runner.options.Options}
     * for the specified parameters
     * @see JMHUtils.JMHConfig
     */
    public static Options getOptions
    (String clazz, JMHConfig... configs) {
        return getOptions(clazz, null, configs);
    }

    /**
     * Returns the {@link org.openjdk.jmh.runner.options.Options}
     * for the specified parameters
     *
     * @param clazz   the class of the benchmark
     * @param result  the file name of the result to be stored,
     *                if not using results, leave it null or blank
     * @param configs the configuration of the JMH benchmark
     * @return the {@link org.openjdk.jmh.runner.options.Options}
     * for the specified parameters
     * @see JMHUtils.JMHConfig
     */
    public static @Nullable Options getOptions
    (String clazz, String result, JMHConfig... configs) {
        ChainedOptionsBuilder builder = getBuilder(clazz, result, configs);
        if (builder == null) {
            LOG.error("Exception while getting options");
            return null;
        }
        return builder.build();
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