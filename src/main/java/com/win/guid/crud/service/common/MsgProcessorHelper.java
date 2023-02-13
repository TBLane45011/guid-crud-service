package com.win.guid.crud.service.common;

import com.win.guid.crud.service.exception.handler.RestTemplateResponseErrorHandler;
import com.win.guid.crud.service.services.EmailService;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.mail.MessagingException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Helper component for message processor.
 *
 * @author Tim Lane
 *
 */

@Service
public class MsgProcessorHelper {

    /**
     * the logger
     */
    private Logger mLogger = LogManager.getLogger(com.win.guid.crud.service.common.MsgProcessorHelper.class);

    @Autowired
    private EmailService mEmailService;

    @Value("${win.mail.notification.from}")
    private String mEmailFrom;

    @Value("${win.mail.notification.to}")
    private String[] mEmailTo;

    @Value("${win.environment}")
    private String mEnvironment;

    /**
     * <b>sendErrorEmail</b> - Sends the error emailWIN_MAIL_NOTIFICATION_TO
     *
     * @param pException - the Exception
     * @param pWiseMsgPayload - the Wise message payload
     */
    public void sendExceptionErrorEmail(final Exception pException,
                               final String pWiseMsgPayload) {
        final StringWriter lStrWriter = new StringWriter();
        final PrintWriter lPrintWriter = new PrintWriter(lStrWriter);
        pException.printStackTrace(lPrintWriter);
        final String lExceptionStr = lStrWriter.toString();
        int lEndIndex = 250;
        if (lExceptionStr.length() < 250) {
            lEndIndex = lExceptionStr.length();
        }

        StringBuilder lMessage = new StringBuilder("<b>Error Message</b> - ").append(pException.getMessage()).append("<br /><b>Exception Stack Trace</b> - ");
        lMessage.append(lExceptionStr.substring(0, lEndIndex)).append("<br /><b>Error and Request</b> - <textarea rows=\"30\" cols=\"75\" readonly>");
        lMessage.append(pWiseMsgPayload).append("</textarea>");

        // Send mail
        try {
            final String lSubject = mEnvironment + " | ERROR Notification - Guid Crud Service";
            mEmailService.sendMail(mEmailFrom, mEmailTo, lSubject, lMessage.toString());
        } catch (final MessagingException lMessagingException) {
            mLogger.error(lMessagingException.getClass(), lMessagingException);
        }
    }

    /**
     ** <b>sendErrorEmail</b> - Sends the error emailWIN_MAIL_NOTIFICATION_TO
     ** @param pWiseMsgPayload - the Wise message payload
     **/
    public void sendErrorEmail(final String pWiseMsgPayload) {

        StringBuilder lMessage = new StringBuilder("<br /><b>Error and Request</b> - <textarea rows=\"30\" cols=\"75\" readonly>");
        lMessage.append(pWiseMsgPayload).append("</textarea>");

        // Send mail
        try {
            final String lSubject = mEnvironment + " | ERROR Notification - Guid Crud Service";
            mEmailService.sendMail(mEmailFrom, mEmailTo, lSubject, lMessage.toString());
        } catch (final MessagingException lMessagingException) {
            mLogger.error(lMessagingException.getClass(), lMessagingException);
        }
    }

    /**
     * <b>restTemplateWT</b> - the rest template for WT
     *
     * @param pBuilder - the Rest Template Builder
     * @return RestTemplate
     */
    @Bean
    public RestTemplate restTemplateWT(final RestTemplateBuilder pBuilder) {
        return pBuilder.errorHandler(new RestTemplateResponseErrorHandler()).build();
    }



}



