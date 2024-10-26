package com.vibrant.prismio.providers;

import com.intellij.ide.IconProvider;
import com.intellij.psi.PsiElement;
import com.vibrant.prismio.psi.PrismioProperty;
import com.vibrant.prismio.utils.Icons;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

final class PsPropertyIconProvider extends IconProvider {

  @Override
  public @Nullable Icon getIcon(@NotNull PsiElement element, int flags) {
    return element instanceof PrismioProperty ? Icons.FILE : null;
  }
}
