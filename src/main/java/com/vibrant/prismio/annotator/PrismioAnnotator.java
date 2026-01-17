package com.vibrant.prismio.annotator;

import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.lang.annotation.HighlightSeverity;
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.psi.PsiElement;
import com.intellij.psi.tree.IElementType;
import com.vibrant.prismio.highlighter.PsSyntaxHighlighter;
import com.vibrant.prismio.psi.PrismioTypes;
import org.jetbrains.annotations.NotNull;

/**
 * Semantic annotator for Prismio
 * Provides context-aware highlighting beyond lexical analysis
 */
public class PrismioAnnotator implements Annotator {

    // Constants for compatibility with old code
    public static final String PS_PREFIX_STR = "psm";
    public static final String PS_SEPARATOR_STR = ":";

    @Override
    public void annotate(@NotNull PsiElement element, @NotNull AnnotationHolder holder) {
        IElementType elementType = element.getNode().getElementType();

        // Highlight function names in declarations
        if (isInFunctionDeclaration(element)) {
            if (elementType == PrismioTypes.IDENTIFIER) {
                highlightElement(element, holder, PsSyntaxHighlighter.FUNCTION_DECLARATION);
                return;
            }
        }

        // Highlight function calls
        if (isFunctionCall(element)) {
            highlightElement(element, holder, PsSyntaxHighlighter.FUNCTION_CALL);
            return;
        }

        // Highlight struct names
        if (isInStructDeclaration(element)) {
            if (elementType == PrismioTypes.IDENTIFIER) {
                highlightElement(element, holder, PsSyntaxHighlighter.STRUCT_NAME);
                return;
            }
        }

        // Highlight enum names
        if (isInEnumDeclaration(element)) {
            if (elementType == PrismioTypes.IDENTIFIER) {
                highlightElement(element, holder, PsSyntaxHighlighter.ENUM_NAME);
                return;
            }
        }

        // Highlight parameters
        if (isParameter(element)) {
            highlightElement(element, holder, PsSyntaxHighlighter.PARAMETER);
            return;
        }

        // Check for common mistakes
        checkMutableWithoutAssignment(element, holder);
        checkUnusedVariables(element, holder);
        checkMissingReturnType(element, holder);
    }

    private void highlightElement(@NotNull PsiElement element,
                                  @NotNull AnnotationHolder holder,
                                  @NotNull TextAttributesKey key) {
        holder.newSilentAnnotation(HighlightSeverity.INFORMATION)
                .range(element.getTextRange())
                .textAttributes(key)
                .create();
    }

    private boolean isInFunctionDeclaration(@NotNull PsiElement element) {
        PsiElement parent = element.getParent();
        if (parent == null) return false;

        // Check if previous sibling is 'fn' keyword
        PsiElement prevSibling = element.getPrevSibling();
        while (prevSibling != null && prevSibling.getText().trim().isEmpty()) {
            prevSibling = prevSibling.getPrevSibling();
        }

        return prevSibling != null &&
                prevSibling.getNode().getElementType() == PrismioTypes.KEYWORD &&
                "fn".equals(prevSibling.getText());
    }

    private boolean isFunctionCall(@NotNull PsiElement element) {
        if (element.getNode().getElementType() != PrismioTypes.IDENTIFIER) {
            return false;
        }

        PsiElement nextSibling = element.getNextSibling();
        while (nextSibling != null && nextSibling.getText().trim().isEmpty()) {
            nextSibling = nextSibling.getNextSibling();
        }

        return nextSibling != null &&
                nextSibling.getNode().getElementType() == PrismioTypes.SEPARATOR &&
                "(".equals(nextSibling.getText());
    }

    private boolean isInStructDeclaration(@NotNull PsiElement element) {
        PsiElement parent = element.getParent();
        if (parent == null) return false;

        PsiElement prevSibling = element.getPrevSibling();
        while (prevSibling != null && prevSibling.getText().trim().isEmpty()) {
            prevSibling = prevSibling.getPrevSibling();
        }

        return prevSibling != null &&
                prevSibling.getNode().getElementType() == PrismioTypes.KEYWORD &&
                "struct".equals(prevSibling.getText());
    }

    private boolean isInEnumDeclaration(@NotNull PsiElement element) {
        PsiElement parent = element.getParent();
        if (parent == null) return false;

        PsiElement prevSibling = element.getPrevSibling();
        while (prevSibling != null && prevSibling.getText().trim().isEmpty()) {
            prevSibling = prevSibling.getPrevSibling();
        }

        return prevSibling != null &&
                prevSibling.getNode().getElementType() == PrismioTypes.KEYWORD &&
                "enum".equals(prevSibling.getText());
    }

    private boolean isParameter(@NotNull PsiElement element) {
        if (element.getNode().getElementType() != PrismioTypes.IDENTIFIER) {
            return false;
        }

        // Check if inside parameter list (between parentheses after function name)
        PsiElement parent = element.getParent();
        if (parent == null) return false;

        PsiElement nextSibling = element.getNextSibling();
        while (nextSibling != null && nextSibling.getText().trim().isEmpty()) {
            nextSibling = nextSibling.getNextSibling();
        }

        // Parameter if followed by colon (type annotation)
        return nextSibling != null &&
                nextSibling.getNode().getElementType() == PrismioTypes.SEPARATOR &&
                ":".equals(nextSibling.getText());
    }

    private void checkMutableWithoutAssignment(@NotNull PsiElement element,
                                               @NotNull AnnotationHolder holder) {
        // Check for 'let mut' declarations without subsequent assignments
        if (element.getNode().getElementType() == PrismioTypes.KEYWORD &&
                "mut".equals(element.getText())) {

            // This is a simplified check - in a full implementation,
            // you'd track variable usage throughout the scope
            holder.newAnnotation(HighlightSeverity.WEAK_WARNING,
                            "Mutable variable may not be reassigned")
                    .range(element.getTextRange())
                    .create();
        }
    }

    private void checkUnusedVariables(@NotNull PsiElement element,
                                      @NotNull AnnotationHolder holder) {
        // Simplified unused variable check
        // In a full implementation, track all variable declarations and usages
    }

    private void checkMissingReturnType(@NotNull PsiElement element,
                                        @NotNull AnnotationHolder holder) {
        // Check for functions that might need explicit return types
        if (element.getNode().getElementType() == PrismioTypes.KEYWORD &&
                "fn".equals(element.getText())) {

            // Look for arrow (->) indicating return type
            PsiElement current = element.getNextSibling();
            boolean hasReturnType = false;
            boolean foundBody = false;

            while (current != null && !foundBody) {
                String text = current.getText().trim();
                if ("->".equals(text)) {
                    hasReturnType = true;
                    break;
                }
                if ("{".equals(text)) {
                    foundBody = true;
                    break;
                }
                current = current.getNextSibling();
            }

            // This is just a hint, not an error
            if (!hasReturnType && foundBody) {
                holder.newAnnotation(HighlightSeverity.INFORMATION,
                                "Consider adding explicit return type")
                        .range(element.getTextRange())
                        .create();
            }
        }
    }
}