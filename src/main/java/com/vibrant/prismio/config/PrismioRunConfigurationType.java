package com.vibrant.prismio.config;

import com.intellij.execution.configurations.ConfigurationTypeBase;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.util.NotNullLazyValue;
import com.vibrant.prismio.utils.Icons;

final class PrismioRunConfigurationType extends ConfigurationTypeBase {

    static final String ID = "PrismioRunConfiguration";
    static final String NAME = "Prismio";


    PrismioRunConfigurationType() {
        super(ID,NAME, "", NotNullLazyValue.createValue(() -> Icons.FILE));
        addFactory(new PrismioConfigurationFactory(this) {});
    }
}
