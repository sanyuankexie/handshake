package com.guet.flexbox.handshake

import com.intellij.execution.KillableProcess
import com.intellij.execution.configurations.CommandLineState
import com.intellij.execution.process.ProcessHandler
import com.intellij.execution.process.ProcessOutputTypes
import com.intellij.execution.runners.ExecutionEnvironment
import com.intellij.util.concurrency.AppExecutorUtil
import com.sun.net.httpserver.HttpServer
import java.awt.EventQueue
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import java.io.OutputStream
import java.net.Inet4Address
import java.net.InetAddress
import java.net.InetSocketAddress
import java.net.NetworkInterface

class FlexmlMockCommandLineState(
    private val configuration: FlexmlMockRunConfiguration,
    environment: ExecutionEnvironment
) : CommandLineState(environment) {

    override fun startProcess(): ProcessHandler {
        return MockServerHandler(configuration)
    }

    private class MockServerHandler(
        private val configuration: FlexmlMockRunConfiguration
    ) : ProcessHandler(), KillableProcess {

        private var form: QrCodeForm? = null

        private var server: HttpServer? = null

        override fun startNotify() {
            val server = HttpServer.create(InetSocketAddress(configuration.port), 0)
            server.executor = AppExecutorUtil.getAppExecutorService()
            server.createContext("/layout") { httpExchange ->
                notifyTextAvailable(
                    httpExchange.remoteAddress.toString() + " request layout",
                    ProcessOutputTypes.STDOUT
                )
                httpExchange.sendResponseHeaders(200, 0)
            }
            server.createContext("/data") { httpExchange ->
                notifyTextAvailable(
                    httpExchange.remoteAddress.toString() + " request layout",
                    ProcessOutputTypes.STDOUT
                )
                httpExchange.sendResponseHeaders(200, 0)
            }
            val address = findHostAddress()
            if (address != null) {
                val url = "http://$address:${configuration.port}"
                notifyTextAvailable("layout url：$url/layout\n", ProcessOutputTypes.STDOUT)
                notifyTextAvailable("data source url：$url/data\n", ProcessOutputTypes.STDOUT)
                server.start()
                EventQueue.invokeLater {
                    val form = QrCodeForm(url)
                    form.addWindowListener(object : WindowAdapter() {
                        override fun windowClosing(e: WindowEvent?) {
                            killProcess()
                        }
                    })
                    this.form = form
                }
            } else {
                notifyTextAvailable(
                    "搜索本机IP时出错，请检查机器的在网状态，" +
                            "并确保手机和电脑在同一网络中\n",
                    ProcessOutputTypes.STDERR
                )
                throw RuntimeException("搜索本机IP时出错")
            }
            this.server = server
            super.startNotify()
        }

        override fun killProcess() {
            EventQueue.invokeLater {
                this.form?.dispose()
                this.form = null
            }
            server?.stop(0)
            server = null
            notifyProcessTerminated(0)
        }

        override fun canKillProcess(): Boolean = true

        override fun getProcessInput(): OutputStream = NullOutputStream

        override fun detachIsDefault(): Boolean = true

        override fun detachProcessImpl() {
            killProcess()
        }

        override fun destroyProcessImpl() {
            killProcess()
        }

        companion object {
            private fun findHostAddress(): String? {
                try {
                    val allNetInterfaces = NetworkInterface
                        .getNetworkInterfaces()
                    while (allNetInterfaces.hasMoreElements()) {
                        val netInterface = allNetInterfaces.nextElement()
                                as NetworkInterface
                        val addresses = netInterface.inetAddresses
                        while (addresses.hasMoreElements()) {
                            val ip = addresses.nextElement()
                                    as InetAddress
                            if (ip is Inet4Address && !ip.isLoopbackAddress
                                && ip.hostAddress.indexOf(":") == -1
                            ) {
                                return ip.hostAddress
                            }
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                return null
            }
        }
    }

    private object NullOutputStream : OutputStream() {

        override fun write(b: ByteArray, off: Int, len: Int) {}

        override fun write(b: Int) {}

        override fun write(b: ByteArray) {}
    }
}