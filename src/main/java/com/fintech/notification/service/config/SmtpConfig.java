package com.fintech.notification.service.config;


import com.fintech.notification.service.service.NotificationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import com.fintech.notification.service.service.impl.SmtpNotificationServiceImpl;
import java.util.Properties;

@Configuration
public class SmtpConfig {

    @Value("${smtp.username:assessment.sy@gmail.com}")
    private String smtpUsername;
    @Value("${smtp.password:Assessment2.}")
    private String smtpPassword;
    @Value("${email.service.bean:SMTP}")
    private String emailServiceBean;


    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername(smtpUsername);
        mailSender.setPassword(smtpPassword);
        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");
        return mailSender;
    }

    @Bean
    public NotificationService notificationService() {
        if (emailServiceBean.equals("SMTP"))
            return new SmtpNotificationServiceImpl();
        return null;
    }
}
