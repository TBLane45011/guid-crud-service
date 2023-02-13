package com.win.guid.crud.service.services;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

/**
 * To send mail notification
 *
 * @author Tim Lane
 */
@Service
public class EmailService {

    private final Logger mLogger = LogManager.getLogger(EmailService.class);

    @Autowired
    private JavaMailSender emailSender;

    /**
     * <b>sendMail</b> method will send mail notification to users
     *
     * @param pFrom    - mail from user
     * @param pSendTos  - mail send to users
     * @param pSubject - mail subject
     * @param pMessage - mail body content
     * @throws MessagingException -
     */
    public void sendMail(final String pFrom,
                         final String[] pSendTos,
                         final String pSubject,
                         final String pMessage)
            throws MessagingException {
        final MimeMessage lMessage = emailSender.createMimeMessage();
        final MimeMessageHelper lHelper = new MimeMessageHelper(lMessage,
                true);
        lHelper.setFrom(pFrom);
        for (String lSendTo : pSendTos) {
            mLogger.debug("Mail Id of recipients {}...", lSendTo);
            lHelper.addTo(lSendTo);
        }
        lHelper.setSubject(pSubject);
        lHelper.setText(pMessage, true);
        emailSender.send(lMessage);
    }


}
