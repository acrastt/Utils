package org.example.acrastt.utils;

import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The utility class for JMH.
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
     * The json file will be saved with specified directory and name.
     *
     * @param file  the name of the json file to be saved
     * @param clazz the class to be called for the benchmark
     */
    public static void runWithJson(String file, String clazz) {
        try {
            new Runner(new OptionsBuilder()
                    // Specify the class to be called for the benchmark
                    .include(clazz)
                    // Use JSON to save the benchmark results
                    .resultFormat(ResultFormatType.JSON)
                    // Specify the file of the results
                    .result(file)
                    // Build and runs the benchmark
                    .build()).run();
        } catch (RunnerException e) {
            log.error("Error when running JMH benchmark with JSON", e);
        }
    }

    /**
     * Run a JMH benchmark while saving the results in a csv file.
     * The csv file will be saved with specified directory and name.
     *
     * @param file  the name of the csv file to be saved
     * @param clazz the class to be called for the benchmark
     */
    public static void runWithCsv(String file, String clazz) {
        try {
            new Runner(new OptionsBuilder()
                    // Specify the class to be called for the benchmark
                    .include(clazz)
                    // Use CSV to save the benchmark results
                    .resultFormat(ResultFormatType.CSV)
                    // Specify the file of the results
                    .result(file)
                    // Build and runs the benchmark
                    .build()).run();
        } catch (RunnerException e) {
            log.error("Error running JMH benchmark with CSV", e);
        }
    }

    /**
     * This runs the given JMH benchmark with GC profiling(Memory measurement)
     * and saves the results in a json file.
     *
     * @param file  the json file to be saved
     * @param clazz class of the benchmark to be run
     */
    public static void runWithGCAndJson(String file, String clazz) {
        try {
            new Runner(new OptionsBuilder()
                    // Specify the class to be called for the benchmark
                    .include(clazz)
                    // Use JSON to save the benchmark results
                    .resultFormat(ResultFormatType.JSON)
                    // Specify the file of the results
                    .result(file)
                    // Adds the GC profiler
                    .addProfiler("gc")
                    // Build and runs the benchmark
                    .build()).run();
        } catch (RunnerException e) {
            log.error("Error when running JMH benchmark with GC and JSON", e);
        }
    }

    /**
     * This runs the given JMH benchmark with GC profiling(Memory measurement)
     * and saves the results in a csv file.
     *
     * @param file  the csv file to be saved
     * @param clazz class of the benchmark to be run
     */
    public static void runWithGCAndCSV(String file, String clazz) {
        try {
            new Runner(new OptionsBuilder()
                    // Specify the class to be called for the benchmark
                    .include(clazz)
                    // Use CSV to save the benchmark results
                    .resultFormat(ResultFormatType.CSV)
                    // Specify the file of the results
                    .result(file)
                    // Adds the GC profiler
                    .addProfiler("gc")
                    // Build and runs the benchmark
                    .build()).run();
        } catch (RunnerException e) {
            log.error("Error when running JMH benchmark with GC and CSV", e);
        }
    }

    /**
     * This runs the given JMH benchmark with GC profiling(Memory measurement).
     *
     * @param clazz class of the benchmark to be run
     */
    public static void runWithGC(String clazz) {
        try {
            new Runner(new OptionsBuilder()
                    // Specify the class to be called for the benchmark
                    .include(clazz)
                    // Adds the GC profiler
                    .addProfiler("gc")
                    // Build and runs the benchmark
                    .build()).run();
        } catch (RunnerException e) {
            log.error("Error when running JMH benchmark with GC", e);
        }
    }
}
