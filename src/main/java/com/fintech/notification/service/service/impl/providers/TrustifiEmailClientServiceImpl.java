package com.fintech.notification.service.service.impl.providers;

import com.fintech.notification.lib.model.request.EmailNotificationRequest;
import com.fintech.notification.lib.model.request.TrustifiRecipientEmail;
import com.fintech.notification.lib.model.request.TrustifiRequestModel;
import com.fintech.notification.lib.util.NotificationRestTemplateUtil;
import com.fintech.notification.service.service.EmailClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;


public class TrustifiEmailClientServiceImpl implements EmailClientService {

    @Autowired
    private NotificationRestTemplateUtil notificationRestTemplateUtil;

    @Value("${trustify.base.url:https://be.trustifi.com/}")
    private String trustifiBaseUrl;

    @Value("${trustify.key:}")
    private String trustifiKey;

    @Value("${trustify.secret:}")
    private String trustifiSecret;

    private final Map<String, String> header = new HashMap<>();

    private static final Logger LOGGER = LoggerFactory.getLogger(TrustifiEmailClientServiceImpl.class.getName());

    @PostConstruct
    private void init() {
        header.put("x-trustifi-key", trustifiKey);
        header.put("x-trustifi-secret", trustifiSecret);
    }

    @Override
    public void sendEmailToEmailProvider(EmailNotificationRequest emailNotificationRequest) {
        try {
            TrustifiRequestModel trustifiRequestModel = new TrustifiRequestModel();
            populateEmailFromNotificationRequest(emailNotificationRequest, trustifiRequestModel);
            trustifiRequestModel.setTitle(emailNotificationRequest.getSubject());
            trustifiRequestModel.setContent(emailNotificationRequest.getEmailContent());
            ResponseEntity<Object> responseEntity = notificationRestTemplateUtil.postForEntity(String.format("%sapi/i/v1/email", trustifiBaseUrl), trustifiRequestModel,
                    Object.class, header);
            LOGGER.info("Response from Trustifi is [{}] ", responseEntity);
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            LOGGER.error(String.format("Error occurred while sending email to provider to %s", e.getResponseBodyAsString()));
        } catch (Exception e) {
            LOGGER.error("Error occurred while sending email to provider to to ", e);
        }
    }

    private void populateEmailFromNotificationRequest(EmailNotificationRequest emailNotificationRequest, TrustifiRequestModel trustifiRequestModel) {
        for (String email:emailNotificationRequest.getRecipients()) {
            trustifiRequestModel.getRecipients().add(new TrustifiRecipientEmail(email));
        }
        for (String email:emailNotificationRequest.getBccList()) {
            trustifiRequestModel.getRecipients().add(new TrustifiRecipientEmail(email));
        }
        for (String email:emailNotificationRequest.getCcList()) {
            trustifiRequestModel.getRecipients().add(new TrustifiRecipientEmail(email));
        }
    }

}
