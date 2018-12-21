package com.sd.his.service;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClient;
import com.sd.his.model.Branch;
import com.sd.his.model.EmailConfiguration;
import com.sd.his.repository.EmailConfigurationRepository;
import com.sd.his.utill.AmazonSESUtil;
import com.sd.his.utill.IEmailUtil;
import com.sd.his.utill.SMTPUtil;
import com.sd.his.wrapper.EmailWrapper;
import com.sd.his.wrapper.request.EmailTemplateWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Component
public class EmailService implements ApplicationContextAware {


    private static EmailService INSTANCE;
    private EmailConfiguration configuration;
    private IEmailUtil emailUtil;
    private static ApplicationContext ctx;

    /*private EmailService(){}*/

    private void initializeDefault() {
        ApplicationContext appCtx = EmailService
                .getApplicationContext();
        EmailConfigurationRepository emailConfigurationRepository = (EmailConfigurationRepository) appCtx.getBean(EmailConfigurationRepository.class);
      //  System.out.println("test branc..."+ branchService.totalBranches());
         configuration = emailConfigurationRepository.findBySystemDefaultTrue();
        if(configuration.getServerType().equalsIgnoreCase("SES")) {
            emailUtil = AmazonSESUtil.getInstance(false);
        }
        else {
            emailUtil = SMTPUtil.getInstance(false);
        }
    }

   public static synchronized EmailService getInstance(Boolean isUpdated) {
        if(INSTANCE == null || isUpdated) {
            INSTANCE = new EmailService();

        }
        return INSTANCE;

    }

@Transactional
    public  Boolean sendEmail(EmailWrapper email) {
            initializeDefault();
    return emailUtil.sendEmail(configuration.getSenderEmail(),email.getRecepient(), email.getSubject(), email.getContent());
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ctx = applicationContext;
    }
    public static ApplicationContext getApplicationContext() {
        return ctx;
    }

}
