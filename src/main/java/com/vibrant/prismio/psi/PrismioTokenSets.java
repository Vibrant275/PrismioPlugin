package com.vibrant.prismio.psi;

import com.intellij.psi.tree.TokenSet;

public interface PrismioTokenSets {
  TokenSet IDENTIFIERS = TokenSet.create(PrismioTypes.IDENTIFIER);

  TokenSet COMMENTS = TokenSet.create(
          PrismioTypes.SINGLE_LINE_COMMENT,
          PrismioTypes.MULTILINE_COMMENT
  );

  TokenSet KEYWORDS = TokenSet.create(PrismioTypes.KEYWORD);

  TokenSet LITERALS = TokenSet.create(
          PrismioTypes.STRING_LITERAL,
          PrismioTypes.CHARACTER_LITERAL,
          PrismioTypes.INTEGER,
          PrismioTypes.FLOAT,
          PrismioTypes.BOOLEAN
  );
}