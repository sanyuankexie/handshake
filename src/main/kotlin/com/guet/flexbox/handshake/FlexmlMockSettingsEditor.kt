package com.guet.flexbox.handshake

import com.intellij.openapi.options.SettingsEditor
import javax.swing.JComponent

class FlexmlMockSettingsEditor : SettingsEditor<FlexmlMockRunConfiguration>() {

    private val from = FlexmlSettingFrom()

    override fun resetEditorFrom(s: FlexmlMockRunConfiguration) {
        from.textInput.text = "8080"
        s.port = 8080
    }

    override fun createEditor(): JComponent {
        return from.wrapPanel
    }

    override fun applyEditorTo(s: FlexmlMockRunConfiguration) {
        from.textInput.text = s.port.toString()
    }
}