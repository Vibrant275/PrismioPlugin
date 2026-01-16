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
WHITE_SPACE=[\ \n\t\f\r]
SINGLE_LINE_COMMENT=("//")[^\r\n]*
MULTILINE_COMMENT="/*" [^*] ~"*/" | "/*" "*"+ "/"

// Literals
STRING_LITERAL=\"([^\"\\]|\\.)*\"
CHARACTER_LITERAL='([^'\\]|\\.)'
INTEGER=-?[0-9]+
FLOAT=-?[0-9]*\.[0-9]+([eE][+-]?[0-9]+)?
BOOLEAN="true"|"false"

// Keywords - exact matches
KEYWORD="fn"|"let"|"mut"|"if"|"else"|"while"|"for"|"return"|"struct"|"enum"|"trait"|"impl"|"extern"|"import"|"in"|"loop"|"match"|"break"|"continue"

// Type keywords
TYPE_KEYWORD="Int"|"Bool"|"Char"|"String"|"Float"

// Identifiers
IDENTIFIER=[a-zA-Z_][a-zA-Z0-9_]*

// Operators and separators
OPERATOR="<="|">="|"=="|"!="|"+="|"-="|"*="|"/="|"%="|"++"|"--"|"->"|"=>"|"&&"|"||"|[+\-*/%<>=!&|]
SEPARATOR=[()[\],:;.]]

%%

<YYINITIAL> {
  {SINGLE_LINE_COMMENT}     { return PrismioTypes.SINGLE_LINE_COMMENT; }
  {MULTILINE_COMMENT}       { return PrismioTypes.MULTILINE_COMMENT; }

  {KEYWORD}                 { return PrismioTypes.KEYWORD; }
  {TYPE_KEYWORD}            { return PrismioTypes.TYPE_KEYWORD; }
  {BOOLEAN}                 { return PrismioTypes.BOOLEAN; }

  {STRING_LITERAL}          { return PrismioTypes.STRING_LITERAL; }
  {CHARACTER_LITERAL}       { return PrismioTypes.CHARACTER_LITERAL; }
  {FLOAT}                   { return PrismioTypes.FLOAT; }
  {INTEGER}                 { return PrismioTypes.INTEGER; }

  {OPERATOR}                { return PrismioTypes.OPERATOR; }
  {SEPARATOR}               { return PrismioTypes.SEPARATOR; }

  {IDENTIFIER}              { return PrismioTypes.IDENTIFIER; }

  {WHITE_SPACE}             { return TokenType.WHITE_SPACE; }
  {CRLF}                    { return TokenType.WHITE_SPACE; }
      "{" { return PrismioTypes.LBRACE; }
      "}" { return PrismioTypes.RBRACE; }
}

[^]                         { return TokenType.BAD_CHARACTER; }