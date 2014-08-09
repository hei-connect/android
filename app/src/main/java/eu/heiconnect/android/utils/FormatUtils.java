package eu.heiconnect.android.utils;

import android.content.Context;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import eu.heiconnect.android.R;

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

    public static String getUserFriendlyElapsedTimeSinceDate(Date date, Context context) {
        long seconds = TimeUnit.MILLISECONDS.toSeconds(new Date().getTime() - date.getTime());

        String text = context.getString(R.string.generic_time_about) + " ";

        if (seconds < 0) {
            text = context.getString(R.string.generic_time_now);

        } else if (TimeUnit.SECONDS.toDays(seconds) >= 1) {
            text = context.getString(R.string.generic_time_on, FormatUtils.getShortDateFormat().format(date));

        } else if (TimeUnit.SECONDS.toHours(seconds) >= 1) {
            text += context.getString(R.string.generic_time_hour, TimeUnit.SECONDS.toHours(seconds));

        } else if (TimeUnit.SECONDS.toMinutes(seconds) >= 1) {
            text += context.getString(R.string.generic_time_minute, TimeUnit.SECONDS.toMinutes(seconds));

        } else {
            text += context.getString(R.string.generic_time_second, seconds);
        }

        return text;
    }

    public static SynchronizedNumberFormat getTwoDecimalNumberFormat() {
        return new SynchronizedNumberFormat("##0.00;-#");
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
    }

    public static final class SynchronizedNumberFormat {

        private NumberFormat nf;

        private SynchronizedNumberFormat(String pattern) {
            nf = new DecimalFormat(pattern);
        }

        public synchronized String format(Number value) {
            return nf.format(value);
        }
    }
}
