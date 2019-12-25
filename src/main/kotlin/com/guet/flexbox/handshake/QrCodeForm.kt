package com.guet.flexbox.handshake

import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.WriterException
import com.google.zxing.qrcode.QRCodeWriter
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel
import com.intellij.ui.JBColor
import com.intellij.util.ui.UIUtil
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.Toolkit
import java.awt.image.BufferedImage
import javax.swing.JFrame
import javax.swing.JPanel


class QrCodeForm(url: String) : JFrame() {

    init {
        val size = 400
        title = "Display"
        defaultCloseOperation = DISPOSE_ON_CLOSE
        setSize(size, size)
        isResizable = false
        val content = contentPane
        val image = singleTask.submit<BufferedImage> {
            generateQR(url, size)
        }
        val panel = object : JPanel() {
            override fun paintComponent(g: Graphics) {
                g.drawImage(image.get(), 0, 0, size, size, null)
            }
        }
        panel.setSize(size, size)
        content.add(panel)
        isVisible = true
        val windowWidth = width //获得窗口宽
        val windowHeight = height //获得窗口高
        val kit = Toolkit.getDefaultToolkit() //定义工具包
        val screenSize = kit.screenSize //获取屏幕的尺寸
        val screenWidth = screenSize.width //获取屏幕的宽
        val screenHeight = screenSize.height //获取屏幕的高
        setLocation(screenWidth / 2 - windowWidth / 2, screenHeight / 2 - windowHeight / 2)//设置窗口居中显示
    }


    companion object {


        /**
         * Generating a qr code with provided content
         *
         * @param content The content that should be in the QR
         * @return An Buffered image object containing the qr code
         */
        private fun generateQR(content: String, size: Int): BufferedImage {
            try {
                val hintMap = HashMap<EncodeHintType, ErrorCorrectionLevel>()
                hintMap[EncodeHintType.ERROR_CORRECTION] = ErrorCorrectionLevel.L
                val qrCodeWriter = QRCodeWriter()
                val byteMatrix = qrCodeWriter.encode(
                    content,
                    BarcodeFormat.QR_CODE, size, size, hintMap
                )
                val width = byteMatrix.width
                val image = UIUtil.createImage(
                    width,
                    width,
                    BufferedImage.TYPE_INT_RGB
                )
                val graphics = image.createGraphics() as Graphics2D
                graphics.color = JBColor.WHITE
                graphics.fillRect(0, 0, width, width)
                graphics.color = JBColor.BLACK

                for (i in 0 until width) {
                    for (j in 0 until width) {
                        if (byteMatrix.get(i, j)) {
                            graphics.fillRect(i, j, 1, 1)
                        }
                    }
                }

                return image
            } catch (e: WriterException) {
                throw RuntimeException(e)
            }
        }
    }
}