package com.guet.flexbox.handshake

import com.intellij.execution.configurations.ConfigurationFactory
import com.intellij.execution.configurations.ConfigurationTypeBase
import com.intellij.execution.configurations.RunConfiguration
import com.intellij.openapi.project.Project

class FlexmlMockConfigurationType : ConfigurationTypeBase(
    "FlexmlMock",
    "Flexml mock",
    "begin run flexml mock task",
    fileIcon
) {
    init {
        addFactory(object : ConfigurationFactory(this) {

            override fun createTemplateConfiguration(project: Project): RunConfiguration {
                return FlexmlMockRunConfiguration(project, this)
            }
        })
    }

}