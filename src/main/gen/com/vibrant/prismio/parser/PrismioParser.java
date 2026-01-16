// This is a generated file. Not intended for manual editing.
package com.vibrant.prismio.parser;

import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiBuilder.Marker;
import static com.vibrant.prismio.psi.PrismioTypes.*;
import static com.intellij.lang.parser.GeneratedParserUtilBase.*;
import com.intellij.psi.tree.IElementType;
import com.intellij.lang.ASTNode;
import com.intellij.psi.tree.TokenSet;
import com.intellij.lang.PsiParser;
import com.intellij.lang.LightPsiParser;

@SuppressWarnings({"SimplifiableIfStatement", "UnusedAssignment"})
public class PrismioParser implements PsiParser, LightPsiParser {

  public ASTNode parse(IElementType t, PsiBuilder b) {
    parseLight(t, b);
    return b.getTreeBuilt();
  }

  public void parseLight(IElementType t, PsiBuilder b) {
    boolean r;
    b = adapt_builder_(t, b, this, null);
    Marker m = enter_section_(b, 0, _COLLAPSE_, null);
    r = parse_root_(t, b);
    exit_section_(b, 0, m, t, r, true, TRUE_CONDITION);
  }

  protected boolean parse_root_(IElementType t, PsiBuilder b) {
    return parse_root_(t, b, 0);
  }

  static boolean parse_root_(IElementType t, PsiBuilder b, int l) {
    return prismioFile(b, l + 1);
  }

  /* ********************************************************** */
  // KEYWORD
  //   | TYPE_KEYWORD
  //   | IDENTIFIER
  //   | OPERATOR
  //   | SEPARATOR
  //   | STRING_LITERAL
  //   | CHARACTER_LITERAL
  //   | BOOLEAN
  //   | MULTILINE_COMMENT
  //   | SINGLE_LINE_COMMENT
  //   | INTEGER
  //   | FLOAT
  static boolean item_(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "item_")) return false;
    boolean r;
    r = consumeToken(b, KEYWORD);
    if (!r) r = consumeToken(b, TYPE_KEYWORD);
    if (!r) r = consumeToken(b, IDENTIFIER);
    if (!r) r = consumeToken(b, OPERATOR);
    if (!r) r = consumeToken(b, SEPARATOR);
    if (!r) r = consumeToken(b, STRING_LITERAL);
    if (!r) r = consumeToken(b, CHARACTER_LITERAL);
    if (!r) r = consumeToken(b, BOOLEAN);
    if (!r) r = consumeToken(b, MULTILINE_COMMENT);
    if (!r) r = consumeToken(b, SINGLE_LINE_COMMENT);
    if (!r) r = consumeToken(b, INTEGER);
    if (!r) r = consumeToken(b, FLOAT);
    return r;
  }

  /* ********************************************************** */
  // item_*
  static boolean prismioFile(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "prismioFile")) return false;
    while (true) {
      int c = current_position_(b);
      if (!item_(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "prismioFile", c)) break;
    }
    return true;
  }

}
