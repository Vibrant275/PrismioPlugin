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
        if (parent == null)
            return false;

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

        // First, check if this is a function declaration (identifier after 'fn')
        // If so, don't mark it as a function call
        PsiElement prevSibling = element.getPrevSibling();
        while (prevSibling != null && isWhitespaceElement(prevSibling)) {
            prevSibling = prevSibling.getPrevSibling();
        }
        if (prevSibling != null &&
                prevSibling.getNode().getElementType() == PrismioTypes.KEYWORD &&
                "fn".equals(prevSibling.getText())) {
            return false; // This is a function declaration, not a call
        }

        // Also check for 'extern' fn declarations
        if (prevSibling != null &&
                prevSibling.getNode().getElementType() == PrismioTypes.KEYWORD &&
                "extern".equals(prevSibling.getText())) {
            return false; // This is an extern function declaration
        }

        // Check if next non-whitespace token is '('
        PsiElement nextSibling = element.getNextSibling();
        while (nextSibling != null && isWhitespaceElement(nextSibling)) {
            nextSibling = nextSibling.getNextSibling();
        }

        if (nextSibling == null) {
            return false;
        }

        // Check for opening parenthesis
        IElementType nextType = nextSibling.getNode().getElementType();
        if (nextType == PrismioTypes.LPAREN) {
            return true;
        }

        // Also check the text directly in case token type doesn't match
        String nextText = nextSibling.getText();
        if (nextText != null && nextText.startsWith("(")) {
            return true;
        }

        return false;
    }

    /**
     * Check if an element is whitespace (either empty text or WHITE_SPACE token
     * type)
     */
    private boolean isWhitespaceElement(PsiElement element) {
        if (element == null)
            return false;

        // Check token type
        IElementType type = element.getNode().getElementType();
        if (type == com.intellij.psi.TokenType.WHITE_SPACE) {
            return true;
        }

        // Also check if text is only whitespace
        String text = element.getText();
        return text != null && text.trim().isEmpty();
    }

    private boolean isParameter(@NotNull PsiElement element) {
        if (element.getNode().getElementType() != PrismioTypes.IDENTIFIER) {
            return false;
        }

        PsiElement parent = element.getParent();
        if (parent == null)
            return false;

        PsiElement nextSibling = element.getNextSibling();
        while (nextSibling != null && nextSibling.getText().trim().isEmpty()) {
            nextSibling = nextSibling.getNextSibling();
        }

        if (nextSibling == null) {
            return false;
        }

        IElementType nextType = nextSibling.getNode().getElementType();
        return (nextType == PrismioTypes.COLON ||
                (nextType == PrismioTypes.SEPARATOR && ":".equals(nextSibling.getText())));
    }

    private boolean isInStructDeclaration(@NotNull PsiElement element) {
        PsiElement parent = element.getParent();
        if (parent == null)
            return false;

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
        if (parent == null)
            return false;

        PsiElement prevSibling = element.getPrevSibling();
        while (prevSibling != null && prevSibling.getText().trim().isEmpty()) {
            prevSibling = prevSibling.getPrevSibling();
        }

        return prevSibling != null &&
                prevSibling.getNode().getElementType() == PrismioTypes.KEYWORD &&
                "enum".equals(prevSibling.getText());
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