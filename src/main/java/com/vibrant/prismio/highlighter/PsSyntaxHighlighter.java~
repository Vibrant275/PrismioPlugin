package com.vibrant.prismio.highlighter;

import com.intellij.lexer.Lexer;
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors;
import com.intellij.openapi.editor.HighlighterColors;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase;
import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IElementType;
import com.vibrant.prismio.lexer.LexerAdapter;
import com.vibrant.prismio.psi.PrismioTypes;
import org.jetbrains.annotations.NotNull;

import static com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey;

public class PsSyntaxHighlighter extends SyntaxHighlighterBase {

  public static final TextAttributesKey SEPARATOR =
      createTextAttributesKey("PS_SEPARATOR", DefaultLanguageHighlighterColors.OPERATION_SIGN);

  public static final TextAttributesKey KEYWORD =
      createTextAttributesKey("PS_KEYWORD", DefaultLanguageHighlighterColors.KEYWORD);

  public static final TextAttributesKey VALUE =
      createTextAttributesKey("PS_VALUE", DefaultLanguageHighlighterColors.STRING);
  public static final TextAttributesKey LINE_COMMENT =
      createTextAttributesKey("PS_LINE_COMMENT", DefaultLanguageHighlighterColors.LINE_COMMENT);
  public static final TextAttributesKey BLOCK_COMMENT =
      createTextAttributesKey("PS_BLOCK_COMMENT", DefaultLanguageHighlighterColors.BLOCK_COMMENT);
  public static final TextAttributesKey BAD_CHARACTER =
      createTextAttributesKey("PS_BAD_CHARACTER", HighlighterColors.BAD_CHARACTER);

  public static final TextAttributesKey NUMBER =
      createTextAttributesKey("PS_NUMBER", DefaultLanguageHighlighterColors.NUMBER);


  private static final TextAttributesKey[] BAD_CHAR_KEYS = new TextAttributesKey[]{BAD_CHARACTER};
  private static final TextAttributesKey[] SEPARATOR_KEYS = new TextAttributesKey[]{SEPARATOR};
  private static final TextAttributesKey[] KEYWORDS_KEYS = new TextAttributesKey[]{KEYWORD};
  private static final TextAttributesKey[] VALUE_KEYS = new TextAttributesKey[]{VALUE};
  private static final TextAttributesKey[] LINE_COMMENT_KEYS = new TextAttributesKey[]{LINE_COMMENT};
  private static final TextAttributesKey[] NUMBER_KEYS = new TextAttributesKey[]{NUMBER};

  private static final TextAttributesKey[] BLOCK_COMMENT_KEYS = new TextAttributesKey[]{BLOCK_COMMENT};
  private static final TextAttributesKey[] EMPTY_KEYS = new TextAttributesKey[0];

  @NotNull
  @Override
  public Lexer getHighlightingLexer() {
    return new LexerAdapter();
  }

  @Override
  public TextAttributesKey @NotNull [] getTokenHighlights(IElementType tokenType) {
    if (tokenType.equals(PrismioTypes.SEPARATOR)) {
      return SEPARATOR_KEYS;
    }
    if (tokenType.equals(PrismioTypes.KEYWORD)) {
      return KEYWORDS_KEYS;
    }
    if (tokenType.equals(PrismioTypes.VALUE)) {
      return VALUE_KEYS;
    }
    if (tokenType.equals(PrismioTypes.SINGLE_LINE_COMMENT)) {
      return LINE_COMMENT_KEYS;
    }
    if (tokenType.equals(PrismioTypes.MULTILINE_COMMENT)) {
      return BLOCK_COMMENT_KEYS;
    }
    if (tokenType.equals(TokenType.BAD_CHARACTER)) {
      return BAD_CHAR_KEYS;
    }
    if (tokenType.equals(PrismioTypes.INTEGER) || tokenType.equals(PrismioTypes.FLOAT)) {
      return NUMBER_KEYS;
    }
    return EMPTY_KEYS;
  }

}
