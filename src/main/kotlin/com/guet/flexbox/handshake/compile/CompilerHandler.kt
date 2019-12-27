package com.guet.flexbox.handshake.compile

import com.guet.flexbox.handshake.util.EmbeddedHandler
import com.intellij.execution.process.ProcessOutputTypes
import com.intellij.util.concurrency.AppExecutorUtil
import java.io.File
import java.io.PrintWriter
import java.io.StringWriter

class CompilerHandler(
    private val configuration: FlexmlCompileRunConfiguration
) : EmbeddedHandler() {

    override fun onStart() {
        val template = configuration.state?.template
        if (template.isNullOrEmpty()) {
            notifyTextAvailable("No target file found\n", ProcessOutputTypes.STDERR)
            killProcess()
            return
        }
        val output = configuration.state?.output
        val fixedOutput = if (output.isNullOrEmpty()) {
            File(File(template).parentFile, "out.json").absolutePath
        } else {
            output
        }
        AppExecutorUtil.getAppExecutorService().submit {
            try {
                val file = File(fixedOutput)
                if (!file.exists()) {
                    file.createNewFile()
                }
                file.writer().buffered().use { writer ->
                    writer.write(Compiler.compile(template))
                }
            } catch (e: Exception) {
                val w = StringWriter()
                e.printStackTrace(PrintWriter(w))
                notifyTextAvailable(w.toString() + "\n", ProcessOutputTypes.STDERR)
            } finally {
                killProcess()
            }
        }
    }
}