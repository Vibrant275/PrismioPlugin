// Copyright 2000-2023 JetBrains s.r.o. and other contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.

package com.vibrant.prismio.annotations;

import com.intellij.codeInspection.ProblemHighlightType;
import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.lang.annotation.HighlightSeverity;
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiLiteralExpression;
import com.vibrant.prismio.SimpleCreatePropertyQuickFix;
import com.vibrant.prismio.highlighter.PsSyntaxHighlighter;
import com.vibrant.prismio.SimpleUtil;
import com.vibrant.prismio.psi.SimpleProperty;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public final class SimpleAnnotator implements Annotator {

  // Define strings for the Simple language prefix - used for annotations, line markers, etc.
  public static final String PS_PREFIX_STR = "psmi";
  public static final String PS_SEPARATOR_STR = ":";

  @Override
  public void annotate(@NotNull final PsiElement element, @NotNull AnnotationHolder holder) {
    // Ensure the PSI Element is an expression
    if (!(element instanceof PsiLiteralExpression literalExpression)) {
      return;
    }

    // Ensure the PSI element contains a string that starts with the prefix and separator
    String value = literalExpression.getValue() instanceof String ? (String) literalExpression.getValue() : null;
    if (value == null || !value.startsWith(PS_PREFIX_STR + PS_SEPARATOR_STR)) {
      return;
    }

    // Define the text ranges (start is inclusive, end is exclusive)
    // "simple:key"
    //  01234567890
    TextRange prefixRange = TextRange.from(element.getTextRange().getStartOffset(), PS_PREFIX_STR.length() + 1);
    TextRange separatorRange = TextRange.from(prefixRange.getEndOffset(), PS_SEPARATOR_STR.length());
    TextRange keyRange = new TextRange(separatorRange.getEndOffset(), element.getTextRange().getEndOffset() - 1);

    // highlight "psmi" prefix and ":" separator
    holder.newSilentAnnotation(HighlightSeverity.INFORMATION)
        .range(prefixRange).textAttributes(DefaultLanguageHighlighterColors.KEYWORD).create();
    holder.newSilentAnnotation(HighlightSeverity.INFORMATION)
        .range(separatorRange).textAttributes(PsSyntaxHighlighter.SEPARATOR).create();


    // Get the list of properties for given key
    String key = value.substring(PS_PREFIX_STR.length() + PS_SEPARATOR_STR.length());
    List<SimpleProperty> properties = SimpleUtil.findProperties(element.getProject(), key);
    if (properties.isEmpty()) {
      holder.newAnnotation(HighlightSeverity.ERROR, "Unresolved property")
          .range(keyRange)
          .highlightType(ProblemHighlightType.LIKE_UNKNOWN_SYMBOL)
          // ** Tutorial step 19. - Add a quick fix for the string containing possible properties
          .withFix(new SimpleCreatePropertyQuickFix(key))
          .create();
    } else {
      // Found at least one property, force the text attributes to Simple syntax value character
      holder.newSilentAnnotation(HighlightSeverity.INFORMATION)
          .range(keyRange).textAttributes(PsSyntaxHighlighter.VALUE).create();
    }
  }

}
