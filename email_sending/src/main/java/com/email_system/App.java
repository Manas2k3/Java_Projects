package com.email_system;

import java.io.File;
import java.net.URI;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JTextField;

public class App {
    public static void main(String[] args) {
        System.out.println("Preparing to send message...");
        String message = Compose.messageField.getText();
        String subject = ((AbstractButton) Compose.subjectField).getText();
        String to = Compose.recipientField.getText();
        String from = Login_Page.emailField.getText();

        // sendEmail(message, subject, to, from);
        sendAttach(message, subject, to, from);
    }

    // This is responsible for sending the message with attachment
    private static void sendAttach(String message, String subject, String to, String from) {
        String host = "smtp.gmail.com";

        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("your-email@gmail.com", "your-password");
            }
        });

        session.setDebug(true);

        MimeMessage mimeMessage = new MimeMessage(session);

        try {
            mimeMessage.setFrom(new InternetAddress(from));
            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            mimeMessage.setSubject(subject);

            MimeMultipart mimeMultipart = new MimeMultipart();

            MimeBodyPart textMime = new MimeBodyPart();
            textMime.setText(message);
            mimeMultipart.addBodyPart(textMime);

            JButton attachmentButton = Compose.attachmentButton;
            String attachmentFilePath = ""; // Variable to hold the attachment file path

            if (attachmentButton != null && attachmentFilePath != null && !attachmentFilePath.isEmpty()) {
                MimeBodyPart fileMime = new MimeBodyPart();
                File file = new File(attachmentFilePath);
                fileMime.attachFile(file);
                mimeMultipart.addBodyPart(fileMime);
            }

            mimeMessage.setContent(mimeMultipart);

            Transport.send(mimeMessage);

            System.out.println("Sent success...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // This is responsible for sending email
    static void sendEmail(String message, String subject, String to, String from) {

        // Variable for mail
        String host = "smtp.gmail.com";

        // Get the system properties
        Properties properties = System.getProperties();
        System.out.println("PROPERTIES " + properties);

        // Setting important information to properties object

        // Host set
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        // Step 1: Get the session object
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(Login_Page.emailField.getText(), Login_Page.passwordField.getText());
            }
        });

        session.setDebug(true);

        // Step 2: Compose the message [text, multi-media]

        MimeMessage mimeMessage = new MimeMessage(session);
        
        try {
            // From email
            mimeMessage.setFrom(new InternetAddress(from));

            // Adding recipient to message
            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            // Adding subject to message
            mimeMessage.setSubject(subject);

            // Adding text to message
            mimeMessage.setText(message);

            // Send

            // Step 3: Send the message using Transport class
            Transport.send(mimeMessage);

            System.out.println("Sent success...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

