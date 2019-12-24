package com.guet.flexbox.handshake

import com.intellij.openapi.options.SettingsEditor
import javax.swing.JComponent
import javax.swing.JLabel

class FlexmlMockSettingsEditor : SettingsEditor<FlexmlMockRunConfiguration>() {

    override fun resetEditorFrom(s: FlexmlMockRunConfiguration) {
    }

    override fun createEditor(): JComponent {
        return JLabel()
    }

    override fun applyEditorTo(s: FlexmlMockRunConfiguration) {
    }

}