package com.vibrant.prismio.handler;

import com.intellij.codeInsight.editorActions.TypedHandlerDelegate;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.EditorModificationUtil;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFile;
import com.vibrant.prismio.psi.PrismioFile;
import org.jetbrains.annotations.NotNull;

public class PrismioTypedHandler extends TypedHandlerDelegate {

  @Override
  public @NotNull Result charTyped(char c, @NotNull Project project, @NotNull Editor editor, @NotNull PsiFile file) {
    if (!(file instanceof PrismioFile)) {
      return Result.CONTINUE;
    }

    int offset = editor.getCaretModel().getOffset();
    
    switch (c) {
      case '(':
        EditorModificationUtil.insertStringAtCaret(editor, ")", false, 0);
        return Result.STOP;
        
      case '{':
        EditorModificationUtil.insertStringAtCaret(editor, "}", false, 0);
        return Result.STOP;
        
      case '[':
        EditorModificationUtil.insertStringAtCaret(editor, "]", false, 0);
        return Result.STOP;
        
      case '"':
        // Only auto-close if not already after a quote
        if (offset > 0) {
          char prevChar = editor.getDocument().getCharsSequence().charAt(offset - 2);
          if (prevChar != '\\') {
            EditorModificationUtil.insertStringAtCaret(editor, "\"", false, 0);
            return Result.STOP;
          }
        } else {
          EditorModificationUtil.insertStringAtCaret(editor, "\"", false, 0);
          return Result.STOP;
        }
        break;
        
      case '\'':
        // Only auto-close if not already after a quote
        if (offset > 0) {
          char prevChar = editor.getDocument().getCharsSequence().charAt(offset - 2);
          if (prevChar != '\\') {
            EditorModificationUtil.insertStringAtCaret(editor, "'", false, 0);
            return Result.STOP;
          }
        } else {
          EditorModificationUtil.insertStringAtCaret(editor, "'", false, 0);
          return Result.STOP;
        }
        break;
    }

    return Result.CONTINUE;
  }
}