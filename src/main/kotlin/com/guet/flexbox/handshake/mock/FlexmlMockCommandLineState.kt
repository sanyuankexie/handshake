package com.guet.flexbox.handshake.mock

import com.intellij.execution.configurations.CommandLineState
import com.intellij.execution.process.ProcessHandler
import com.intellij.execution.runners.ExecutionEnvironment

class FlexmlMockCommandLineState(
    private val configuration: FlexmlMockRunConfiguration,
    environment: ExecutionEnvironment
) : CommandLineState(environment) {

    override fun startProcess(): ProcessHandler {
        return MockServerHandler(configuration)
    }
}