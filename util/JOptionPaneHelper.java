package com.mycompany.flowerbouquet.util;

import javax.swing.JOptionPane;

public class JOptionPaneHelper {

    public static void showError(String message) {

        JOptionPane.showMessageDialog(
                null,
                message,
                "PETALCRAFT",
                JOptionPane.ERROR_MESSAGE
        );
    }
}