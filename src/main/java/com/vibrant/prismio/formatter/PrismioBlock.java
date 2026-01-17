package com.vibrant.prismio.formatter;

import com.intellij.formatting.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.TokenType;
import com.intellij.psi.codeStyle.CodeStyleSettings;
import com.intellij.psi.formatter.common.AbstractBlock;
import com.intellij.psi.tree.IElementType;
import com.vibrant.prismio.PrismioLanguage;
import com.vibrant.prismio.psi.PrismioTypes;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Enhanced code formatter for Prismio with comprehensive spacing rules
 */
public class PrismioBlock extends AbstractBlock {

  private final CodeStyleSettings settings;
  private final SpacingBuilder spacingBuilder;

  public PrismioBlock(@NotNull ASTNode node,
                      @Nullable Wrap wrap,
                      @Nullable Alignment alignment,
                      CodeStyleSettings settings) {
    super(node, wrap, alignment);
    this.settings = settings;
    this.spacingBuilder = createSpacingBuilder(settings);
  }

  private static SpacingBuilder createSpacingBuilder(CodeStyleSettings settings) {
    return new SpacingBuilder(settings, PrismioLanguage.INSTANCE)
            // Around operators
            .around(PrismioTypes.OPERATOR).spaces(1)

            // After keywords
            .after(PrismioTypes.KEYWORD).spaces(1)
            .after(PrismioTypes.TYPE_KEYWORD).spaces(1)

            // Around separators - general rules
            .before(PrismioTypes.SEPARATOR).spacing(0, 1, 0, false, 0)
            .after(PrismioTypes.SEPARATOR).spacing(0, 1, 0, false, 0)

            // Braces
            .before(PrismioTypes.LBRACE).spaces(1)
            .after(PrismioTypes.LBRACE).lineBreakInCode()
            .before(PrismioTypes.RBRACE).lineBreakInCode()
            .after(PrismioTypes.RBRACE).blankLines(1)

            // Around function declarations (if exists)
            .before(PrismioTypes.FUNCTION_DECL).blankLines(1)
            .after(PrismioTypes.FUNCTION_DECL).blankLines(1)

            // Comments
            .before(PrismioTypes.SINGLE_LINE_COMMENT).lineBreakInCode()
            .after(PrismioTypes.SINGLE_LINE_COMMENT).lineBreakInCode()
            .before(PrismioTypes.MULTILINE_COMMENT).lineBreakInCode()
            .after(PrismioTypes.MULTILINE_COMMENT).lineBreakInCode();
  }

  @Override
  protected List<Block> buildChildren() {
    List<Block> blocks = new ArrayList<>();
    ASTNode child = myNode.getFirstChildNode();

    while (child != null) {
      if (child.getElementType() != TokenType.WHITE_SPACE &&
              child.getTextLength() > 0) {

        Block block = new PrismioBlock(
                child,
                Wrap.createWrap(WrapType.NONE, false),
                createChildAlignment(child),
                settings
        );
        blocks.add(block);
      }
      child = child.getTreeNext();
    }
    return blocks;
  }

  private Alignment createChildAlignment(ASTNode child) {
    // Align parameters in function declarations
    if (isInFunctionParameters(child)) {
      return Alignment.createAlignment();
    }

    // Align struct fields
    if (isInStructDeclaration(child)) {
      return Alignment.createAlignment();
    }

    return null;
  }

  private boolean isInFunctionParameters(ASTNode node) {
    ASTNode parent = node.getTreeParent();
    return parent != null && parent.getElementType() == PrismioTypes.FUNCTION_DECL;
  }

  private boolean isInStructDeclaration(ASTNode node) {
    ASTNode parent = node.getTreeParent();
    while (parent != null) {
      // Check if we're in a block that follows the 'struct' keyword
      IElementType parentType = parent.getElementType();
      if (parentType == PrismioTypes.BLOCK) {
        // Look for 'struct' keyword before this block
        ASTNode prevSibling = parent.getTreePrev();
        while (prevSibling != null) {
          if (prevSibling.getElementType() == PrismioTypes.KEYWORD &&
                  "struct".equals(prevSibling.getText())) {
            return true;
          }
          prevSibling = prevSibling.getTreePrev();
        }
      }
      parent = parent.getTreeParent();
    }
    return false;
  }

  @Override
  public Indent getIndent() {
    IElementType parentType = myNode.getTreeParent() != null ?
            myNode.getTreeParent().getElementType() : null;

    // Indent content inside blocks
    if (parentType == PrismioTypes.BLOCK ||
            parentType == PrismioTypes.FUNCTION_DECL) {

      // Don't indent braces themselves
      if (myNode.getElementType() == PrismioTypes.LBRACE ||
              myNode.getElementType() == PrismioTypes.RBRACE) {
        return Indent.getNoneIndent();
      }

      return Indent.getNormalIndent();
    }

    return Indent.getNoneIndent();
  }

  @Nullable
  @Override
  public Spacing getSpacing(@Nullable Block child1, @NotNull Block child2) {
    if (child1 instanceof PrismioBlock && child2 instanceof PrismioBlock) {
      return spacingBuilder.getSpacing(this, child1, child2);
    }
    return null;
  }

  @Override
  public boolean isLeaf() {
    return myNode.getFirstChildNode() == null;
  }

  @NotNull
  @Override
  public ChildAttributes getChildAttributes(int newChildIndex) {
    IElementType type = myNode.getElementType();

    // Indent children in blocks
    if (type == PrismioTypes.BLOCK ||
            type == PrismioTypes.FUNCTION_DECL) {
      return new ChildAttributes(Indent.getNormalIndent(), null);
    }

    return new ChildAttributes(Indent.getNoneIndent(), null);
  }
}