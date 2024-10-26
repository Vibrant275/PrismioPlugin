package com.vibrant.prismio.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.vibrant.prismio.psi.PrismioNamedElement;
import org.jetbrains.annotations.NotNull;

public abstract class PrismioNamedElementImpl extends ASTWrapperPsiElement implements PrismioNamedElement {

  public PrismioNamedElementImpl(@NotNull ASTNode node) {
    super(node);
  }

}
