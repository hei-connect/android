package eu.heiconnect.android.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import eu.heiconnect.android.BuildConfig;
import eu.heiconnect.android.ConnectApplication;
import eu.heiconnect.android.R;
import eu.heiconnect.android.utils.PreferencesWrapper;
import eu.heiconnect.android.webservice.config.ConfigRequest;
import eu.heiconnect.android.webservice.config.ConfigResult;

public class SplashActivity extends ConnectActivity {

    // ----------------------------------
    // CONSTANTS
    // ----------------------------------
    private static final String MARKET_URI = "market://details?id=";
    private static final String PLAY_STORE_URI = "http://play.google.com/store/apps/details?id=";

    // ----------------------------------
    // LIFE CYCLE
    // ----------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ConfigRequest request = new ConfigRequest(getConfiguration(), new SuccessListener(), new ErrorListener());
        request.setTag(this);

        getRequestQueue().add(request);
    }


    // ----------------------------------
    // INNER CLASSES
    // ----------------------------------
    private class SuccessListener implements Response.Listener<ConfigResult> {
        @Override
        public void onResponse(ConfigResult configResult) {
            ConnectApplication application = (ConnectApplication) getApplication();
            application.getConfiguration().setApplicationMinimumVersion(configResult.getConfig().getAndroidMinimumVersion());
            application.getConfiguration().setApiBaseUrl(configResult.getConfig().getUrl());

            if (BuildConfig.VERSION_CODE >= application.getConfiguration().getApplicationMinimumVersion()) {
                // If the user is already logged in, go to main activity
                PreferencesWrapper preferences = new PreferencesWrapper(SplashActivity.this);
                Intent intent;
                if (preferences.getUserName() != null && preferences.getUserToken() != null) {
                    intent = new Intent(SplashActivity.this, MainActivity.class);
                } else {
                    intent = new Intent(SplashActivity.this, LoginActivity.class);
                }
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, 0);
                finish();
            } else {
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(SplashActivity.this);
                dialogBuilder.setTitle(getString(R.string.splash_outdated_title));
                dialogBuilder.setMessage(getString(R.string.splash_outdated_message));
                dialogBuilder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(MARKET_URI + getPackageName())));
                        } catch (android.content.ActivityNotFoundException anfe) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(PLAY_STORE_URI + getPackageName())));
                        }
                        finish();
                    }
                });
                dialogBuilder.setNegativeButton(getString(R.string.generic_close), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                dialogBuilder.show();
            }
        }
    }

    private class ErrorListener implements Response.ErrorListener {
        @Override
        public void onErrorResponse(VolleyError volleyError) {
            showGenericErrorAlertDialog(volleyError);
        }
    }
}
