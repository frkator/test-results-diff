package com.github.frkator.test.results.diff;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Arguments {

    private Path leftPath;
    private Path rightPath;

    public Arguments(String[] args) {
        if (args.length != 2) {
            throw new IllegalArgumentException("both arguments must be paths to an existing folder");
        }
        leftPath = Paths.get(args[0]).toAbsolutePath();
        rightPath = Paths.get(args[1]).toAbsolutePath();
        if (!leftPath.toFile().exists()) {
            throw new IllegalArgumentException("does not exist "+ leftPath.toString());
        }
        if (!rightPath.toFile().exists()) {
            throw new IllegalArgumentException("does not exist "+ rightPath.toString());
        }
    }

    public Path getLeftPath() {
        return leftPath;
    }

    public Path getRightPath() {
        return rightPath;
    }
}
