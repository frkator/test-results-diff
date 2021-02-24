package com.github.frkator.test.results.diff;

import java.util.Collection;

public class Preconditions {

    public static <F, S> void checkSizes(Collection<F> first, Collection<S> second, String errorMsg) {
        checkState(first.size() == second.size(), errorMsg);
    }

    public static void checkState(boolean b, String errorMsg) {
        if (!b) {
            throw new IllegalStateException(errorMsg);
        }
    }

}
