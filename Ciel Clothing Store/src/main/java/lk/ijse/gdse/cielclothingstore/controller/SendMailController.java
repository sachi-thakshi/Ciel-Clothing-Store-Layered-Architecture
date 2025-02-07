package lk.ijse.gdse.cielclothingstore.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class SendMailController {

    @FXML
    private TextField txtToEmail;
    @FXML
    private TextField txtFromEmail;
    @FXML
    private TextField txtSubject;
    @FXML
    private TextArea txtBody;


    public void setCustomerEmail(String email) {
        txtToEmail.setText(email);
    }


    @FXML
    void sendGmail(ActionEvent event) {
        // Get the values from the text fields
        String toEmail = txtToEmail.getText();
        String fromEmail = txtFromEmail.getText();
        String subject = txtSubject.getText();
        String body = txtBody.getText();

        // Validate the recipient email
        if (!isValidEmail(toEmail)) {
            new Alert(Alert.AlertType.WARNING, "Invalid recipient email address").show();
            return;
        }

        // Ensure that subject and body are not empty
        if (subject.isEmpty() || body.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Subject and body are required").show();
            return;
        }

        // Call the method to send the email
        sendEmailWithGmail(fromEmail, toEmail, subject, body);
    }

    // Helper method to send the email via Gmail's SMTP server
    public void sendEmailWithGmail(String fromEmail, String toEmail, String subject, String body) {
        final String PASSWORD = System.getenv("EMAIL_PASSWORD"); // Get password from environment variable
        if (PASSWORD == null || PASSWORD.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Email password not configured").show();
            return;
        }

        // Set up properties for Gmail SMTP server
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        // Create session with authentication
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, PASSWORD);
            }
        });

        try {
            // Create a message object
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject(subject);
            message.setText(body);

            // Send the email
            Transport.send(message);
            new Alert(Alert.AlertType.INFORMATION, "Email sent successfully").show();
        } catch (AddressException e) {
            // Show error if the email address is invalid
            new Alert(Alert.AlertType.ERROR, "Invalid email address").show();
            e.printStackTrace();
        } catch (MessagingException e) {
            // Show error if the email failed to send
            new Alert(Alert.AlertType.ERROR, "Failed to send email").show();
            e.printStackTrace();
        }
    }

    // Helper method to validate email format using a regular expression
    private boolean isValidEmail(String email) {
        return email != null && email.matches("^[\\w.%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$");
    }
}
