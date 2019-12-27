package com.guet.flexbox.handshake.mock

import com.intellij.execution.Executor
import com.intellij.execution.configurations.*
import com.intellij.execution.runners.ExecutionEnvironment
import com.intellij.openapi.options.SettingsEditor
import com.intellij.openapi.project.Project
import org.jdom.Element

class FlexmlMockRunConfiguration(project: Project, factory: ConfigurationFactory) :
    LocatableConfigurationBase<FlexmlMockRunConfigurationOptions>(project, factory, "Mock this package") {

    override fun getConfigurationEditor(): SettingsEditor<out RunConfiguration> = FlexmlMockSettingsEditor()

    override fun getState(executor: Executor, environment: ExecutionEnvironment): RunProfileState? {
        return FlexmlMockCommandLineState(this, environment)
    }

    override fun getDefaultOptionsClass(): Class<out LocatableRunConfigurationOptions> {
        return FlexmlMockRunConfigurationOptions::class.java
    }

    override fun readExternal(element: Element) {
        element.getAttribute("port")
    }

    override fun writeExternal(element: Element) {

    }
}