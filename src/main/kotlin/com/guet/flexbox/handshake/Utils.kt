package com.guet.flexbox.handshake

import com.intellij.openapi.util.IconLoader
import com.intellij.psi.PsiElement
import java.util.*


val PsiElement.isOnFlexmlFile: Boolean
    get() {
        return if (this.containingFile != null) {
            this.containingFile.name.toLowerCase().endsWith("." + FlexmlFileType.defaultExtension)
        } else {
            false
        }
    }

val fileIcon = IconLoader.getIcon("icons/icon_file.png")

val tagIcon = IconLoader.getIcon("icons/icon_tag.png")

private val resourceBundle = ResourceBundle.getBundle("strings")


fun String.getString(): String = resourceBundle.getString(this)
