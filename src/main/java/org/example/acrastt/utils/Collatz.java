package org.example.acrastt.utils;

import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Collatz {
    private Collatz() {
    }

    private static final HashMap<BigInteger, Boolean> memory = new HashMap<>();

    public static boolean checkCollatz(@NotNull BigInteger i) {
        if (i.compareTo(BigInteger.ZERO) <= 0) {
            return false;
        }
        Set<BigInteger> used = new HashSet<>();
        while (!i.equals(BigInteger.ONE)) {
            if (used.contains(i)) {
                memory.put(i, false);
                return false;
            }
            if (Boolean.TRUE.equals(memory.get(i))) {
                return true;
            }
            if (Boolean.FALSE.equals(memory.get(i))) {
                return false;
            }
            used.add(i);
            if (i.mod(BigInteger.TWO).equals(BigInteger.ZERO)) {
                i = i.divide(BigInteger.TWO);
            } else {
                i = i.multiply(BigInteger.valueOf(3)).add(BigInteger.ONE);
            }
        }
        memory.put(i, true);
        return true;
    }

    public static boolean checkCollatz(long i) {
        return checkCollatz(BigInteger.valueOf(i));
    }
}
