package eu.heiconnect.android;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.crashlytics.android.Crashlytics;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Logger;
import com.google.android.gms.analytics.Tracker;
import com.newrelic.agent.android.NewRelic;

import eu.heiconnect.android.utils.Configuration;
import eu.heiconnect.android.webservice.OkHttpStack;

public class ConnectApplication extends Application {

    // ----------------------------------
    // ATTRIBUTES
    // ----------------------------------
    private RequestQueue requestQueue;
    private Configuration configuration;
    private Tracker tracker;

    // ----------------------------------
    // LIFE CYCLE
    // ----------------------------------
    @Override
    public void onCreate() {
        super.onCreate();
        Crashlytics.start(this);
        NewRelic.withApplicationToken(BuildConfig.NEWRELIC_KEY).start(this);
        getTracker();

        configuration = new Configuration();
        requestQueue = Volley.newRequestQueue(this, new OkHttpStack());
        requestQueue.start();

    }

    @Override
    public void onTerminate() {
        requestQueue.stop();
        super.onTerminate();
    }

    // ----------------------------------
    // PUBLIC METHODS
    // ----------------------------------
    public RequestQueue getRequestQueue() {
        return requestQueue;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public synchronized Tracker getTracker() {
        if (tracker == null) {
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);

            if (BuildConfig.DEBUG) {
                analytics.setDryRun(true);
                analytics.getLogger().setLogLevel(Logger.LogLevel.VERBOSE);
            }

            tracker = analytics.newTracker(R.xml.tracker);
        }

        return tracker;
    }
}
