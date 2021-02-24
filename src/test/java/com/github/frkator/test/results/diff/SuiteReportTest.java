package com.github.frkator.test.results.diff;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class SuiteReportTest {

    @Test
    void testSuiteReport() throws Exception {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        App app = new App(
                new Arguments(new String[]{
                        "src/test/resources/byte-buddy-1",
                        "src/test/resources/byte-buddy-2",
                }),
                new PrintStream(byteArrayOutputStream),
                new Settings(new PropertiesFactory().create())
        );
        new SuiteReport(app).process();
        Assertions.assertEquals(
                "only left\n" +
                        "net.bytebuddy.agent.AttacherTest [4/0/0/0]\n" +
                        "net.bytebuddy.agent.ByteBuddyAgentAttachmentProviderTest [5/0/0/0]\n" +
                        "only right\n" +
                        "net.bytebuddy.agent.ByteBuddyAgentAgentProviderTest [2/0/0/0]\n" +
                        "net.bytebuddy.agent.ByteBuddyAgentAttachmentProviderTest [5/2/0/0]\n" +
                        "common\n" +
                        "net.bytebuddy.asm.AdviceArgumentHandlerCopyingTest [9/0/0/0]\n" +
                        "net.bytebuddy.asm.AdviceAnnotationTest [42/0/0/0]\n" +
                        "net.bytebuddy.agent.builder.AgentBuilderCircularityLockTest [4/0/0/0]\n",
                byteArrayOutputStream.toString()
        );
    }

}
