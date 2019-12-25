package test

import com.guet.flexbox.handshake.QrCodeForm

fun main(args: Array<String>) {
    QrCodeForm("adasdasdasdasd").apply {
        isVisible = true
    }
    System.`in`.read()
}