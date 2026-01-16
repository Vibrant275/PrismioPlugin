package com.vibrant.prismio.annotations;

import com.intellij.codeInspection.ProblemHighlightType;
import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.lang.annotation.HighlightSeverity;
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiLiteralExpression;
import com.vibrant.prismio.highlighter.PsSyntaxHighlighter;
import org.jetbrains.annotations.NotNull;

/**
 * Annotator for Prismio language - provides additional semantic highlighting
 * This is simplified for the basic syntax highlighting needs
 */
public final class SimpleAnnotator implements Annotator {

  // Define strings for the Simple language prefix - used for annotations, line markers, etc.
  public static final String PS_PREFIX_STR = "psm";
  public static final String PS_SEPARATOR_STR = ":";

  @Override
  public void annotate(@NotNull final PsiElement element, @NotNull AnnotationHolder holder) {
    // For now, we'll keep this simple and just do basic validation
    // The main syntax highlighting is handled by PsSyntaxHighlighter

    // You can add semantic highlighting here later, for example:
    // - Highlighting unresolved references
    // - Type checking errors
    // - Unused variables
    // etc.
  }
}