package com.guet.flexbox.handshake;

import javax.swing.*;

public class FlexmlSettingFrom {
    public JPanel wrapPanel;
    public JTextField textInput;

    FlexmlSettingFrom(){
        textInput.setInputVerifier(new InputVerifier() {
            @Override
            public boolean verify(JComponent input) {
                try {
                    Integer.parseInt(textInput.getText());
                    return true;
                }catch (Exception e){
                    return false;
                }
            }
        });
    }
}
