// Copyright 2000-2024 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.

package com.vibrant.prismio;

import com.intellij.lang.cacheBuilder.DefaultWordsScanner;
import com.intellij.lang.cacheBuilder.WordsScanner;
import com.intellij.lang.findUsages.FindUsagesProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNamedElement;
import com.intellij.psi.tree.TokenSet;
import com.vibrant.prismio.annotations.SimpleAnnotator;
import com.vibrant.prismio.lexer.LexerAdapter;
import com.vibrant.prismio.psi.SimpleProperty;
import com.vibrant.prismio.psi.SimpleTokenSets;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

final class SimpleFindUsagesProvider implements FindUsagesProvider {

  @Override
  public WordsScanner getWordsScanner() {
    return new DefaultWordsScanner(new LexerAdapter(),
        SimpleTokenSets.IDENTIFIERS,
        SimpleTokenSets.COMMENTS,
        TokenSet.EMPTY);
  }

  @Override
  public boolean canFindUsagesFor(@NotNull PsiElement psiElement) {
    return psiElement instanceof PsiNamedElement;
  }

  @Nullable
  @Override
  public String getHelpId(@NotNull PsiElement psiElement) {
    return null;
  }

  @NotNull
  @Override
  public String getType(@NotNull PsiElement element) {
    if (element instanceof SimpleProperty) {
      return "simple property";
    }
    return "";
  }

  @NotNull
  @Override
  public String getDescriptiveName(@NotNull PsiElement element) {
    if (element instanceof SimpleProperty) {
      return ((SimpleProperty) element).getKey();
    }
    return "";
  }

  @NotNull
  @Override
  public String getNodeText(@NotNull PsiElement element, boolean useFullName) {
    if (element instanceof SimpleProperty) {
      return ((SimpleProperty) element).getKey() +
          SimpleAnnotator.PS_SEPARATOR_STR +
          ((SimpleProperty) element).getValue();
    }
    return "";
  }

}
