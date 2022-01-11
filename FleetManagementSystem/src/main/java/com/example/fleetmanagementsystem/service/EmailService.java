package com.example.fleetmanagementsystem.service;

import com.example.fleetmanagementsystem.domain.Transport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;


@Service
public class EmailService {

    private static final String FROM_EMAIL = "fleetmsystem@gmail.com";

    @Autowired
    private JavaMailSender emailSender;

    private void sendSimpleMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(FROM_EMAIL);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);

    }

    private void sendHTMLMessage(String to, String subject, String text) {
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(FROM_EMAIL);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text);
            mimeMessage.setContent(text, "text/html");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        emailSender.send(mimeMessage);
    }

    public void sendTransportHasBeenAssignedEmailToDriver(String driverEmailAddress, Transport transport) {
        sendSimpleMessage(driverEmailAddress, "A new transport has been assigned to you", String.format("A transport on %s  has been assigned to you.", transport.getStartDate()));
    }

    public void sendTransportHasStartedEmailToManager(String managerEmailAddress, Transport transport) {
        sendSimpleMessage(managerEmailAddress, "A transport started", String.format("The transport from %s to %s started.", transport.getOriginAddress(), transport.getDestinationAddress()));
    }

    public void sendTransportHasFinishedEmailToManager(String managerEmailAddress, Transport transport) {
        sendSimpleMessage(managerEmailAddress, "A transport finished", String.format("The transport from %s to %s finished.", transport.getOriginAddress(), transport.getDestinationAddress()));
    }

}