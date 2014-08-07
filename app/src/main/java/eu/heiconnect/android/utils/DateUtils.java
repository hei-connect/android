package eu.heiconnect.android.utils;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DateUtils {

    public static long getDifferenceInMinutes(Date startDate, Date endDate) {
        long duration = endDate.getTime() - startDate.getTime();
        return TimeUnit.MILLISECONDS.toMinutes(duration);
    }

    public static long getDifferenceInHours(Date startDate, Date endDate) {
        long duration = endDate.getTime() - startDate.getTime();
        return TimeUnit.MILLISECONDS.toHours(duration);
    }

}
