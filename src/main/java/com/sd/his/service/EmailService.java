package com.sd.his.service;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClient;
import com.amazonaws.services.simpleemail.model.*;
import com.sd.his.enums.ModuleEnum;
import com.sd.his.model.EmailConfiguration;
import com.sd.his.model.Prefix;
import com.sd.his.repository.EmailConfigurationRepository;
import com.sd.his.repository.PrefixRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmailService {

    @Autowired
    EmailConfigurationRepository emailConfigurationRepository;


    private final Logger logger = LoggerFactory.getLogger(EmailService.class);

    private AmazonSimpleEmailServiceClient client;


    public EmailService() {
        initialize();
    }

    private void initialize() {
        try {

            EmailConfiguration config = emailConfigurationRepository.findBySystemDefaultTrue();
            AWSCredentials credentials = new BasicAWSCredentials(config.getSesAccessKey(), config.getSesSecretKey());
            client = new AmazonSimpleEmailServiceClient(credentials);
        } catch (Exception t) {
            logger.error("Error while configuration of email service");
        }

    }


    public Boolean sendEmail(
            String emailSender,
            String emailRecipient,
            String emailSubject,
            String emailHtmlBody) {

        try {
            SendEmailRequest request = new SendEmailRequest().withSource(emailSender);
            List<String> toAddresses = new ArrayList<>();
            toAddresses.add(emailRecipient);
            Destination dest = new Destination().withToAddresses(toAddresses);
            request.setDestination(dest);
            Content subjContent = new Content().withData(emailSubject);
            Message msg = new Message().withSubject(subjContent);
            Content htmlContent = new Content().withData(emailHtmlBody);
            Body body = new Body().withHtml(htmlContent);
            msg.setBody(body);
            request.setMessage(msg);
            client.sendEmail(request);
            return true;
        } catch (Exception e) {
            logger.error("Error while sending email");
            return false;
        }
    }


}
