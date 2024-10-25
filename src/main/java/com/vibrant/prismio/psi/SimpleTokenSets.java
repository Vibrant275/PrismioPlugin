// Copyright 2000-2022 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.

package com.vibrant.prismio.psi;

import com.intellij.psi.tree.TokenSet;

public interface SimpleTokenSets {

  TokenSet IDENTIFIERS = TokenSet.create(SimpleTypes.KEY);

  TokenSet COMMENTS = TokenSet.create(SimpleTypes.COMMENT);

}
