package com.github.frkator.test.results.diff;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Arguments {

    private Path leftPath;
    private Path rightPath;

    public Arguments(String[] args) {
        try {
            if (args.length < 2) {
                System.out.println(readHelp());
                System.exit(1);
            }
            if (args.length != 2) {
                throw new IllegalArgumentException("both arguments must be paths to an existing folder");
            }
            leftPath = Paths.get(args[0]).toAbsolutePath();
            rightPath = Paths.get(args[1]).toAbsolutePath();
            if (!leftPath.toFile().exists()) {
                throw new IllegalArgumentException("does not exist " + leftPath.toString());
            }
            if (!rightPath.toFile().exists()) {
                throw new IllegalArgumentException("does not exist " + rightPath.toString());
            }
        }
        catch (IllegalArgumentException iae) {
            iae.printStackTrace();
            System.err.println(readHelp());
            System.exit(1);
        }
    }

    private String readHelp() {
        return new Scanner(Arguments.class.getResourceAsStream("/help.txt")).useDelimiter("\\A").next();
    }

    public Path getLeftPath() {
        return leftPath;
    }

    public Path getRightPath() {
        return rightPath;
    }
}
