package com.guet.flexbox.handshake

import com.guet.flexbox.handshake.configs.ComponentConfiguration
import com.intellij.codeInsight.completion.XmlTagInsertHandler
import com.intellij.codeInsight.lookup.LookupElement
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.psi.xml.XmlTag
import com.intellij.xml.XmlTagNameProvider

class FlexmlTagNameProvider : XmlTagNameProvider {

    override fun addTagNameVariants(
        elements: MutableList<LookupElement>,
        tag: XmlTag,
        prefix: String?
    ) {
        if (!tag.isOnFlexmlFile) {
            return
        }
        elements.addAll(
            ComponentConfiguration.allComponentNames.map {
                LookupElementBuilder.create(it)
                    .withInsertHandler(XmlTagInsertHandler.INSTANCE)
                    .withBoldness(true)
                    .withIcon(tagIcon)
                    .withTypeText("flexml component")
            }
        )
    }

}



