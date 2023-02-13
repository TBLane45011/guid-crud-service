package com.win.guid.crud.service.common;

import org.springframework.stereotype.Service;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

/**
 * Commonly used utilities
 *
 * @author Tim Lane
 *
 */
@Service
public class Utility {

    /**
     * This method is to return the Current Date Time
     *
     * @return (Date) - returns current Date time in Date type
     */
    public static Date getCurrentDateTime() throws Exception {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss:SSSSS");
        String strDate = dateFormat.format(new Date());
        return dateFormat.parse(strDate);
    }

}
