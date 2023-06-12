package com.email_system.Register;

import javax.swing.*;

import com.email_system.Login_Page;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;

public class Register extends JFrame {
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;

    public Register() {
        setTitle("Registration Page");
        setSize(300, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout());

        // Create the label
        JLabel titleLabel = new JLabel("Registration Page", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(titleLabel, BorderLayout.NORTH);

        // Create the panel to hold the registration components
        JPanel registrationPanel = new JPanel();
        registrationPanel.setBackground(Color.WHITE);
        registrationPanel.setLayout(new GridLayout(6, 2, 10, 10));

        // Create the first name label and field
        JLabel firstNameLabel = new JLabel("First Name:");
        firstNameField = new JTextField();
        registrationPanel.add(firstNameLabel);
        registrationPanel.add(firstNameField);

        // Create the last name label and field
        JLabel lastNameLabel = new JLabel("Last Name:");
        lastNameField = new JTextField();
        registrationPanel.add(lastNameLabel);
        registrationPanel.add(lastNameField);

        // Create the email label and field
        JLabel emailLabel = new JLabel("Email Address:");
        emailField = new JTextField();
        registrationPanel.add(emailLabel);
        registrationPanel.add(emailField);

        // Create the password label and field
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();
        registrationPanel.add(passwordLabel);
        registrationPanel.add(passwordField);

        // Create the confirm password label and field
        JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
        confirmPasswordField = new JPasswordField();
        registrationPanel.add(confirmPasswordLabel);
        registrationPanel.add(confirmPasswordField);

        // Create the save button
        JButton saveButton = new JButton("Save");
        saveButton.setBackground(new Color(59, 89, 182));
        saveButton.setForeground(Color.WHITE);
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String firstName = firstNameField.getText();
                String lastName = lastNameField.getText();
                String email = emailField.getText();
                char[] password = passwordField.getPassword();
                char[] confirmPassword = confirmPasswordField.getPassword();

                if (isValidRegistration(firstName, lastName, email, password, confirmPassword)) {
                    saveCredentials(firstName, lastName, email, password);
                    JOptionPane.showMessageDialog(Register.this, "Registration Successful!");
                    dispose();

                    new Login_Page().setVisible(true);
                }
            }
        });
        registrationPanel.add(saveButton);

        // Create the "Already have an account? Login" button
        JButton loginButton = new JButton("Already have an account? Login");
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new Login_Page().setVisible(true);
            }
        });
        registrationPanel.add(loginButton);

        // Add the panel to the frame
        add(registrationPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    private boolean isValidRegistration(String firstName, String lastName, String email,
                                        char[] password, char[] confirmPassword) {
        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.length == 0 || confirmPassword.length == 0) {
            JOptionPane.showMessageDialog(Register.this, "Please fill out all fields.","Registration Failed", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!Arrays.equals(password, confirmPassword)) {
            JOptionPane.showMessageDialog(Register.this, "Passwords don't match. Please try again.",
                    "Registration Failed", JOptionPane.ERROR_MESSAGE);
            passwordField.setText("");
            confirmPasswordField.setText("");
            return false;
        }

        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        if (!email.matches(emailRegex)) {
            JOptionPane.showMessageDialog(Register.this, "Invalid email address. Please enter a valid email.",
                    "Registration Failed", JOptionPane.ERROR_MESSAGE);
            emailField.setText("");
            return false;
        }
        
        String passwordStr = new String(password);
        if (passwordStr.length() < 8 || !passwordStr.matches(".*[A-Z].*") || !passwordStr.matches(".*[!@#$%^&*()].*")) {
            JOptionPane.showMessageDialog(Register.this,
                    "Invalid password. The password must be at least 8 characters long, contain at least one uppercase letter, and have a special character.",
                    "Registration Failed", JOptionPane.ERROR_MESSAGE);
            passwordField.setText("");
            confirmPasswordField.setText("");
            return false;
        }
        
        return true;
    }

    private void saveCredentials(String firstName, String lastName, String email, char[] password) {
        String jdbcURL = "jdbc:mysql://localhost:3306/email_send";
        String username = "root";
        String passwordStr = "Manas@2201";
        
        try (Connection connection = DriverManager.getConnection(jdbcURL, username, passwordStr)) {
            String sql = "INSERT INTO users (first_name, last_name, email, password) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setString(3, email);
            statement.setString(4, new String(password));
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Register::new);
    }
}
