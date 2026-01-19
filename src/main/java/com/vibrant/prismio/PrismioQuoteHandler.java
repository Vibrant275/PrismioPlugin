package com.vibrant.prismio;

import com.intellij.codeInsight.editorActions.QuoteHandler;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.highlighter.HighlighterIterator;
import com.intellij.psi.tree.IElementType;
import com.vibrant.prismio.psi.PrismioTypes;
import org.jetbrains.annotations.NotNull;

/**
 * Quote handler for Prismio language
 * Handles auto-completion of double quotes and single quotes
 */
public class PrismioQuoteHandler implements QuoteHandler {

    @Override
    public boolean isClosingQuote(HighlighterIterator iterator, int offset) {
        IElementType tokenType = iterator.getTokenType();
        if (tokenType == PrismioTypes.STRING_LITERAL || tokenType == PrismioTypes.CHARACTER_LITERAL) {
            int end = iterator.getEnd();
            return offset == end - 1;
        }
        return false;
    }

    @Override
    public boolean isOpeningQuote(HighlighterIterator iterator, int offset) {
        IElementType tokenType = iterator.getTokenType();
        if (tokenType == PrismioTypes.STRING_LITERAL || tokenType == PrismioTypes.CHARACTER_LITERAL) {
            int start = iterator.getStart();
            return offset == start;
        }
        return false;
    }

    @Override
    public boolean hasNonClosedLiteral(Editor editor, HighlighterIterator iterator, int offset) {
        // Return true to allow inserting closing quote
        return true;
    }

    @Override
    public boolean isInsideLiteral(HighlighterIterator iterator) {
        IElementType tokenType = iterator.getTokenType();
        return tokenType == PrismioTypes.STRING_LITERAL || tokenType == PrismioTypes.CHARACTER_LITERAL;
    }
}