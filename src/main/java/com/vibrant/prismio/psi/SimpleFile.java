// Copyright 2000-2022 JetBrains s.r.o. and other contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.

package com.vibrant.prismio.psi;

import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;
import com.vibrant.prismio.PsFileType;
import com.vibrant.prismio.PrismioLanguage;
import org.jetbrains.annotations.NotNull;

public class SimpleFile extends PsiFileBase {

  public SimpleFile(@NotNull FileViewProvider viewProvider) {
    super(viewProvider, PrismioLanguage.INSTANCE);
  }

  @NotNull
  @Override
  public FileType getFileType() {
    return PsFileType.INSTANCE;
  }

  @Override
  public String toString() {
    return "Simple File";
  }

}
