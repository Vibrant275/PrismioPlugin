package com.vibrant.prismio.annotations;

import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;

/**
 * Legacy annotator - kept for compatibility
 * Main annotator is now PrismioAnnotator
 */
public final class SimpleAnnotator implements Annotator {

    // Constants for backwards compatibility
    public static final String PS_PREFIX_STR = "psm";
    public static final String PS_SEPARATOR_STR = ":";

    @Override
    public void annotate(@NotNull final PsiElement element, @NotNull AnnotationHolder holder) {
        // Delegate to PrismioAnnotator or keep minimal functionality
        // This is kept for compatibility with existing code like SimpleFindUsagesProvider
    }
}