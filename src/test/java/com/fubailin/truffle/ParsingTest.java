package com.fubailin.truffle;

import com.oracle.truffle.api.CallTarget;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ParsingTest {
    @Test
    public void parses_and_executes_easyscript_code_correctly() {
        EasyScriptNode exprNode = EasyScriptTruffleParser.parse("1 + 2 + 3.0 + 4");
        var rootNode = new EasyScriptRootNode(exprNode);
        CallTarget callTarget = rootNode.getCallTarget();

        var result = callTarget.call();

        assertEquals(10.0, result);
    }

    @Test
    public void throws_an_exception_when_the_code_cannot_be_parsed() {
        assertThrows(ParseCancellationException.class, () -> EasyScriptTruffleParser.parse("xyz"));
    }

    @Test
    public void parsing_a_large_integer_fall_backs_to_double() {
        // this is 12,345,678,901
        String largeInt = "12345678901";
        EasyScriptNode exprNode = EasyScriptTruffleParser.parse(largeInt);

        assertTrue(exprNode instanceof DoubleLiteralNode);
    }
}