// This is a generated file. Not intended for manual editing.
package com.vibrant.prismio.psi;

import com.intellij.psi.tree.IElementType;
import com.intellij.psi.PsiElement;
import com.intellij.lang.ASTNode;

public interface PrismioTypes {

  // Token types for Prismio language
  IElementType KEYWORD = new PrismioTokenType("KEYWORD");
  IElementType TYPE_KEYWORD = new PrismioTokenType("TYPE_KEYWORD");
  IElementType IDENTIFIER = new PrismioTokenType("IDENTIFIER");
  IElementType OPERATOR = new PrismioTokenType("OPERATOR");
  IElementType SEPARATOR = new PrismioTokenType("SEPARATOR");

  IElementType STRING_LITERAL = new PrismioTokenType("STRING_LITERAL");
  IElementType CHARACTER_LITERAL = new PrismioTokenType("CHARACTER_LITERAL");
  IElementType INTEGER = new PrismioTokenType("INTEGER");
  IElementType FLOAT = new PrismioTokenType("FLOAT");
  IElementType BOOLEAN = new PrismioTokenType("BOOLEAN");

  IElementType SINGLE_LINE_COMMENT = new PrismioTokenType("SINGLE_LINE_COMMENT");
  IElementType MULTILINE_COMMENT = new PrismioTokenType("MULTILINE_COMMENT");

  // Element types
  IElementType PROPERTY = new PrismioElementType("PROPERTY");

  // Legacy types (for compatibility)
  IElementType COMMENT = new PrismioTokenType("COMMENT");
  IElementType CRLF = new PrismioTokenType("CRLF");
  IElementType KEY = new PrismioTokenType("KEY");
  IElementType VALUE = new PrismioTokenType("VALUE");

  class Factory {
    public static PsiElement createElement(ASTNode node) {
      IElementType type = node.getElementType();
      if (type == PROPERTY) {
        return new com.vibrant.prismio.psi.impl.PrismioPropertyImpl(node);
      }
      throw new AssertionError("Unknown element type: " + type);
    }
  }
}