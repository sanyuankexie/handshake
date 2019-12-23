package com.guet.flexbox.handshake

import com.intellij.codeInsight.completion.*
import com.intellij.lang.xml.XMLLanguage
import com.intellij.patterns.PlatformPatterns
import com.intellij.psi.xml.XmlElementType
import com.intellij.util.ProcessingContext

class FlexmlCompletionContributor : CompletionContributor() {
    init {
        extend(CompletionType.BASIC,
            PlatformPatterns.psiElement(XmlElementType.XML_ATTRIBUTE)
                .withLanguage(XMLLanguage.INSTANCE),
            object : CompletionProvider<CompletionParameters>() {
                override fun addCompletions(
                    parameters: CompletionParameters,
                    context: ProcessingContext,
                    result: CompletionResultSet
                ) {

                }
            })
    }
}