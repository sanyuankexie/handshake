package com.guet.flexbox.handshake

import com.guet.flexbox.handshake.configs.ComponentConfiguration
import com.intellij.codeInsight.completion.*
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.icons.AllIcons
import com.intellij.patterns.PlatformPatterns
import com.intellij.psi.impl.source.xml.XmlAttributeReference
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.psi.xml.XmlAttribute
import com.intellij.psi.xml.XmlElementType
import com.intellij.util.ProcessingContext

class FlexmlCompletionContributor : CompletionContributor() {

    init {
        extend(CompletionType.BASIC, PlatformPatterns.psiElement(),
            object : CompletionProvider<CompletionParameters>() {
                override fun addCompletions(
                    parameters: CompletionParameters,
                    context: ProcessingContext,
                    result: CompletionResultSet
                ) {
                    if (!parameters.position.isOnFlexmlFile) {
                        return
                    }
                    val reference = parameters
                        .position
                        .containingFile
                        .findReferenceAt(parameters.offset)
                    if (reference is XmlAttributeReference) {
                        val declarationTag = reference.element.parent
                        result.addAllElements(
                            ComponentConfiguration
                                .getAttributeInfoByComponentName(declarationTag.name)
                                .toMutableMap()
                                .apply {
                                    declarationTag.attributes.forEach {
                                        remove(it.name)
                                    }
                                }.map {
                                    val (name, value) = it
                                    LookupElementBuilder.create(name)
                                        .withInsertHandler(XmlAttributeInsertHandler.INSTANCE)
                                        .withBoldness(true)
                                        .withIcon(tagIcon)
                                        .withTypeText(
                                            "${declarationTag.name} attribute" +
                                                    if (value?.required == true) {
                                                        "(required)"
                                                    } else {
                                                        ""
                                                    }
                                        )
                                })
                    }
                }
            })

        extend(
            CompletionType.BASIC, PlatformPatterns.psiElement(),
            object : CompletionProvider<CompletionParameters>() {
                override fun addCompletions(
                    parameters: CompletionParameters,
                    context: ProcessingContext,
                    result: CompletionResultSet
                ) {
                    if (!parameters.position.isOnFlexmlFile) {
                        return
                    }
                    val position = parameters.position
                    if (position.node.elementType === XmlElementType.XML_ATTRIBUTE_VALUE_TOKEN) {
                        val attr = PsiTreeUtil.getParentOfType(
                            position,
                            XmlAttribute::class.java
                        )
                        if (attr != null) {
                            val attrValues = ComponentConfiguration
                                .getAttributeInfoByComponentName(attr.parent.name)[attr.name]
                            if (attrValues?.values != null) {
                                result.addAllElements(attrValues.values.map {
                                    LookupElementBuilder.create(it)
                                        .withIcon(AllIcons.FileTypes.Xml)
                                })
                            }
                        }
                    }
                }
            })
    }
}