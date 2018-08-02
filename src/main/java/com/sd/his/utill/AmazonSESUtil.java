package com.sd.his.utill;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * @author    : Irfan Nasim
 * @Date      : 11-Jun-18
 * @version   : ver. 1.0.0
 *
 * ________________________________________________________________________________________________
 *
 *  Developer				Date		     Version		Operation		Description
 * ________________________________________________________________________________________________
 *
 *
 * ________________________________________________________________________________________________
 *
 * @Project   : HIS
 * @Package   : com.sd.his.utill
 * @FileName  : AmazonSESUtil
 *
 * Copyright ©
 * SolutionDots,
 * All rights reserved.
 *
 */
public final class AmazonSESUtil {

    private AmazonSimpleEmailServiceClient client;
    private static final Logger logger = LoggerFactory.getLogger(AmazonSESUtil.class);

//    @Autowired
//    private EmailConfigurationService emailConfigurationService;
//
//    public AmazonSESUtil() {
//    }
//
//    public AmazonSESUtil(EmailConfiguration configuration) {
//        try {
//            AWSCredentials credentials = new BasicAWSCredentials(configuration.getSesAccessKey(), configuration.getSesSecretKey());
//            client = new AmazonSimpleEmailServiceClient(credentials);
//        } catch (Exception ex) {
//            logger.error("Error creating AmazonEmailClient:", ex.fillInStackTrace());
//        }
//    }
//
//    public Boolean sendEmail(
//            String emailSender,
//            String emailRecipient,
//            String emailSubject,
//            String emailHtmlBody) {
//
//        try {
//            SendEmailRequest request = new SendEmailRequest().withSource(emailSender);
//            List<String> toAddresses = new ArrayList<>();
//            toAddresses.add(emailRecipient);
//            Destination dest = new Destination().withToAddresses(toAddresses);
//            request.setDestination(dest);
//            Content subjContent = new Content().withData(emailSubject);
//            Message msg = new Message().withSubject(subjContent);
//
//            Content htmlContent = new Content().withData(emailHtmlBody);
//            Body body = new Body().withHtml(htmlContent);
//            msg.setBody(body);
//            request.setMessage(msg);
//            client.sendEmail(request);
//            return true;
//        } catch (Exception e) {
//            return false;
//        }
//    }
//
//    private static AmazonSimpleEmailServiceClient createConnection(String accessKey, String secretKey) {
//        AmazonSimpleEmailServiceClient client = null;
//        try {
//
//            AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
//            client = new AmazonSimpleEmailServiceClient(credentials);
//        } catch (Exception ex) {
//            logger.error("Exception Occurred while Fetching List of Users: ", ex.fillInStackTrace());
//        }
//        return client;
//    }


}
