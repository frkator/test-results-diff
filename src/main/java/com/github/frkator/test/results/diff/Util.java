package com.github.frkator.test.results.diff;

import java.lang.reflect.Method;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Util {

    public static boolean isEligible(Method method) {
        return (method.getName().startsWith("get") ||
                method.getName().startsWith("is") ||
                method.getName().startsWith("has")
         ) && !method.getName().equals("hashCode")
                && method.getParameterCount() == 0;
    }

    public static <T> String toString(Set<T> input, Function<T, String> mapper) {
        return input.stream().map(mapper).collect(Collectors.joining(System.lineSeparator()));
    }
}
