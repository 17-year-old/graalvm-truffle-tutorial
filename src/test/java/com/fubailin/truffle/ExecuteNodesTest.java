package com.fubailin.truffle;

import com.oracle.truffle.api.CallTarget;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExecuteNodesTest {
    @Test
    public void adds_12_and_34_correctly() {
        EasyScriptNode exprNode = AdditionNodeGen.create(new IntLiteralNode(12), new IntLiteralNode(34));
        var rootNode = new EasyScriptRootNode(exprNode);
        CallTarget callTarget = rootNode.getCallTarget();
        var result = callTarget.call();
        assertEquals(46, result);
    }
}
