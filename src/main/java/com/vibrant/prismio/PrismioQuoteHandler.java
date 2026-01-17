package com.vibrant.prismio;

import com.intellij.codeInsight.editorActions.SimpleTokenSetQuoteHandler;
import com.vibrant.prismio.psi.PrismioTypes;
import com.intellij.psi.tree.TokenSet;

/**
 * Quote handler for Prismio language
 * Handles auto-completion of quotes
 */
public class PrismioQuoteHandler extends SimpleTokenSetQuoteHandler {

    public PrismioQuoteHandler() {
        super(TokenSet.create(
                PrismioTypes.STRING_LITERAL,
                PrismioTypes.CHARACTER_LITERAL
        ));
    }
}