package eu.heiconnect.android.activity;

import android.content.Intent;
import android.os.Bundle;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import eu.heiconnect.android.BuildConfig;
import eu.heiconnect.android.ConnectApplication;
import eu.heiconnect.android.R;
import eu.heiconnect.android.webservice.config.ConfigRequest;
import eu.heiconnect.android.webservice.config.ConfigResult;

public class SplashActivity extends ConnectActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ConfigRequest request = new ConfigRequest(getConfiguration(), new SuccessListener(), new ErrorListener());
        request.setTag(this);

        getRequestQueue().add(request);
    }

    private class SuccessListener implements Response.Listener<ConfigResult> {
        @Override
        public void onResponse(ConfigResult configResult) {
            ConnectApplication application = (ConnectApplication) getApplication();
            application.getConfiguration().setApplicationMinimumVersion(configResult.getConfig().getAndroidMinimumVersion());
            application.getConfiguration().setApiBaseUrl(configResult.getConfig().getUrl());

            if (BuildConfig.VERSION_CODE >= application.getConfiguration().getApplicationMinimumVersion()) {
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            } else {
                // TODO Error message
            }
        }
    }

    private class ErrorListener implements Response.ErrorListener {
        @Override
        public void onErrorResponse(VolleyError volleyError) {
            // TODO Error messages
        }
    }
}
