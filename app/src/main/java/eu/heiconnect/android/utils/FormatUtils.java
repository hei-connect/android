package eu.heiconnect.android.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class FormatUtils {

    // ----------------------------------
    // CONSTANTS
    // ----------------------------------
    public static final Locale LOCALE_APPLICATION = Locale.FRANCE;


    // ----------------------------------
    // PUBLIC STATIC METHODS
    // ----------------------------------
    public static SynchronizedSimpleDateFormat getTimeFormat() {
        return new SynchronizedSimpleDateFormat("HH'h'mm");
    }

    public static SynchronizedSimpleDateFormat getShortDateFormat() {
        return new SynchronizedSimpleDateFormat("dd/MM");
    }

    public static String getUserFriendlyElapsedTimeSinceDate(Date date) {
        long seconds = TimeUnit.MILLISECONDS.toSeconds(new Date().getTime() - date.getTime());

        String text = "il y a ";

        if (seconds < 0) {
            text = "à l'instant";

        } else if (TimeUnit.SECONDS.toDays(seconds) >= 1) {
            text = "le " + FormatUtils.getShortDateFormat().format(date);

        } else if (TimeUnit.SECONDS.toHours(seconds) >= 1) {
            text += String.valueOf(TimeUnit.SECONDS.toHours(seconds)) + " h";

        } else if (TimeUnit.SECONDS.toMinutes(seconds) >= 1) {
            text += String.valueOf(TimeUnit.SECONDS.toMinutes(seconds)) + " min";

        } else {
            text += String.valueOf(seconds) + " s";
        }

        return text;
    }

    public static long getDifferenceInMinutes(Date startDate, Date endDate) {
        long duration = endDate.getTime() - startDate.getTime();
        return TimeUnit.MILLISECONDS.toMinutes(duration);
    }

    // ----------------------------------
    // INNER CLASSES
    // ----------------------------------
    public static final class SynchronizedSimpleDateFormat {

        private SimpleDateFormat sdf;

        private SynchronizedSimpleDateFormat(String pattern) {
            sdf = new SimpleDateFormat(pattern, LOCALE_APPLICATION);
            sdf.setTimeZone(TimeZone.getDefault());
        }

        public synchronized String format(Date date) {
            return sdf.format(date);
        }

        public synchronized String format(long l) {
            return sdf.format(l);
        }

        public synchronized Date parse(String string) throws ParseException {
            return sdf.parse(string);
        }
    }

}
