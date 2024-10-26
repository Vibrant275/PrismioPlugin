package com.vibrant.prismio;

import com.intellij.lang.cacheBuilder.DefaultWordsScanner;
import com.intellij.lang.cacheBuilder.WordsScanner;
import com.intellij.lang.findUsages.FindUsagesProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNamedElement;
import com.intellij.psi.tree.TokenSet;
import com.vibrant.prismio.annotations.SimpleAnnotator;
import com.vibrant.prismio.lexer.LexerAdapter;
import com.vibrant.prismio.psi.PrismioProperty;
import com.vibrant.prismio.psi.PrismioTokenSets;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

final class SimpleFindUsagesProvider implements FindUsagesProvider {

  @Override
  public WordsScanner getWordsScanner() {
    return new DefaultWordsScanner(new LexerAdapter(),
        PrismioTokenSets.IDENTIFIERS,
        PrismioTokenSets.COMMENTS,
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
    if (element instanceof PrismioProperty) {
      return "simple property";
    }
    return "";
  }

  @NotNull
  @Override
  public String getDescriptiveName(@NotNull PsiElement element) {
    if (element instanceof PrismioProperty) {
      return ((PrismioProperty) element).getKey();
    }
    return "";
  }

  @NotNull
  @Override
  public String getNodeText(@NotNull PsiElement element, boolean useFullName) {
    if (element instanceof PrismioProperty) {
      return ((PrismioProperty) element).getKey() +
          SimpleAnnotator.PS_SEPARATOR_STR +
          ((PrismioProperty) element).getValue();
    }
    return "";
  }

}
