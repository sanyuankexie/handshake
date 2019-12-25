package com.guet.flexbox.handshake

import com.intellij.execution.actions.ConfigurationContext
import com.intellij.execution.actions.RunConfigurationProducer
import com.intellij.execution.configurations.ConfigurationTypeUtil
import com.intellij.openapi.util.Ref
import com.intellij.psi.PsiElement

class FlexmlMockRunConfigurationProducer :
    RunConfigurationProducer<FlexmlMockRunConfiguration>(
        ConfigurationTypeUtil.findConfigurationType(
            FlexmlMockConfigurationType::class.java
        )
    ) {

    override fun isConfigurationFromContext(
        configuration: FlexmlMockRunConfiguration?,
        context: ConfigurationContext
    ): Boolean = context.psiLocation?.isOnFlexmlFile == true

    override fun setupConfigurationFromContext(
        configuration: FlexmlMockRunConfiguration?,
        context: ConfigurationContext,
        sourceElement: Ref<PsiElement>
    ): Boolean {

        return sourceElement.get()?.isOnFlexmlFile == true
    }

}
