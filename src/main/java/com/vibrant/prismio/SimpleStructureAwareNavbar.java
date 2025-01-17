// Copyright 2000-2023 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.

package com.vibrant.prismio;

import com.intellij.icons.AllIcons;
import com.intellij.ide.navigationToolbar.StructureAwareNavBarModelExtension;
import com.intellij.lang.Language;
import com.vibrant.prismio.psi.SimpleFile;
import com.vibrant.prismio.psi.SimpleProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.Icon;

final class SimpleStructureAwareNavbar extends StructureAwareNavBarModelExtension {

  @NotNull
  @Override
  protected Language getLanguage() {
    return PrismioLanguage.INSTANCE;
  }

  @Override
  public @Nullable String getPresentableText(Object object) {
    if (object instanceof SimpleFile) {
      return ((SimpleFile) object).getName();
    }
    if (object instanceof SimpleProperty) {
      return ((SimpleProperty) object).getName();
    }

    return null;
  }

  @Override
  @Nullable
  public Icon getIcon(Object object) {
    if (object instanceof SimpleProperty) {
      return AllIcons.Nodes.Property;
    }

    return null;
  }

}
