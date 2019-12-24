package com.guet.flexbox.handshake

import com.guet.flexbox.handshake.configs.ComponentConfiguration
import com.intellij.codeInsight.completion.XmlTagInsertHandler
import com.intellij.codeInsight.lookup.LookupElement
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.psi.impl.source.xml.DefaultXmlTagNameProvider
import com.intellij.psi.xml.XmlTag

class FlexmlTagNameProvider : DefaultXmlTagNameProvider() {

    private val allTags = ComponentConfiguration.allComponentNames.map {
        LookupElementBuilder.create(it)
            .withInsertHandler(XmlTagInsertHandler.INSTANCE)
            .withBoldness(true)
            .withIcon(tagIcon)
            .withTypeText("flexml component")
    }

    override fun addTagNameVariants(
        elements: MutableList<LookupElement>,
        tag: XmlTag,
        prefix: String?
    ) {
        super.addTagNameVariants(elements, tag, prefix)
        if (!tag.isOnFlexmlFile) {
            return
        }
        elements.addAll(allTags)
    }

}



