package com.guet.flexbox.handshake.util

import com.guet.flexbox.handshake.lang.FlexmlFileType
import com.intellij.openapi.util.IconLoader
import com.intellij.psi.PsiElement
import java.util.regex.Pattern

val PsiElement.isOnFlexmlFile: Boolean
    get() {
        return if (this.containingFile != null) {
            this.containingFile.name.toLowerCase()
                .endsWith("." + FlexmlFileType.defaultExtension)
        } else {
            false
        }
    }


val fileIcon = IconLoader.getIcon("icons/icon_file.png")

val tagIcon = IconLoader.getIcon("icons/icon_tag.png")

val String.isUrl: Boolean
    get() {
        val regex = "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?"
        return match(regex, this)
    }

private fun match(regex: String, str: String): Boolean {
    val pattern = Pattern.compile(regex)
    val matcher = pattern.matcher(str)
    return matcher.matches()
}

