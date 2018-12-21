package com.sd.his.utill;

public interface IEmailUtil {
     Boolean sendEmail(
            String emailSender,
            String emailRecipient,
            String emailSubject,
            String emailHtmlBody);
}
