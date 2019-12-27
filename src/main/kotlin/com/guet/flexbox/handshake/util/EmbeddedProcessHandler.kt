package com.guet.flexbox.handshake.util

import com.intellij.execution.KillableProcess
import com.intellij.execution.process.ProcessHandler
import com.intellij.execution.process.ProcessOutputTypes
import java.io.OutputStream

abstract class EmbeddedProcessHandler : ProcessHandler(), KillableProcess {

    abstract fun onStart()

    abstract fun onDestroy()

    fun println(text: String) {
        notifyTextAvailable(text + "\n", ProcessOutputTypes.STDOUT)
    }

    final override fun startNotify() {
        onStart()
        super.startNotify()
    }

    final override fun killProcess() {
        onDestroy()
    }

    final override fun canKillProcess(): Boolean = true

    final override fun getProcessInput(): OutputStream = NullOutputStream

    final override fun detachIsDefault(): Boolean = true

    final override fun detachProcessImpl() {
        killProcess()
    }

    final override fun destroyProcessImpl() {
        killProcess()
    }
}