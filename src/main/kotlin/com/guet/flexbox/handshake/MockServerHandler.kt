package com.guet.flexbox.handshake

import com.intellij.execution.process.ProcessOutputTypes
import com.intellij.util.concurrency.AppExecutorUtil
import com.sun.net.httpserver.HttpServer
import java.awt.EventQueue
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import java.net.Inet4Address
import java.net.InetAddress
import java.net.InetSocketAddress
import java.net.NetworkInterface

class MockServerHandler(
    private val configuration: FlexmlMockRunConfiguration
) : EmbeddedProcessHandler() {

    private var form: QrCodeForm? = null

    private var server: HttpServer? = null

    override fun onStart() {
        val server = HttpServer.create(InetSocketAddress(configuration.port), 0)
        server.executor = AppExecutorUtil.getAppExecutorService()
        server.createContext("/layout") { httpExchange ->
            println(
                httpExchange.remoteAddress.toString() + " request layout"
            )
            httpExchange.sendResponseHeaders(200, 0)
        }
        server.createContext("/data") { httpExchange ->
            println(
                httpExchange.remoteAddress.toString() + " request layout"
            )
            httpExchange.sendResponseHeaders(200, 0)
        }
        AppExecutorUtil.getAppExecutorService().submit {

        }
        val address = findHostAddress()
        if (address != null) {
            val url = "http://$address:${configuration.port}"
            println("host url：$url")
            server.start()
            this.server = server
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
                "An error occurred while searching the local IP address. " +
                        "Please check the network status of the machine and make " +
                        "sure that the mobile phone and the computer are on the same network",
                ProcessOutputTypes.STDERR
            )
            throw RuntimeException("Error searching for native IP")
        }
    }

    override fun onDestroy() {
        EventQueue.invokeLater {
            this.form?.dispose()
            this.form = null
        }
        server?.stop(0)
        server = null
        notifyProcessTerminated(0)
    }

    companion object {
        private fun findHostAddress(): String? {
            try {
                val allNetInterfaces = NetworkInterface.getNetworkInterfaces()
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