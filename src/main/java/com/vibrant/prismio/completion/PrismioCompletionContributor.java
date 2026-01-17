package com.vibrant.prismio.completion;

import com.intellij.codeInsight.completion.*;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.util.ProcessingContext;
import com.vibrant.prismio.utils.Icons;
import org.jetbrains.annotations.NotNull;

/**
 * Advanced code completion for Prismio language
 * Provides context-aware suggestions for keywords, types, and common patterns
 */
public class PrismioCompletionContributor extends CompletionContributor {

    public PrismioCompletionContributor() {
        // Keyword completion
        extend(CompletionType.BASIC,
                PlatformPatterns.psiElement(),
                new CompletionProvider<CompletionParameters>() {
                    @Override
                    protected void addCompletions(@NotNull CompletionParameters parameters,
                                                  @NotNull ProcessingContext context,
                                                  @NotNull CompletionResultSet result) {
                        // Control flow keywords
                        result.addElement(LookupElementBuilder.create("if")
                                .withTypeText("control flow")
                                .withInsertHandler(new ParenthesisInsertHandler())
                                .bold());

                        result.addElement(LookupElementBuilder.create("else")
                                .withTypeText("control flow")
                                .bold());

                        result.addElement(LookupElementBuilder.create("while")
                                .withTypeText("loop")
                                .withInsertHandler(new ParenthesisInsertHandler())
                                .bold());

                        result.addElement(LookupElementBuilder.create("for")
                                .withTypeText("loop")
                                .withInsertHandler(new ParenthesisInsertHandler())
                                .bold());

                        result.addElement(LookupElementBuilder.create("loop")
                                .withTypeText("infinite loop")
                                .withInsertHandler(new BraceInsertHandler())
                                .bold());

                        result.addElement(LookupElementBuilder.create("match")
                                .withTypeText("pattern matching")
                                .withInsertHandler(new BraceInsertHandler())
                                .bold());

                        // Declaration keywords
                        result.addElement(LookupElementBuilder.create("let")
                                .withTypeText("variable")
                                .bold());

                        result.addElement(LookupElementBuilder.create("let mut")
                                .withTypeText("mutable variable")
                                .bold());

                        result.addElement(LookupElementBuilder.create("fn")
                                .withTypeText("function")
                                .withInsertHandler(new FunctionInsertHandler())
                                .bold());

                        result.addElement(LookupElementBuilder.create("struct")
                                .withTypeText("structure")
                                .withInsertHandler(new BraceInsertHandler())
                                .bold());

                        result.addElement(LookupElementBuilder.create("enum")
                                .withTypeText("enumeration")
                                .withInsertHandler(new BraceInsertHandler())
                                .bold());

                        result.addElement(LookupElementBuilder.create("trait")
                                .withTypeText("trait")
                                .withInsertHandler(new BraceInsertHandler())
                                .bold());

                        result.addElement(LookupElementBuilder.create("impl")
                                .withTypeText("implementation")
                                .withInsertHandler(new BraceInsertHandler())
                                .bold());

                        // Other keywords
                        result.addElement(LookupElementBuilder.create("return")
                                .withTypeText("statement")
                                .bold());

                        result.addElement(LookupElementBuilder.create("break")
                                .withTypeText("statement")
                                .bold());

                        result.addElement(LookupElementBuilder.create("continue")
                                .withTypeText("statement")
                                .bold());

                        result.addElement(LookupElementBuilder.create("extern")
                                .withTypeText("external declaration")
                                .bold());

                        result.addElement(LookupElementBuilder.create("import")
                                .withTypeText("import")
                                .bold());

                        // Type keywords
                        result.addElement(LookupElementBuilder.create("Int")
                                .withTypeText("primitive type")
                                .withIcon(Icons.FILE));

                        result.addElement(LookupElementBuilder.create("String")
                                .withTypeText("primitive type")
                                .withIcon(Icons.FILE));

                        result.addElement(LookupElementBuilder.create("Bool")
                                .withTypeText("primitive type")
                                .withIcon(Icons.FILE));

                        result.addElement(LookupElementBuilder.create("Char")
                                .withTypeText("primitive type")
                                .withIcon(Icons.FILE));

                        result.addElement(LookupElementBuilder.create("Float")
                                .withTypeText("primitive type")
                                .withIcon(Icons.FILE));

                        // Boolean literals
                        result.addElement(LookupElementBuilder.create("true")
                                .withTypeText("boolean")
                                .bold());

                        result.addElement(LookupElementBuilder.create("false")
                                .withTypeText("boolean")
                                .bold());

                        // Common functions
                        result.addElement(LookupElementBuilder.create("println")
                                .withTypeText("built-in function")
                                .withInsertHandler(new ParenthesisInsertHandler())
                                .withIcon(Icons.FILE));

                        result.addElement(LookupElementBuilder.create("print")
                                .withTypeText("built-in function")
                                .withInsertHandler(new ParenthesisInsertHandler())
                                .withIcon(Icons.FILE));
                    }
                });

        // Template completions
        extend(CompletionType.BASIC,
                PlatformPatterns.psiElement(),
                new CompletionProvider<CompletionParameters>() {
                    @Override
                    protected void addCompletions(@NotNull CompletionParameters parameters,
                                                  @NotNull ProcessingContext context,
                                                  @NotNull CompletionResultSet result) {
                        // Main function template
                        result.addElement(LookupElementBuilder.create("fn main()")
                                .withPresentableText("main")
                                .withTypeText("main function template")
                                .withInsertHandler((insertContext, item) -> {
                                    insertContext.getDocument().insertString(
                                            insertContext.getTailOffset(),
                                            " {\n    \n}"
                                    );
                                    insertContext.getEditor().getCaretModel().moveToOffset(
                                            insertContext.getTailOffset() - 2
                                    );
                                }));

                        // Function with return type template
                        result.addElement(LookupElementBuilder.create("fn name() -> Type")
                                .withPresentableText("fn -> Type")
                                .withTypeText("function with return type")
                                .withInsertHandler((insertContext, item) -> {
                                    insertContext.getDocument().insertString(
                                            insertContext.getTailOffset(),
                                            " {\n    \n}"
                                    );
                                }));
                    }
                });
    }

    // Custom insert handlers
    private static class ParenthesisInsertHandler implements InsertHandler<LookupElement> {
        @Override
        public void handleInsert(@NotNull InsertionContext context, @NotNull LookupElement item) {
            context.getDocument().insertString(context.getTailOffset(), " ()");
            context.getEditor().getCaretModel().moveToOffset(context.getTailOffset() - 1);
        }
    }

    private static class BraceInsertHandler implements InsertHandler<LookupElement> {
        @Override
        public void handleInsert(@NotNull InsertionContext context, @NotNull LookupElement item) {
            context.getDocument().insertString(context.getTailOffset(), " {\n    \n}");
            context.getEditor().getCaretModel().moveToOffset(context.getTailOffset() - 2);
        }
    }

    private static class FunctionInsertHandler implements InsertHandler<LookupElement> {
        @Override
        public void handleInsert(@NotNull InsertionContext context, @NotNull LookupElement item) {
            context.getDocument().insertString(context.getTailOffset(), " name() {\n    \n}");
            context.getEditor().getCaretModel().moveToOffset(context.getTailOffset() - 12);
        }
    }
}