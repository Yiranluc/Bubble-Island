package controllers;

import javax.swing.JOptionPane;

public class UserDaoController {

    public void registered() {
        JOptionPane.showMessageDialog(null, "Your account has been created!");
    }

    public void emptyFields() {
        JOptionPane.showMessageDialog(null, "Please fill in all required fields.");
    }

    public void duplicateUsername() {
        JOptionPane.showMessageDialog(null, "This username is already in use.");
    }

    public void connectionFailed() {
        JOptionPane.showMessageDialog(null,
                "The connection with the database failed. Please try again.");
    }

    public void loggedIn() {
        JOptionPane.showMessageDialog(null, "You are ready to start playing.");
    }

    public void logInFailed() {
        JOptionPane.showMessageDialog(null,
                "Invalid credentials. Please try again.");
    }
}
