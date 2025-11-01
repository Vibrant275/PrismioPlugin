package com.vibrant.prismio.template;

import com.intellij.icons.AllIcons;
import com.intellij.ide.actions.CreateFileFromTemplateAction;
import com.intellij.ide.actions.CreateFileFromTemplateDialog;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiDirectory;
import org.jetbrains.annotations.NotNull;

public class CreateClassAction extends CreateFileFromTemplateAction {

  @Override
  protected void buildDialog(@NotNull Project project, @NotNull PsiDirectory directory, CreateFileFromTemplateDialog.Builder builder) {
    builder
    .setTitle("Prismio File")
            .addKind("File", AllIcons.Actions.InlayRenameInNoCodeFilesActive, "File")      // Representing a generic File-related class
            .addKind("Class", AllIcons.Nodes.Class, "Regular")      // Regular class type (like MyClass)
            .addKind("Enum", AllIcons.Nodes.Enum, "Enum")           // Represents an enum class
            .addKind("Virtual", AllIcons.Nodes.ExceptionClass, "Virtual")  // For classes with virtual methods (overridable)
            .addKind("Struct", AllIcons.Nodes.AbstractClass, "Struct")     // Represents a struct (in languages like C/C++)
            .addKind("Sealed", AllIcons.Nodes.Annotationtype, "Sealed ");    // A class that restricts its subclassing
  }

  @Override
  protected String getActionName(PsiDirectory directory,
      @NotNull String newName, String templateName) {
    return "Create My Class: " + newName;
  }

//  @Override
//  protected void buildDialog(@NotNull Project project, @NotNull PsiDirectory psiDirectory, CreateFileFromTemplateDialog.@NotNull Builder builder) {
//
//  }
}