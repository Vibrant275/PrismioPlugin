package com.vibrant.prismio;

import com.intellij.lang.ASTNode;
import com.intellij.lang.ParserDefinition;
import com.intellij.lang.PsiParser;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.project.Project;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.tree.IFileElementType;
import com.intellij.psi.tree.TokenSet;
import com.vibrant.prismio.lexer.LexerAdapter;
import com.vibrant.prismio.parser.PrismioParser;
import com.vibrant.prismio.psi.PrismioTypes;
import com.vibrant.prismio.psi.PrismioFile;
import com.vibrant.prismio.psi.PrismioTokenSets;
import org.jetbrains.annotations.NotNull;

final class SimpleParserDefinition implements ParserDefinition {

  public static final IFileElementType FILE = new IFileElementType(PrismioLanguage.INSTANCE);

  @NotNull
  @Override
  public Lexer createLexer(Project project) {
    return new LexerAdapter();
  }

  @NotNull
  @Override
  public TokenSet getCommentTokens() {
    return PrismioTokenSets.COMMENTS;
  }

  @NotNull
  @Override
  public TokenSet getStringLiteralElements() {
    return TokenSet.create(PrismioTypes.STRING_LITERAL, PrismioTypes.CHARACTER_LITERAL);
  }

  @NotNull
  @Override
  public PsiParser createParser(final Project project) {
    return new PrismioParser();
  }

  @NotNull
  @Override
  public IFileElementType getFileNodeType() {
    return FILE;
  }

  @NotNull
  @Override
  public PsiFile createFile(@NotNull FileViewProvider viewProvider) {
    return new PrismioFile(viewProvider);
  }

  @NotNull
  @Override
  public PsiElement createElement(ASTNode node) {
    // For simplified highlighting, we don't create complex PSI elements
    // Just return basic wrapper
    return new com.intellij.extapi.psi.ASTWrapperPsiElement(node);
  }
}