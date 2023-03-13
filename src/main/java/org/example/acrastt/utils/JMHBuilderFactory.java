package org.example.acrastt.utils;

import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.ChainedOptionsBuilder;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The utility class for JMH.
 *
 * @author Bohan Du
 * @version 1.2
 * @since 1.0
 */
public final class JMHBuilderFactory {

    private static final Logger LOG = LoggerFactory.getLogger(JMHBuilderFactory.class);


    private JMHBuilderFactory() {
    }

    /**
     * Run a JMH benchmark while saving the results in a json file. The json file will be saved with
     * specified directory and name.
     *
     * @param file  the name of the json file to be saved
     * @param clazz the class to be called for the benchmark
     */
    public static void runWithJson(String file, String clazz) {
        try {
            new Runner(getJSONBuilder(file, clazz)
                    // Build and runs the benchmark
                    .build()).run();
        } catch (RunnerException e) {
            LOG.error("Error when running JMH benchmark with JSON", e);
        }
    }

    /**
     * Run a JMH benchmark while saving the results in a csv file. The csv file will be saved with
     * specified directory and name.
     *
     * @param file  the name of the csv file to be saved
     * @param clazz the class to be called for the benchmark
     */
    public static void runWithCSV(String file, String clazz) {
        try {
            new Runner(getCSVBuilder(file, clazz)
                    // Build and runs the benchmark
                    .build()).run();
        } catch (RunnerException e) {
            LOG.error("Error running JMH benchmark with CSV", e);
        }
    }

    /**
     * This runs the given JMH benchmark with GC profiling(Memory measurement) and saves the results
     * in a json file.
     *
     * @param file  the json file to be saved
     * @param clazz class of the benchmark to be run
     */
    public static void runWithGCAndJson(String file, String clazz) {
        try {
            new Runner(getJSONAndGCProfiler(file, clazz)
                    // Build and runs the benchmark
                    .build()).run();
        } catch (RunnerException e) {
            LOG.error("Error when running JMH benchmark with GC and JSON", e);
        }
    }

    /**
     * This runs the given JMH benchmark with GC profiling(Memory measurement) and saves the results
     * in a csv file.
     *
     * @param file  the csv file to be saved
     * @param clazz class of the benchmark to be run
     */
    public static void runWithGCAndCSV(String file, String clazz) {
        try {
            new Runner(getCSVAndGCBuilder(file, clazz)
                    // Build and runs the benchmark
                    .build()).run();
        } catch (RunnerException e) {
            LOG.error("Error when running JMH benchmark with GC and CSV", e);
        }
    }

    /**
     * This runs the given JMH benchmark with GC profiling(Memory measurement).
     *
     * @param clazz class of the benchmark to be run
     */
    public static void runWithGC(String clazz) {
        try {
            new Runner(getBuilder(clazz)
                    // Adds the GC profiler
                    .addProfiler("gc")
                    // Build and runs the benchmark
                    .build()).run();
        } catch (RunnerException e) {
            LOG.error("Error when running JMH benchmark with GC", e);
        }
    }

    /**
     * Gets the option builder for the result in file and the class clazz
     *
     * @param file  the file of the result to be stored
     * @param clazz the class to get the option builder
     * @return the option builder
     */
    public static ChainedOptionsBuilder getBuilder(String file, String clazz) {
        return getBuilder(clazz)
                // Specify the file of the results
                .result(file);
    }

    /**
     * Return the builder for the class clazz
     *
     * @param clazz the class to get the option builder
     * @return the option builder
     */
    public static ChainedOptionsBuilder getBuilder(String clazz) {
        return new OptionsBuilder()
                // Specify the class to be called for the benchmark
                .include(clazz);
    }

    /**
     * Return the builder for the CSV result in file and the class clazz
     *
     * @param file  the file of the result in CSV to be stored
     * @param clazz the class to get the option builder
     * @return the option builder
     */
    public static ChainedOptionsBuilder getCSVBuilder(String file, String clazz) {
        return getBuilder(file, clazz)
                // Specify the result format
                .resultFormat(ResultFormatType.CSV);
    }

    /**
     * Return the builder for the JSON result in file and the class clazz
     *
     * @param file  the file of the result in JSON to be stored
     * @param clazz the class to get the option builder
     * @return the option builder
     */
    public static ChainedOptionsBuilder getJSONBuilder(String file, String clazz) {
        return getBuilder(file, clazz)
                // Specify the result format
                .resultFormat(ResultFormatType.JSON);
    }

    /**
     * Returns the option builder for the CSV result in file and the class clazz
     *
     * @param file  the file of the result in CSV to be stored
     * @param clazz the class to get the option builder
     * @return the option builder
     */
    public static ChainedOptionsBuilder getCSVAndGCBuilder(String file, String clazz) {
        return getCSVBuilder(file, clazz)
                // Specify the profiler
                .addProfiler("gc");
    }

    /**
     * Returns the option builder for the JSON result in file and the class clazz
     *
     * @param file  the file of the result in JSON to be stored
     * @param clazz the class to get the option builder
     * @return the option builder
     */
    public static ChainedOptionsBuilder getJSONAndGCProfiler(String file, String clazz) {
        return getJSONBuilder(file, clazz)
                // Specify the profiler
                .addProfiler("gc");
    }
}
