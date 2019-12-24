package com.guet.flexbox.handshake

import com.intellij.execution.configurations.JavaCommandLineState
import com.intellij.execution.configurations.JavaParameters
import com.intellij.execution.runners.ExecutionEnvironment

class FlexmlMockCommandLineState(environment: ExecutionEnvironment) : JavaCommandLineState(environment) {

    override fun createJavaParameters(): JavaParameters {
        return JavaParameters()
    }
}