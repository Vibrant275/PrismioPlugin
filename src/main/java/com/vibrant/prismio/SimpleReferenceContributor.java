// Copyright 2000-2023 JetBrains s.r.o. and other contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.

package com.vibrant.prismio;

import com.intellij.openapi.util.TextRange;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.*;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;

import static com.vibrant.prismio.annotations.SimpleAnnotator.PS_PREFIX_STR;
import static com.vibrant.prismio.annotations.SimpleAnnotator.PS_SEPARATOR_STR;

final class SimpleReferenceContributor extends PsiReferenceContributor {

  @Override
  public void registerReferenceProviders(@NotNull PsiReferenceRegistrar registrar) {
    registrar.registerReferenceProvider(PlatformPatterns.psiElement(PsiLiteralExpression.class),
        new PsiReferenceProvider() {
          @Override
          public PsiReference @NotNull [] getReferencesByElement(@NotNull PsiElement element,
                                                                 @NotNull ProcessingContext context) {
            PsiLiteralExpression literalExpression = (PsiLiteralExpression) element;
            String value = literalExpression.getValue() instanceof String ?
                (String) literalExpression.getValue() : null;
            if ((value != null && value.startsWith(PS_PREFIX_STR + PS_SEPARATOR_STR))) {
              TextRange property = new TextRange(PS_PREFIX_STR.length() + PS_SEPARATOR_STR.length() + 1,
                  value.length() + 1);
              return new PsiReference[]{new SimpleReference(element, property)};
            }
            return PsiReference.EMPTY_ARRAY;
          }
        });
  }

}
