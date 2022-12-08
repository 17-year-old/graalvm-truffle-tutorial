package com.fubailin.truffle;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.RootNode;

public class EasyScriptRootNode extends RootNode {
    @SuppressWarnings("FieldMayBeFinal")
    @Child
    private EasyScriptNode exprNode;

    public EasyScriptRootNode(EasyScriptNode exprNode) {
        super(null);

        this.exprNode = exprNode;
    }

    @Override
    public Object execute(VirtualFrame frame) {
        return this.exprNode.executeGeneric(frame);
    }


}
