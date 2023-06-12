package com.email_system;
import javax.swing.*;

import com.email_system.Register.Register;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login_Page extends JFrame {
    static JTextField emailField;
    static JPasswordField passwordField;

    public Login_Page() {
        setTitle("Login Page");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);

        // Create the panel to hold the login components
        JPanel loginPanel = new JPanel();
        loginPanel.setBackground(Color.WHITE);
        loginPanel.setLayout(new GridLayout(3, 1, 10, 10));

        // Create the email label and field
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        emailField = new JTextField();
        loginPanel.add(emailLabel);
        loginPanel.add(emailField);

        // Create the password label and field
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordField = new JPasswordField();
        loginPanel.add(passwordLabel);
        loginPanel.add(passwordField);

        // Create the login button
        JButton loginButton = new JButton("Login");
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.setBackground(new Color(51, 153, 255));
        loginButton.setForeground(Color.WHITE);
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });
        loginPanel.add(loginButton);

        // Create the register button
        JButton registerButton = new JButton("Register");
        registerButton.setFont(new Font("Arial", Font.PLAIN, 14));
        registerButton.setBackground(new Color(102, 204, 0));
        registerButton.setForeground(Color.WHITE);
        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new Register().setVisible(true);
            }
        });
        loginPanel.add(registerButton);

        // Add the panel to the frame
        add(loginPanel, BorderLayout.CENTER);

        // Add KeyListener to the password field for Enter key press
        passwordField.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    login();
                }
            }
        });

        setVisible(true);
    }

    private void login() {
        String email = emailField.getText();
        char[] password = passwordField.getPassword();

        if (isValidLogin(email, password)) {
            JOptionPane.showMessageDialog(Login_Page.this, "Login Successful!");

            dispose();
            new Compose(email).setVisible(true); // Pass email to Compose constructor
        } else {
            JOptionPane.showMessageDialog(Login_Page.this, "Invalid Credentials. Please try again.",
                    "Login Failed", JOptionPane.ERROR_MESSAGE);
            passwordField.setText("");
        }
    }

    private boolean isValidLogin(String email, char[] password) {
        String jdbcURL = "jdbc:mysql://localhost:3306/email_send";
        String username = "root";
        String passwordStr = "Manas@2201";

        try (Connection connection = DriverManager.getConnection(jdbcURL, username, passwordStr)) {
            String sql = "SELECT * FROM users WHERE email = ? AND password = ?";
            PreparedStatement statement = connection.prepareStatement (sql);
            statement.setString(1, email);
            statement.setString(2, new String(password));
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
            } catch (SQLException e) {
            e.printStackTrace();
            }
        return false;
    }

    public static void main(String[] args) {
        try {
            // Set look and feel to Nimbus
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> new Login_Page().setVisible(true));
    }
}
