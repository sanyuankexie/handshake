package com.guet.flexbox.handshake

import com.intellij.psi.PsiElement


val PsiElement.isOnFlexmlFile: Boolean
    get() {
        return if (this.containingFile != null) {
            this.containingFile.name.toLowerCase().endsWith("." + FlexmlFileType.defaultExtension)
        } else {
            false
        }
    }
