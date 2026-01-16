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

  // Define color keys for different token types
  public static final TextAttributesKey KEYWORD =
          createTextAttributesKey("PRISMIO_KEYWORD", DefaultLanguageHighlighterColors.KEYWORD);

  public static final TextAttributesKey TYPE_KEYWORD =
          createTextAttributesKey("PRISMIO_TYPE", DefaultLanguageHighlighterColors.CLASS_NAME);

  public static final TextAttributesKey STRING =
          createTextAttributesKey("PRISMIO_STRING", DefaultLanguageHighlighterColors.STRING);

  public static final TextAttributesKey NUMBER =
          createTextAttributesKey("PRISMIO_NUMBER", DefaultLanguageHighlighterColors.NUMBER);

  public static final TextAttributesKey OPERATOR =
          createTextAttributesKey("PRISMIO_OPERATOR", DefaultLanguageHighlighterColors.OPERATION_SIGN);

  public static final TextAttributesKey SEPARATOR =
          createTextAttributesKey("PRISMIO_SEPARATOR", DefaultLanguageHighlighterColors.BRACES);

  public static final TextAttributesKey LINE_COMMENT =
          createTextAttributesKey("PRISMIO_LINE_COMMENT", DefaultLanguageHighlighterColors.LINE_COMMENT);

  public static final TextAttributesKey BLOCK_COMMENT =
          createTextAttributesKey("PRISMIO_BLOCK_COMMENT", DefaultLanguageHighlighterColors.BLOCK_COMMENT);

  public static final TextAttributesKey IDENTIFIER =
          createTextAttributesKey("PRISMIO_IDENTIFIER", DefaultLanguageHighlighterColors.IDENTIFIER);

  public static final TextAttributesKey FUNCTION =
          createTextAttributesKey("PRISMIO_FUNCTION", DefaultLanguageHighlighterColors.FUNCTION_DECLARATION);

  public static final TextAttributesKey TEXT_CHAR =
          createTextAttributesKey("PRISMIO_TEXT_CHAR", DefaultLanguageHighlighterColors.STRING);

  public static final TextAttributesKey BAD_CHARACTER =
          createTextAttributesKey("PRISMIO_BAD_CHARACTER", HighlighterColors.BAD_CHARACTER);

  // Key arrays for faster lookup
  private static final TextAttributesKey[] KEYWORD_KEYS = new TextAttributesKey[]{KEYWORD};
  private static final TextAttributesKey[] TYPE_KEYS = new TextAttributesKey[]{TYPE_KEYWORD};
  private static final TextAttributesKey[] STRING_KEYS = new TextAttributesKey[]{STRING};
  private static final TextAttributesKey[] NUMBER_KEYS = new TextAttributesKey[]{NUMBER};
  private static final TextAttributesKey[] OPERATOR_KEYS = new TextAttributesKey[]{OPERATOR};
  private static final TextAttributesKey[] SEPARATOR_KEYS = new TextAttributesKey[]{SEPARATOR};
  private static final TextAttributesKey[] LINE_COMMENT_KEYS = new TextAttributesKey[]{LINE_COMMENT};
  private static final TextAttributesKey[] BLOCK_COMMENT_KEYS = new TextAttributesKey[]{BLOCK_COMMENT};
  private static final TextAttributesKey[] IDENTIFIER_KEYS = new TextAttributesKey[]{IDENTIFIER};
  private static final TextAttributesKey[] BAD_CHAR_KEYS = new TextAttributesKey[]{BAD_CHARACTER};
  private static final TextAttributesKey[] EMPTY_KEYS = new TextAttributesKey[0];

  @NotNull
  @Override
  public Lexer getHighlightingLexer() {
    return new LexerAdapter();
  }

  @Override
  public TextAttributesKey @NotNull [] getTokenHighlights(IElementType tokenType) {
    if (tokenType.equals(PrismioTypes.KEYWORD)) {
      return KEYWORD_KEYS;
    }
    if (tokenType.equals(PrismioTypes.TYPE_KEYWORD)) {
      return TYPE_KEYS;
    }
    if (tokenType.equals(PrismioTypes.STRING_LITERAL)) {
      return STRING_KEYS;
    }
    if (tokenType.equals(PrismioTypes.CHARACTER_LITERAL)) {
      return STRING_KEYS;
    }
    if (tokenType.equals(PrismioTypes.INTEGER) || tokenType.equals(PrismioTypes.FLOAT)) {
      return NUMBER_KEYS;
    }
    if (tokenType.equals(PrismioTypes.BOOLEAN)) {
      return NUMBER_KEYS; // Use number color for boolean literals
    }
    if (tokenType.equals(PrismioTypes.OPERATOR)) {
      return OPERATOR_KEYS;
    }
    if (tokenType.equals(PrismioTypes.SEPARATOR)) {
      return SEPARATOR_KEYS;
    }
    if (tokenType.equals(PrismioTypes.SINGLE_LINE_COMMENT)) {
      return LINE_COMMENT_KEYS;
    }
    if (tokenType.equals(PrismioTypes.MULTILINE_COMMENT)) {
      return BLOCK_COMMENT_KEYS;
    }
    if (tokenType.equals(PrismioTypes.IDENTIFIER)) {
      return IDENTIFIER_KEYS;
    }
    if (tokenType.equals(TokenType.BAD_CHARACTER)) {
      return BAD_CHAR_KEYS;
    }
    return EMPTY_KEYS;
  }
}