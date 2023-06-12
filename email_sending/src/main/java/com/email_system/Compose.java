package com.email_system;

import javax.swing.*;
import javax.swing.text.JTextComponent;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URI;

public class Compose extends JFrame {
    static JTextField recipientField;
    private JTextField senderField;
    static JButton attachmentButton;
    private JButton sendButton;
    static Component subjectField;
    static JTextArea messageField;

    public Compose(String senderEmail) {
        setTitle("Compose");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);

        // Create the label
        JLabel titleLabel = new JLabel("Compose", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(titleLabel, BorderLayout.NORTH);

        // Create the panel to hold the compose components
        JPanel composePanel = new JPanel();
        composePanel.setLayout(new GridLayout(6, 2, 10, 10));
        composePanel.setBackground(Color.WHITE);

        // Create the recipient label and field
        JLabel recipientLabel = new JLabel("Recipient Email:");
        recipientField = new JTextField();
        composePanel.add(recipientLabel);
        composePanel.add(recipientField);

        // Create the sender label and field
        JLabel senderLabel = new JLabel("Sender Email:");
        senderField = new JTextField(senderEmail);
        senderField.setEditable(false);
        composePanel.add(senderLabel);
        composePanel.add(senderField);

        // Create the subject label and field
        JLabel subjectLabel = new JLabel("Subject:");
        subjectField = new JTextField();
        composePanel.add(subjectLabel);
        composePanel.add(subjectField);

        // Create the message label and field
        JLabel messageLabel = new JLabel("Message:");
        messageField = new JTextArea();
        JScrollPane messageScrollPane = new JScrollPane(messageField);
        composePanel.add(messageLabel);
        composePanel.add(messageScrollPane);

        // Create the attachment button
        attachmentButton = new JButton("Attach File");
        attachmentButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // TODO: Add code to handle file attachment
                JFileChooser fileChooser = new JFileChooser();
                int option = fileChooser.showOpenDialog(Compose.this);
                if (option == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    // TODO: Process the selected file
                }
            }
        });
        composePanel.add(attachmentButton);

        // Create the send button
        sendButton = new JButton("Send");
        sendButton.setBackground(new Color(59, 89, 182));
        sendButton.setForeground(Color.WHITE);
        sendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // TODO: Add code to send the email using App class
                String recipientEmail = recipientField.getText();
                String senderEmail = senderField.getText();
                String message = messageField.getText();
                String subject = ((JTextComponent) subjectField).getText();

                // Call the method from App class
                App.sendEmail(message, subject, recipientEmail, senderEmail);

                JOptionPane.showMessageDialog(Compose.this, "Email sent successfully!");
                dispose();
            }
        });
        composePanel.add(sendButton);

        // Add the panel to the frame
        add(composePanel, BorderLayout.CENTER);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Compose("example@example.com"));
    }
}
