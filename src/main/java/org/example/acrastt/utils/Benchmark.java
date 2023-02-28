package org.example.acrastt.utils;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.SingleShotTime)
@Fork(1)
@Warmup(iterations = 50)
@Measurement(iterations = 1000)
@State(Scope.Benchmark)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class Benchmark {
    String s = "";

    @Setup
    public void setup() {
        s = "";
    }

    @org.openjdk.jmh.annotations.Benchmark
    @SuppressWarnings("all")
    public void nativeStringAppend(Blackhole bh) {
        for (int i = 0; i < 100; i++) {
            s += " ";
        }
        bh.consume(s);
    }

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
