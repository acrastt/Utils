package org.example.acrastt.utils;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.concurrent.TimeUnit;

/**
 * If you see this message, it means that you are not supposed to use this benchmark.
 */
@BenchmarkMode(Mode.SingleShotTime)
@Fork(1)
@Warmup(iterations = 50)
@Measurement(iterations = 1000)
@State(Scope.Benchmark)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class Benchmark {
    /**
     * Just why...
     */
    String s = "";

    /**
     * Hey what are you doing here?
     */
    public Benchmark() {

    }

    /**
     * Stop reading this!
     */
    @Setup
    public void setup() {
        s = "";
    }

    /**
     * I said stopp reading this!
     *
     * @param bh you will be eaten by the black hole!
     */
    @org.openjdk.jmh.annotations.Benchmark
    @SuppressWarnings("all")
    public void nativeStringAppend(Blackhole bh) {
        for (int i = 0; i < 100; i++) {
            s += " ";
        }
        bh.consume(s);
    }

    /**
     * STOP!!!
     *
     * @param bh bh.consume(YOU);
     */
    @org.openjdk.jmh.annotations.Benchmark
    @SuppressWarnings("all")
    public void stringBuilder(Blackhole bh) {
        StringBuilder sb = new StringBuilder(s);
        for (int i = 0; i < 100; i++) {
            sb.append(" ");
        }
        bh.consume(sb.toString());
    }
}
