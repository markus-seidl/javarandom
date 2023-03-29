package de.markusseidl.javarandom;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OperationsPerInvocation;

import java.io.IOException;
import java.util.Random;

public class MyBenchmark {

    private static final int ITERATIONS = 1_000_000;

    public static void main(String[] args) throws IOException {
        org.openjdk.jmh.Main.main(args);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OperationsPerInvocation(ITERATIONS)
    public long benchmark_java_random() {
        var rng = new Random();

        var sum = 0l;
        for(var i = 0; i < ITERATIONS; i++) {
            sum += rng.nextInt();
        }

        return sum; // dead code elimination
    }


    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OperationsPerInvocation(ITERATIONS)
    public long benchmark_simple_rng() {
        var state = 1337;

        var sum = 0l;
        for(var i = 0; i < ITERATIONS; i++) {

            var t = state;
            state ^= state << 13;
            state ^= state >> 17;
            state ^= state << 5;

            sum += t;

        }

        return sum; // dead code elimination
    }
}
