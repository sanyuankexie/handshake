package com.guet.flexbox.handshake.mock

import com.guet.flexbox.handshake.compile.Compiler
import com.guet.flexbox.handshake.util.EmbeddedProcessHandler
import com.intellij.execution.process.ProcessOutputTypes
import com.intellij.util.concurrency.AppExecutorUtil
import com.sun.net.httpserver.HttpServer
import java.awt.EventQueue
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import java.io.File
import java.io.OutputStreamWriter
import java.io.RandomAccessFile
import java.net.Inet4Address
import java.net.InetAddress
import java.net.InetSocketAddress
import java.net.NetworkInterface
import java.nio.channels.Channels

class MockServerHandler(
    private val configuration: FlexmlMockRunConfiguration
) : EmbeddedProcessHandler() {

    private var form: QrCodeForm? = null

    private var server: HttpServer? = null

    override fun onStart() {
        val port = configuration.state?.port ?: 8080
        val template = configuration.state?.template
        if (template.isNullOrEmpty()) {
            notifyTextAvailable("No target file found\n", ProcessOutputTypes.STDERR)
            killProcess()
            return
        }
        var dataSource = configuration.state?.dataSource
        if (dataSource.isNullOrEmpty()) {
            dataSource = File(template, "data.json").absolutePath
        }
        val server = HttpServer.create(InetSocketAddress(port), 0)
        server.executor = AppExecutorUtil.getAppExecutorService()
        server.createContext("/template") { httpExchange ->
            println(httpExchange.remoteAddress.toString() + " request layout")
            try {
                httpExchange.sendResponseHeaders(200, 0)
                val os = OutputStreamWriter(httpExchange.responseBody)
                val string = Compiler.compile(template)
                os.write(string)
                os.close()
            } catch (e: Exception) {
                notifyTextAvailable(
                    e.toString(),
                    ProcessOutputTypes.STDERR
                )
            }
        }
        server.createContext("/datasource") { httpExchange ->
            println(httpExchange.remoteAddress.toString() + " request datasource")
            try {
                httpExchange.sendResponseHeaders(200, 0)
                val output = Channels.newChannel(httpExchange.responseBody)
                val file = RandomAccessFile(dataSource, "r")
                val input = file.channel
                input.transferTo(0, file.length(), output)
                output.close()
                file.close()
            } catch (e: Exception) {
                notifyTextAvailable(
                    e.toString(),
                    ProcessOutputTypes.STDERR
                )
            }
        }
        val address = findHostAddress()
        if (address != null) {
            val url = "http://$address:$port"
            AppExecutorUtil.getAppExecutorService().execute {
                println("host url：$url")
            }
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
            AppExecutorUtil.getAppExecutorService().submit {
                notifyTextAvailable(
                    "An error occurred while searching the local IP address. " +
                            "Please check the network status of the machine and make " +
                            "sure that the mobile phone and the computer are on the same network",
                    ProcessOutputTypes.STDERR
                )
            }
            throw RuntimeException("Error searching for local IP")
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