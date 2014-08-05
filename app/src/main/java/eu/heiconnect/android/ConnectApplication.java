package eu.heiconnect.android;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import eu.heiconnect.android.utils.Configuration;

public class ConnectApplication extends Application {

    private RequestQueue requestQueue;
    private Configuration configuration;

    @Override
    public void onCreate() {
        super.onCreate();
        configuration = new Configuration();
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.start();
    }

    @Override
    public void onTerminate() {
        requestQueue.stop();
        super.onTerminate();
    }

    public RequestQueue getRequestQueue() {
        return requestQueue;
    }

    public Configuration getConfiguration() {
        return configuration;
    }
}
