package com.vibrant.prismio;

import com.intellij.formatting.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.TokenType;
import com.intellij.psi.codeStyle.CodeStyleSettings;
import com.intellij.psi.formatter.common.AbstractBlock;
import com.intellij.psi.tree.IElementType;
import com.vibrant.prismio.psi.PrismioTypes;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SimpleBlock extends AbstractBlock {

  private final CodeStyleSettings mySettings;

  protected SimpleBlock(@NotNull ASTNode node, @Nullable Wrap wrap, @Nullable Alignment alignment,
                        CodeStyleSettings settings) {
    super(node, wrap, alignment);
    this.mySettings = settings;
  }

  @Override
  protected List<Block> buildChildren() {
    List<Block> blocks = new ArrayList<>();
    ASTNode child = myNode.getFirstChildNode();

    while (child != null) {
      if (child.getElementType() != TokenType.WHITE_SPACE && child.getTextLength() > 0) {
        Block block = new SimpleBlock(
                child,
                Wrap.createWrap(WrapType.NONE, false),
                Alignment.createAlignment(),
                mySettings
        );
        blocks.add(block);
      }
      child = child.getTreeNext();
    }
    return blocks;
  }

  @Override
  public Indent getIndent() {
    ASTNode parent = myNode.getTreeParent();
    if (parent != null && parent.getElementType() == PrismioTypes.BLOCK) {
      return Indent.getNormalIndent();
    }
    return Indent.getNoneIndent();
  }


  @Nullable
  @Override
  public Spacing getSpacing(@Nullable Block child1, @NotNull Block child2) {
    if (!(child1 instanceof SimpleBlock) || !(child2 instanceof SimpleBlock)) {
      return null;
    }

    ASTNode node1 = ((SimpleBlock) child1).getNode();
    ASTNode node2 = ((SimpleBlock) child2).getNode();

    IElementType type1 = node1.getElementType();
    IElementType type2 = node2.getElementType();

    String text1 = node1.getText();
    String text2 = node2.getText();

    // Line break after opening brace - MUST come first
    if (type1 == PrismioTypes.SEPARATOR && "{".equals(text1)) {
      // Check if next node is not immediately a closing brace
      if (!"}".equals(text2)) {
        return Spacing.createSpacing(0, 0, 1, true, 1);
      }
    }

    // Line break before closing brace - MUST come early
    if (type2 == PrismioTypes.SEPARATOR && "}".equals(text2)) {
      return Spacing.createSpacing(0, 0, 1, true, 1);
    }

    // Space after keywords (fn, let, mut, if, else, while, return, etc.)
    if (type1 == PrismioTypes.KEYWORD) {
      return Spacing.createSpacing(1, 1, 0, true, 0);
    }

    // Space after type keywords (Int, String, Bool, etc.)
    if (type1 == PrismioTypes.TYPE_KEYWORD) {
      return Spacing.createSpacing(1, 1, 0, true, 0);
    }

    // Space around operators (=, +, -, *, /, ==, !=, <=, >=, etc.)
    if (type1 == PrismioTypes.OPERATOR || type2 == PrismioTypes.OPERATOR) {
      return Spacing.createSpacing(1, 1, 0, true, 0);
    }

    // Separator-specific spacing rules
    if (type1 == PrismioTypes.SEPARATOR || type2 == PrismioTypes.SEPARATOR) {

      // Space after comma
      if (",".equals(text1)) {
        return Spacing.createSpacing(1, 1, 0, true, 0);
      }

      // Space after colon (for type annotations like n: Int)
      if (":".equals(text1)) {
        return Spacing.createSpacing(1, 1, 0, true, 0);
      }

      // No space before comma, semicolon, colon
      if (",".equals(text2) || ";".equals(text2) || ":".equals(text2)) {
        return Spacing.createSpacing(0, 0, 0, false, 0);
      }

      // Space before opening brace (for function/control flow blocks)
      if ("{".equals(text2)) {
        return Spacing.createSpacing(1, 1, 0, true, 0);
      }

      // No space after opening parenthesis/bracket/brace
      if ("(".equals(text1) || "[".equals(text1)) {
        return Spacing.createSpacing(0, 0, 0, false, 0);
      }

      // No space before closing parenthesis/bracket/brace
      if (")".equals(text2) || "]".equals(text2)) {
        return Spacing.createSpacing(0, 0, 0, false, 0);
      }

      // No space before opening parenthesis (for function calls)
      if ("(".equals(text2) && type1 == PrismioTypes.IDENTIFIER) {
        return Spacing.createSpacing(0, 0, 0, false, 0);
      }
    }

    // Default: single space with line break preservation
    return Spacing.createSpacing(0, 1, 0, true, 0);
  }

  @Override
  public @Nullable Alignment getAlignment() {
    // Align elements at the same level
    return myAlignment;
  }

  @Override
  public boolean isLeaf() {
    return myNode.getFirstChildNode() == null;
  }

  @Override
  public @NotNull ChildAttributes getChildAttributes(int newChildIndex) {
    if (myNode.getElementType() == PrismioTypes.BLOCK) {
      return new ChildAttributes(Indent.getNormalIndent(), null);
    }
    return new ChildAttributes(Indent.getNoneIndent(), null);
  }

}