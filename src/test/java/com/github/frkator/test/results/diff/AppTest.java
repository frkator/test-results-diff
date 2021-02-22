package com.github.frkator.test.results.diff;

import org.apache.maven.surefire.shared.io.output.ByteArrayOutputStream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.PrintStream;

public class AppTest {

    @Test
    void testApp() throws Exception {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        App app = new App(
                new Arguments(new String[]{
                        "src/test/resources/byte-buddy-1",
                        "src/test/resources/byte-buddy-2",
                }),
                new PrintStream(byteArrayOutputStream),
                new Settings(new PropertiesFactory().create())
        );
        Assertions.assertEquals(
                "[net.bytebuddy.asm.AdviceArgumentHandlerCopyingTest [9/0/0/0], net.bytebuddy.asm.AdviceAnnotationTest [42/0/0/0], net.bytebuddy.agent.builder.AgentBuilderCircularityLockTest [4/0/0/0], net.bytebuddy.agent.AttacherTest [4/0/0/0], net.bytebuddy.agent.ByteBuddyAgentAttachmentProviderTest [5/0/0/0]]",
                app.leftSet.toString()
        );
        Assertions.assertEquals(
                "[net.bytebuddy.asm.AdviceArgumentHandlerCopyingTest [9/0/0/0], net.bytebuddy.asm.AdviceAnnotationTest [42/0/0/0], net.bytebuddy.agent.builder.AgentBuilderCircularityLockTest [4/0/0/0], net.bytebuddy.agent.ByteBuddyAgentAgentProviderTest [2/0/0/0], net.bytebuddy.agent.ByteBuddyAgentAttachmentProviderTest [5/2/0/0]]",
                app.rightSet.toString()
        );
    }

}
