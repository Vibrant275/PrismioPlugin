package com.vibrant.prismio;

import com.intellij.psi.tree.IElementType;
import com.vibrant.prismio.psi.PrismioTypes;
import com.intellij.psi.TokenType;
import com.intellij.lexer.FlexLexer;

%%

/* JFlex Settings */

%public
%class PsLexer
%implements FlexLexer
%unicode
%function advance
%type IElementType
%eof{  return;
%eof}

// Lexer rules
CRLF=\R
WHITE_SPACE=[\ \n\t\f]
FIRST_VALUE_CHARACTER=[^ \n\f\\] | "\\"{CRLF} | "\\".
VALUE_CHARACTER=[^\n\f\\] | "\\"{CRLF} | "\\".

SEPARATOR=[:=]
KEY_CHARACTER=[^:=\ \n\t\f\\] | "\\ "

/* Updated Patterns */
SINGLE_LINE_COMMENT=("//")[^\r\n]*
MULTILINE_COMMENT="/*" [^*] ~"*/" | "/*" "*"+ "/"*

STRING_LITERAL="\"([^\"\\]|\\.)*\""
CHARACTER_LITERAL="'([^'\\]|\\.)'"

KEYWORD=(
"if"|"else"|"while"|"for"|"break"|"continue"|"return" |
 "void" | "Int" | "Char" | "Bool" | "String" | "Float" | "class" | "enum" | "import"
  )

BOOLEAN=("true"|"false")
IDENTIFIER=([a-zA-Z_][a-zA-Z0-9_]*)
OPERATOR=("="|"<"|">"|"<="|">="|"=="|"!="|"+"|"-"|"*"|"/"|"%"|"!"|"&"|"|"|"^"|"~")

// States
%state WAITING_VALUE

%%

<YYINITIAL> {SINGLE_LINE_COMMENT} { yybegin(YYINITIAL); return PrismioTypes.SINGLE_LINE_COMMENT; }

<YYINITIAL> {MULTILINE_COMMENT} { yybegin(YYINITIAL); return PrismioTypes.MULTILINE_COMMENT; }
<YYINITIAL> {KEYWORD} { yybegin(YYINITIAL); return PrismioTypes.KEYWORD; }
<YYINITIAL> {BOOLEAN} { yybegin(YYINITIAL); return PrismioTypes.BOOLEAN; }
<YYINITIAL> {IDENTIFIER} { yybegin(YYINITIAL); return PrismioTypes.IDENTIFIER; }
<YYINITIAL> {OPERATOR} { yybegin(YYINITIAL); return PrismioTypes.OPERATOR; }
<YYINITIAL> {STRING_LITERAL} { yybegin(YYINITIAL); return PrismioTypes.STRING_LITERAL; }
<YYINITIAL> {CHARACTER_LITERAL} { yybegin(YYINITIAL); return PrismioTypes.CHARACTER_LITERAL; }
<YYINITIAL> {SEPARATOR} { yybegin(WAITING_VALUE); return PrismioTypes.SEPARATOR; }
<YYINITIAL> {CRLF} { yybegin(YYINITIAL); return TokenType.WHITE_SPACE; }



/* Waiting Value State Rules */
<WAITING_VALUE> {CRLF}({CRLF}|{WHITE_SPACE})+               { yybegin(YYINITIAL); return TokenType.WHITE_SPACE; }
<WAITING_VALUE> {WHITE_SPACE}+                              { yybegin(WAITING_VALUE); return TokenType.WHITE_SPACE; }
<WAITING_VALUE> {FIRST_VALUE_CHARACTER}{VALUE_CHARACTER}*   { yybegin(YYINITIAL); return PrismioTypes.VALUE; }

/* General White Space Handling */
({CRLF}|{WHITE_SPACE})+                                     { yybegin(YYINITIAL); return TokenType.WHITE_SPACE; }

/* Error Handling */
[^]                                                         { return TokenType.BAD_CHARACTER; }
