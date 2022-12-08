package com.fubailin.truffle;

import com.oracle.truffle.api.CallTarget;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class OverflowTest {
    @Test
    public void adding_1_to_int_max_overflows() {
        EasyScriptNode exprNode = new AdditionNode(
                new IntLiteralNode(Integer.MAX_VALUE),
                new IntLiteralNode(1));
        var rootNode = new EasyScriptRootNode(exprNode);
        CallTarget callTarget = rootNode.getCallTarget();

        var result = callTarget.call();

        assertNotEquals(Integer.MIN_VALUE, result);
    }
}
