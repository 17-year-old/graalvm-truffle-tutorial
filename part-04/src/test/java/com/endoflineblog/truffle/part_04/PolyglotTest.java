package com.endoflineblog.truffle.part_04;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Value;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PolyglotTest {
    private Context context;

    @BeforeEach
    public void setUp() {
        this.context = Context.create();
    }

    @AfterEach
    public void tearDown() {
        this.context.close();
    }

    /**
     * This test invokes the {@link EasyScriptTruffleLanguage} class
     * through GraalVM's polyglot API.
     */
    @Test
    public void runs_EasyScript_code() {
        Value result = this.context.eval("ezs",
                "10 + 24 + 56.0");
        assertEquals(90.0, result.asDouble(), 0.0);
    }
}
