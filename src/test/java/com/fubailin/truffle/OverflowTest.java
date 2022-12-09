package com.fubailin.truffle;

import com.oracle.truffle.api.CallTarget;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OverflowTest {
    @Test
    public void adding_1_to_int_max_overflows() {
        EasyScriptNode exprNode = AdditionNodeGen.create(
                new IntLiteralNode(Integer.MAX_VALUE),
                new IntLiteralNode(1));
        var rootNode = new EasyScriptRootNode(exprNode);
        CallTarget callTarget = rootNode.getCallTarget();

        var result = callTarget.call();

        assertEquals(Integer.MAX_VALUE + 1.0, result);
    }
}
