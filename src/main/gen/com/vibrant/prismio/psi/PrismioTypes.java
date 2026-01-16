// This is a generated file. Not intended for manual editing.
package com.vibrant.prismio.psi;

import com.intellij.psi.tree.IElementType;
import com.intellij.psi.PsiElement;
import com.intellij.lang.ASTNode;

public interface PrismioTypes {

  IElementType BLOCK = new PrismioElementType("BLOCK");
  IElementType FUNCTION_DECL = new PrismioElementType("FUNCTION_DECL");
  IElementType LBRACE = new PrismioElementType("LBRACE");
  IElementType RBRACE = new PrismioElementType("RBRACE");

  IElementType BOOLEAN = new PrismioTokenType("BOOLEAN");
  IElementType CHARACTER_LITERAL = new PrismioTokenType("CHARACTER_LITERAL");
  IElementType FLOAT = new PrismioTokenType("FLOAT");
  IElementType FN = new PrismioTokenType("FN");
  IElementType IDENTIFIER = new PrismioTokenType("IDENTIFIER");
  IElementType INTEGER = new PrismioTokenType("INTEGER");
  IElementType KEYWORD = new PrismioTokenType("KEYWORD");
  IElementType MULTILINE_COMMENT = new PrismioTokenType("MULTILINE_COMMENT");
  IElementType OPERATOR = new PrismioTokenType("OPERATOR");
  IElementType PARAMETER_LIST = new PrismioTokenType("parameter_list");
  IElementType SEPARATOR = new PrismioTokenType("SEPARATOR");
  IElementType SINGLE_LINE_COMMENT = new PrismioTokenType("SINGLE_LINE_COMMENT");
  IElementType STATEMENT = new PrismioTokenType("statement");
  IElementType STRING_LITERAL = new PrismioTokenType("STRING_LITERAL");
  IElementType TYPE_KEYWORD = new PrismioTokenType("TYPE_KEYWORD");
  IElementType KEY = new PrismioTokenType("KEY");
  IElementType VALUE = new PrismioTokenType("KEY");

  class Factory {
    public static PsiElement createElement(ASTNode node) {
      IElementType type = node.getElementType();
      throw new AssertionError("Unknown element type: " + type);
    }
  }
}