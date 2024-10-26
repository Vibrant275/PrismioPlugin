package com.vibrant.prismio;

import com.intellij.formatting.*;
import com.intellij.psi.codeStyle.CodeStyleSettings;
import com.vibrant.prismio.psi.PrismioTypes;
import org.jetbrains.annotations.NotNull;

final class SimpleFormattingModelBuilder implements FormattingModelBuilder {

  private static SpacingBuilder createSpaceBuilder(CodeStyleSettings settings) {
    return new SpacingBuilder(settings, PrismioLanguage.INSTANCE)
        .around(PrismioTypes.SEPARATOR)
        .spaceIf(settings.getCommonSettings(PrismioLanguage.INSTANCE.getID()).SPACE_AROUND_ASSIGNMENT_OPERATORS)
        .before(PrismioTypes.PROPERTY)
        .none();
  }

  @Override
  public @NotNull FormattingModel createModel(@NotNull FormattingContext formattingContext) {
    final CodeStyleSettings codeStyleSettings = formattingContext.getCodeStyleSettings();
    return FormattingModelProvider
        .createFormattingModelForPsiFile(formattingContext.getContainingFile(),
            new SimpleBlock(formattingContext.getNode(),
                Wrap.createWrap(WrapType.NONE, false),
                Alignment.createAlignment(),
                createSpaceBuilder(codeStyleSettings)),
            codeStyleSettings);
  }

}
