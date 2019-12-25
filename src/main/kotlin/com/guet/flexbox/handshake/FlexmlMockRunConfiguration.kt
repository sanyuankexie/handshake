package com.guet.flexbox.handshake

import com.intellij.execution.Executor
import com.intellij.execution.configurations.ConfigurationFactory
import com.intellij.execution.configurations.LocatableConfigurationBase
import com.intellij.execution.configurations.RunConfiguration
import com.intellij.execution.configurations.RunProfileState
import com.intellij.execution.runners.ExecutionEnvironment
import com.intellij.openapi.options.SettingsEditor
import com.intellij.openapi.project.Project

class FlexmlMockRunConfiguration(project: Project, factory: ConfigurationFactory) :
    LocatableConfigurationBase<Any>(project, factory, "Mock configuration") {

    var port: Int = 8080

    override fun getConfigurationEditor(): SettingsEditor<out RunConfiguration> = FlexmlMockSettingsEditor()

    override fun getState(executor: Executor, environment: ExecutionEnvironment): RunProfileState? {

        return FlexmlMockCommandLineState(this, environment)
    }
}