// Copyright 2000-2023 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.

package com.vibrant.prismio.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.navigation.ItemPresentation;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.vibrant.prismio.psi.PrismioProperty;
import com.vibrant.prismio.psi.PrismioTypes;
import com.vibrant.prismio.psi.PrismioElementFactory;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class PrismioPsiImplUtil {

  public static String getKey(PrismioProperty element) {
    ASTNode keyNode = element.getNode().findChildByType(PrismioTypes.KEY);
    if (keyNode != null) {
      // IMPORTANT: Convert embedded escaped spaces to simple spaces
      return keyNode.getText().replaceAll("\\\\ ", " ");
    } else {
      return null;
    }
  }

  public static String getValue(PrismioProperty element) {
    ASTNode valueNode = element.getNode().findChildByType(PrismioTypes.VALUE);
    if (valueNode != null) {
      return valueNode.getText();
    } else {
      return null;
    }
  }

  public static String getName(PrismioProperty element) {
    return getKey(element);
  }

  public static PsiElement setName(PrismioProperty element, String newName) {
    ASTNode keyNode = element.getNode().findChildByType(PrismioTypes.KEY);
    if (keyNode != null) {
      PrismioProperty property = PrismioElementFactory.createProperty(element.getProject(), newName);
      ASTNode newKeyNode = property.getFirstChild().getNode();
      element.getNode().replaceChild(keyNode, newKeyNode);
    }
    return element;
  }

  public static PsiElement getNameIdentifier(PrismioProperty element) {
    ASTNode keyNode = element.getNode().findChildByType(PrismioTypes.KEY);
    if (keyNode != null) {
      return keyNode.getPsi();
    } else {
      return null;
    }
  }

  public static ItemPresentation getPresentation(final PrismioProperty element) {
    return new ItemPresentation() {
      @Nullable
      @Override
      public String getPresentableText() {
        return element.getKey();
      }

      @Nullable
      @Override
      public String getLocationString() {
        PsiFile containingFile = element.getContainingFile();
        return containingFile == null ? null : containingFile.getName();
      }

      @Override
      public Icon getIcon(boolean unused) {
        return element.getIcon(0);
      }
    };
  }

}
