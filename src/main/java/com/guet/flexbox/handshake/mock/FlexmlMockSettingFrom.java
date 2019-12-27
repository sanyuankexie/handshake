package com.guet.flexbox.handshake.mock;

import javax.swing.*;

public class FlexmlMockSettingFrom {
    public JPanel wrapPanel;
    public JTextField port;
    public JTextField template;
    public JTextField dataSource;

    public FlexmlMockSettingFrom(){
        port.setInputVerifier(new InputVerifier() {
            @Override
            public boolean verify(JComponent input) {
                try {
                    Integer.parseInt(port.getText());
                    return true;
                }catch (Exception e){
                    return false;
                }
            }
        });
    }
}
