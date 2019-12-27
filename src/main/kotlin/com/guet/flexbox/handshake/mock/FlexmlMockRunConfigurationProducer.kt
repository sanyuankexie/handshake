package com.guet.flexbox.handshake.mock

import com.guet.flexbox.handshake.util.isOnFlexmlFile
import com.intellij.execution.actions.ConfigurationContext
import com.intellij.execution.actions.RunConfigurationProducer
import com.intellij.execution.configurations.ConfigurationTypeUtil
import com.intellij.openapi.util.Ref
import com.intellij.psi.PsiElement
import com.intellij.psi.xml.XmlFile

class FlexmlMockRunConfigurationProducer : RunConfigurationProducer<FlexmlMockRunConfiguration>(
    ConfigurationTypeUtil.findConfigurationType(
        FlexmlMockConfigurationType::class.java
    )
) {
    override fun isConfigurationFromContext(
        configuration: FlexmlMockRunConfiguration,
        context: ConfigurationContext
    ): Boolean {
        if (context.psiLocation?.isOnFlexmlFile == true) {
            val file = context.psiLocation?.containingFile as? XmlFile
            if (file != null) {
                return configuration.state?.template == file.virtualFile.path
                        && configuration.name == "Mock ${file.virtualFile.name}"
            }
        }
        return false
    }

    override fun setupConfigurationFromContext(
        configuration: FlexmlMockRunConfiguration,
        context: ConfigurationContext,
        sourceElement: Ref<PsiElement>
    ): Boolean {
        if (sourceElement.get()?.isOnFlexmlFile == true) {
            val file = context.psiLocation?.containingFile as? XmlFile
            if (file != null) {
                configuration.name = "Mock ${file.virtualFile.name}"
                configuration.state?.template = file.virtualFile.path
                context.setConfiguration(
                    context.runManager.createConfiguration(
                        configuration,
                        configurationFactory
                    )
                )
                return true
            }
        }
        return false
    }
}