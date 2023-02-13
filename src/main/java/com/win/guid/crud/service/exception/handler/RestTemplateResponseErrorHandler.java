package com.win.guid.crud.service.exception.handler;

import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus.Series;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

/**
 * Rest Template Response Error Handler
 *
 * @author Tim Lane
 *
 */
@Component
public class RestTemplateResponseErrorHandler implements ResponseErrorHandler {

    private final Logger mLogger = LogManager.getLogger(RestTemplateResponseErrorHandler.class);

    /**
     * <b>hasError</b> - Check for error
     *
     * @param pResponse - the Response
     * @return boolean
     * @throws IOException -
     */
    @Override
    public boolean hasError(final ClientHttpResponse pResponse) throws IOException {
        return (pResponse.getStatusCode().series() == Series.CLIENT_ERROR || pResponse.getStatusCode().series() == Series.SERVER_ERROR);
    }

    /**
     * <b>handleError</b> - Handles the error
     *
     * @param pResponse - the Response
     * @throws IOException -
     */
    @Override
    public void handleError(final ClientHttpResponse pResponse) throws IOException {
        if (pResponse.getStatusCode().series() == Series.SERVER_ERROR) {
            mLogger.error("Response -> StatusCode {}", pResponse.getStatusCode());
        } else if (pResponse.getStatusCode().series() == Series.CLIENT_ERROR) {
            mLogger.error("Response -> StatusCode {}", pResponse.getStatusCode());
        }
    }


}
