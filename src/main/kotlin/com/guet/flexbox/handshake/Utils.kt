package com.guet.flexbox.handshake

import com.intellij.openapi.util.IconLoader
import com.intellij.psi.PsiElement


val PsiElement.isOnFlexmlFile: Boolean
    get() {
        return if (this.containingFile != null) {
            this.containingFile.name.toLowerCase().endsWith("." + FlexmlFileType.defaultExtension)
        } else {
            false
        }
    }

val fileIcon = IconLoader.getIcon("icons/icon_file.png").apply {

}

val tagIcon = IconLoader.getIcon("icons/icon_tag.png")
