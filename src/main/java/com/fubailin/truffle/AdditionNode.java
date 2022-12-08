package com.fubailin.truffle;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;

public class AdditionNode extends EasyScriptNode {

    @SuppressWarnings("FieldMayBeFinal")
    @Node.Child
    private EasyScriptNode leftNode, rightNode;

    public AdditionNode(EasyScriptNode leftNode, EasyScriptNode rightNode) {
        this.leftNode = leftNode;
        this.rightNode = rightNode;
    }

    @Override
    public int executeInt(VirtualFrame frame) {
        int leftValue = this.leftNode.executeInt(frame);
        int rightValue = this.rightNode.executeInt(frame);
        return leftValue + rightValue;
    }
}
