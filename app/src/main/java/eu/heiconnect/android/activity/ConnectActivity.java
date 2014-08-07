package eu.heiconnect.android.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;

import eu.heiconnect.android.ConnectApplication;
import eu.heiconnect.android.R;
import eu.heiconnect.android.utils.Configuration;
import eu.heiconnect.android.webservice.HeiConnectError;

public abstract class ConnectActivity extends Activity {

    // ----------------------------------
    // CONSTANTS
    // ----------------------------------
    private static final int GLOBAL_MAINTENANCE_ERROR_CODE = 10;
    private static final int LOGIN_MAINTENANCE_ERROR_CODE = 22;

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
    public Configuration getConfiguration() {
        ConnectApplication application = (ConnectApplication) getApplication();
        return application.getConfiguration();
    }

    // ----------------------------------
    // PROTECTED METHODS
    // ----------------------------------
    protected RequestQueue getRequestQueue() {
        ConnectApplication application = (ConnectApplication) getApplication();
        return application.getRequestQueue();
    }

    protected void showGenericErrorAlertDialog(VolleyError volleyError) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        if (volleyError instanceof HeiConnectError) {
            eu.heiconnect.android.webservice.Error error = ((HeiConnectError) volleyError).getResultError();
            if (error.getCode() == GLOBAL_MAINTENANCE_ERROR_CODE || error.getCode() == LOGIN_MAINTENANCE_ERROR_CODE) {
                dialogBuilder.setTitle(getString(R.string.splash_maintenance_title));
            } else {
                dialogBuilder.setTitle(getString(R.string.generic_error));
            }
            dialogBuilder.setMessage(error.getMessage());
        } else {
            dialogBuilder.setTitle(getString(R.string.generic_error));
            dialogBuilder.setMessage(volleyError.getClass().getSimpleName());
        }
        dialogBuilder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do nothing
            }
        });
        dialogBuilder.show();
    }
}
