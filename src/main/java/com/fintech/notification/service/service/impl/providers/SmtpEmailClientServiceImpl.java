package com.fintech.notification.service.service.impl.providers;

import com.fintech.notification.lib.model.request.EmailNotificationRequest;
import com.fintech.notification.service.service.EmailClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import javax.mail.internet.MimeMessage;

public class SmtpEmailClientServiceImpl implements EmailClientService {

    @Autowired
    private JavaMailSender emailSender;
    private static final Logger LOGGER = LoggerFactory.getLogger(SmtpEmailClientServiceImpl.class.getName());

    @Override
    public void sendEmailToEmailProvider(EmailNotificationRequest emailNotificationRequest) {
        try {
            LOGGER.info("Received email request body [{}]", emailNotificationRequest);
            MimeMessage messag = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(messag, false);
            helper.setTo(emailNotificationRequest.getRecipients().toArray(new String[]{}));
            helper.setCc(emailNotificationRequest.getCcList().toArray(new String[]{}));
            helper.setBcc(emailNotificationRequest.getBccList().toArray(new String[]{}));
            helper.setFrom(emailNotificationRequest.getSender());
            helper.setText(emailNotificationRequest.getEmailContent(), true);
            helper.setSubject(emailNotificationRequest.getSubject());
            emailSender.send(messag);
        } catch (Exception e) {
            LOGGER.error("Error occurred while sending mail due to ", e);
        }
    }
}
