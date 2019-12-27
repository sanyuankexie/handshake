package com.guet.flexbox.handshake.mock

import com.intellij.openapi.options.SettingsEditor
import javax.swing.JComponent

class FlexmlMockSettingsEditor : SettingsEditor<FlexmlMockRunConfiguration>() {

    private val from = FlexmlMockSettingFrom()

    override fun resetEditorFrom(s: FlexmlMockRunConfiguration) {
        from.port.text = s.state?.port.toString()
        from.dataSource.text = s.state?.dataSource
        from.template.text = s.state?.template
    }

    override fun createEditor(): JComponent {
        return from.wrapPanel
    }

    override fun applyEditorTo(s: FlexmlMockRunConfiguration) {
        s.state?.port = from.port.text.toInt()
        s.state?.dataSource = from.dataSource.text
        s.state?.template = from.template.text
    }
}