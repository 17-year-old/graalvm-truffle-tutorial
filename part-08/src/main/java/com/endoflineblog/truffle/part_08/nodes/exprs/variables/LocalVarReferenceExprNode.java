package com.endoflineblog.truffle.part_08.nodes.exprs.variables;

import com.endoflineblog.truffle.part_08.common.LocalVariableFrameSlotId;
import com.endoflineblog.truffle.part_08.exceptions.EasyScriptException;
import com.endoflineblog.truffle.part_08.nodes.exprs.EasyScriptExprNode;
import com.endoflineblog.truffle.part_08.nodes.stmts.variables.LocalVarDeclStmtNode;
import com.oracle.truffle.api.dsl.NodeField;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;

/**
 * A Node that represents the reference to a variable local to a function.
 * Almost identical to the class with the same name from part 7,
 * the only difference is a new specialization, {@link #readBool},
 * that deals with boolean values.
 *
 * @see #readBool
 */
@NodeField(name = "frameSlot", type = int.class)
public abstract class LocalVarReferenceExprNode extends EasyScriptExprNode {
    protected abstract int getFrameSlot();

    @Specialization(guards = "frame.isInt(getFrameSlot())")
    protected int readInt(VirtualFrame frame) {
        return frame.getInt(this.getFrameSlot());
    }

    @Specialization(guards = "frame.isDouble(getFrameSlot())", replaces = "readInt")
    protected double readDouble(VirtualFrame frame) {
        return frame.getDouble(this.getFrameSlot());
    }

    @Specialization(guards = "frame.isBoolean(getFrameSlot())")
    protected boolean readBool(VirtualFrame frame) {
        return frame.getBoolean(this.getFrameSlot());
    }

    @Specialization(replaces = {"readInt", "readDouble", "readBool"})
    protected Object readObject(VirtualFrame frame) {
        Object ret = frame.getObject(this.getFrameSlot());
        if (ret == LocalVarDeclStmtNode.DUMMY) {
            throw new EasyScriptException("Cannot access '" +
                    ((LocalVariableFrameSlotId) frame.getFrameDescriptor().getSlotName(this.getFrameSlot())).variableName +
                    "' before initialization");
        }
        return ret;
    }
}
