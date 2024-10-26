// This is a generated file. Not intended for manual editing.
package com.vibrant.prismio.psi;

import com.intellij.psi.tree.IElementType;
import com.intellij.psi.PsiElement;
import com.intellij.lang.ASTNode;
import com.vibrant.prismio.psi.impl.*;

public interface PrismioTypes {

  IElementType PROPERTY = new PrismioElementType("PROPERTY");

  IElementType BOOLEAN = new PrismioTokenType("BOOLEAN");
  IElementType CHARACTER_LITERAL = new PrismioTokenType("CHARACTER_LITERAL");
  IElementType COMMENT = new PrismioTokenType("COMMENT");
  IElementType CRLF = new PrismioTokenType("CRLF");
  IElementType FLOAT = new PrismioTokenType("FLOAT");
  IElementType IDENTIFIER = new PrismioTokenType("IDENTIFIER");
  IElementType INTEGER = new PrismioTokenType("INTEGER");
  IElementType KEY = new PrismioTokenType("KEY");
  IElementType KEYWORD = new PrismioTokenType("KEYWORD");
  IElementType MULTILINE_COMMENT = new PrismioTokenType("MULTILINE_COMMENT");
  IElementType OPERATOR = new PrismioTokenType("OPERATOR");
  IElementType SEPARATOR = new PrismioTokenType("SEPARATOR");
  IElementType SINGLE_LINE_COMMENT = new PrismioTokenType("SINGLE_LINE_COMMENT");
  IElementType STRING_LITERAL = new PrismioTokenType("STRING_LITERAL");
  IElementType VALUE = new PrismioTokenType("VALUE");

  class Factory {
    public static PsiElement createElement(ASTNode node) {
      IElementType type = node.getElementType();
      if (type == PROPERTY) {
        return new PrismioPropertyImpl(node);
      }
      throw new AssertionError("Unknown element type: " + type);
    }
  }
}
