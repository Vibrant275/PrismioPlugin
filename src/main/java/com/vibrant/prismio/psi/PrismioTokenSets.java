package com.vibrant.prismio.psi;

import com.intellij.psi.tree.TokenSet;

public interface PrismioTokenSets {

  TokenSet IDENTIFIERS = TokenSet.create(PrismioTypes.KEY);

  TokenSet COMMENTS = TokenSet.create(PrismioTypes.SINGLE_LINE_COMMENT , PrismioTypes.MULTILINE_COMMENT);

}
