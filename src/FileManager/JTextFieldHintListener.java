package FileManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusListener;
import java.awt.event.FocusEvent;

public class JTextFieldHintListener implements FocusListener {
    private String mHintText;
    private JTextField mTextField;

    public JTextFieldHintListener(String hintText, JTextField textField) {
        this.mHintText = hintText;
        this.mTextField = textField;
        textField.setForeground(Color.BLACK);
    }
    @Override
    public void focusGained(FocusEvent e) {
        String temp = mTextField.getText();
        if(temp.equals(mHintText)){
            mTextField.setText("");
            mTextField.setForeground(Color.BLACK);
        }
    }
    @Override
    public void focusLost(FocusEvent e) {
        String temp = mTextField.getText();
        if(temp.equals("")){
            mTextField.setForeground(Color.BLACK);
            mTextField.setText(mHintText);
        }
    }
}
