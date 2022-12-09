package com.fubailin.truffle;

import com.oracle.truffle.api.dsl.TypeSystemReference;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.UnexpectedResultException;

@TypeSystemReference(EasyScriptTypeSystem.class)
public abstract class EasyScriptNode extends Node {
    public abstract int executeInt(VirtualFrame frame) throws UnexpectedResultException;

    public abstract double executeDouble(VirtualFrame frame);

    public abstract Object executeGeneric(VirtualFrame frame);
}