package com.fubailin.truffle;

import org.antlr.v4.runtime.BailErrorStrategy;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.io.IOException;
import java.io.Reader;

public final class EasyScriptTruffleParser {
    public static EasyScriptNode parse(String program) {
        return parse(CharStreams.fromString(program));
    }

    public static EasyScriptNode parse(Reader program) throws IOException {
        return parse(CharStreams.fromReader(program));
    }

    private static EasyScriptNode parse(CharStream inputStream) {
        var lexer = new EasyScriptLexer(inputStream);
        // remove the default console error listener
        lexer.removeErrorListeners();
        var parser = new EasyScriptParser(new CommonTokenStream(lexer));
        // remove the default console error listener
        parser.removeErrorListeners();
        // throw an exception when a parsing error is encountered
        parser.setErrorHandler(new BailErrorStrategy());
        EasyScriptParser.ExprContext context = parser.start().expr();
        return expr2TruffleNode(context);
    }

    private static EasyScriptNode expr2TruffleNode(EasyScriptParser.ExprContext expr) {
        return expr instanceof EasyScriptParser.AddExprContext
                ? addExpr2AdditionNode((EasyScriptParser.AddExprContext) expr)
                : literalExpr2ExprNode((EasyScriptParser.LiteralExprContext) expr);
    }

    private static AdditionNode addExpr2AdditionNode(EasyScriptParser.AddExprContext addExpr) {
        return AdditionNodeGen.create(
                expr2TruffleNode(addExpr.left),
                expr2TruffleNode(addExpr.right)
        );
    }

    private static EasyScriptNode literalExpr2ExprNode(EasyScriptParser.LiteralExprContext literalExpr) {
        TerminalNode intTerminal = literalExpr.literal().INT();
        return intTerminal != null
                ? parseIntLiteral(intTerminal.getText())
                : parseDoubleLiteral(literalExpr.getText());
    }

    private static EasyScriptNode parseIntLiteral(String text) {
        try {
            return new IntLiteralNode(Integer.parseInt(text));
        } catch (NumberFormatException e) {
            // it's possible that the integer literal is too big to fit in a 32-bit Java `int` -
            // in that case, fall back to a double literal
            return parseDoubleLiteral(text);
        }
    }

    private static DoubleLiteralNode parseDoubleLiteral(String text) {
        return new DoubleLiteralNode(Double.parseDouble(text));
    }
}