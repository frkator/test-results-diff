package com.github.frkator.test.results.diff;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.github.frkator.test.results.diff.AppTestFactory.create;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AppTest {

    @DisplayName("should assert test sets are as expected ")
    @Test
    void testApp() throws Exception {
        App app = create();
        assertEquals(
                "[net.bytebuddy.agent.AttacherTest [4/0/0/0], net.bytebuddy.agent.ByteBuddyAgentAttachmentProviderTest [5/0/0/0], net.bytebuddy.agent.builder.AgentBuilderCircularityLockTest [4/0/0/0], net.bytebuddy.asm.AdviceAnnotationTest [42/0/0/0], net.bytebuddy.asm.AdviceArgumentHandlerCopyingTest [9/0/0/0]]",
                app.leftSet.toString()
        );
        assertEquals(
                "[net.bytebuddy.agent.ByteBuddyAgentAgentProviderTest [2/0/0/0], net.bytebuddy.agent.ByteBuddyAgentAttachmentProviderTest [5/2/0/0], net.bytebuddy.agent.builder.AgentBuilderCircularityLockTest [4/0/0/0], net.bytebuddy.asm.AdviceAnnotationTest [42/0/0/0], net.bytebuddy.asm.AdviceArgumentHandlerCopyingTest [9/0/0/0]]",
                app.rightSet.toString()
        );
    }

}
