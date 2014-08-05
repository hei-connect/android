package eu.heiconnect.android.activity;

import android.app.Activity;

import com.android.volley.RequestQueue;

import eu.heiconnect.android.ConnectApplication;
import eu.heiconnect.android.utils.Configuration;

public abstract class ConnectActivity extends Activity {

    // ----------------------------------
    // LIFE CYCLE
    // ----------------------------------
    @Override
    protected void onStop() {
        getRequestQueue().cancelAll(this);
        super.onStop();
    }

    // ----------------------------------
    // PUBLIC METHODS
    // ----------------------------------
    protected Configuration getConfiguration() {
        ConnectApplication application = (ConnectApplication) getApplication();
        return application.getConfiguration();
    }

    protected RequestQueue getRequestQueue() {
        ConnectApplication application = (ConnectApplication) getApplication();
        return application.getRequestQueue();
    }
}
