package org.edu.utfpr.elicit;

import view.LoginView;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::launchScreens);
    }

    private static void launchScreens() {
        new LoginView().setVisible(true);
    }
}