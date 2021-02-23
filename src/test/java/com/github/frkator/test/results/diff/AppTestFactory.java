package com.github.frkator.test.results.diff;

import org.apache.maven.surefire.shared.io.output.ByteArrayOutputStream;

import java.io.PrintStream;

public class AppTestFactory {

    public static App create() throws Exception {
        return create(new Arguments(new String[]{
                "src/test/resources/byte-buddy-1",
                "src/test/resources/byte-buddy-2",
        }));
    }

    public static App create(Arguments args) throws Exception {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        return new App(
                args,
                new PrintStream(byteArrayOutputStream),
                new Settings(new PropertiesFactory().create())
        );
    }

}
