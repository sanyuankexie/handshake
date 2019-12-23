package com.guet.flexbox.handshake

import com.intellij.psi.PsiElement
import com.intellij.xml.impl.BasicXmlAttributeDescriptor
import com.intellij.xml.impl.XmlAttributeDescriptorEx

class FlexmlAttributeDescriptor(
    private val attributeName: String,
    private val required: Boolean,
    private val enumValues: Array<String>?
) : BasicXmlAttributeDescriptor(), XmlAttributeDescriptorEx {


    override fun getDefaultValue(): String? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getName(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun isRequired(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hasIdRefType(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun init(element: PsiElement?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun isFixed(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getDeclaration(): PsiElement {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun isEnumerated(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getEnumeratedValues(): Array<String>? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hasIdType(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun handleTargetRename(newTargetName: String): String? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}