package com.vibrant.prismio;

import com.intellij.lang.BracePair;
import com.intellij.lang.PairedBraceMatcher;
import com.intellij.psi.PsiFile;
import com.intellij.psi.tree.IElementType;
import com.vibrant.prismio.psi.PrismioTypes;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Brace matcher for Prismio language
 * Handles matching of (), {}, []
 */
public class PrismioBraceMatcher implements PairedBraceMatcher {

    private static final BracePair[] PAIRS = new BracePair[]{
            new BracePair(PrismioTypes.SEPARATOR, PrismioTypes.SEPARATOR, true),  // ()
            new BracePair(PrismioTypes.LBRACE, PrismioTypes.RBRACE, true),        // {}
            new BracePair(PrismioTypes.SEPARATOR, PrismioTypes.SEPARATOR, true)   // []
    };

    @NotNull
    @Override
    public BracePair[] getPairs() {
        return PAIRS;
    }

    @Override
    public boolean isPairedBracesAllowedBeforeType(@NotNull IElementType lbraceType,
                                                     @Nullable IElementType contextType) {
        return true;
    }

    @Override
    public int getCodeConstructStart(PsiFile file, int openingBraceOffset) {
        return openingBraceOffset;
    }
}