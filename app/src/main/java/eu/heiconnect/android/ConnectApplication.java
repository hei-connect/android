package eu.heiconnect.android;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import eu.heiconnect.android.utils.Configuration;
import eu.heiconnect.android.webservice.OkHttpStack;

public class ConnectApplication extends Application {

    // ----------------------------------
    // ATTRIBUTES
    // ----------------------------------
    private RequestQueue requestQueue;
    private Configuration configuration;

    // ----------------------------------
    // LIFE CYCLE
    // ----------------------------------
    @Override
    public void onCreate() {
        super.onCreate();
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
}
