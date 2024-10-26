package com.vibrant.prismio;

import com.intellij.icons.AllIcons;
import com.intellij.ide.navigationToolbar.StructureAwareNavBarModelExtension;
import com.intellij.lang.Language;
import com.vibrant.prismio.psi.PrismioFile;
import com.vibrant.prismio.psi.PrismioProperty;
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
    if (object instanceof PrismioFile) {
      return ((PrismioFile) object).getName();
    }
    if (object instanceof PrismioProperty) {
      return ((PrismioProperty) object).getName();
    }

    return null;
  }

  @Override
  @Nullable
  public Icon getIcon(Object object) {
    if (object instanceof PrismioProperty) {
      return AllIcons.Nodes.Property;
    }

    return null;
  }

}
