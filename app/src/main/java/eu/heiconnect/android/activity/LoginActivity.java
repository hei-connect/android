package eu.heiconnect.android.activity;

import android.animation.Animator;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import eu.heiconnect.android.R;
import eu.heiconnect.android.utils.PreferencesWrapper;
import eu.heiconnect.android.webservice.HeiConnectError;
import eu.heiconnect.android.webservice.login.LoginRequest;
import eu.heiconnect.android.webservice.login.LoginResult;
import eu.heiconnect.android.webservice.login.User;

public class LoginActivity extends ConnectActivity {

    // ----------------------------------
    // CONSTANTS
    // ----------------------------------
    private static final int BAD_CREDENTIALS_ERROR = 20;
    private static final int NOT_SIGNED_UP_ERROR = 21;
    private static final String HEI_CONNECT_WEB_URL = "http://www.hei-connect.eu";

    // ----------------------------------
    // ATTRIBUTES
    // ----------------------------------
    private ProgressBar progressBar;
    private EditText loginEditText;
    private EditText passwordEditText;
    private Button submitButton;

    // ----------------------------------
    // LIFE CYCLE
    // ----------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progressBar = (ProgressBar) findViewById(R.id.progressbar_login_loader);
        loginEditText = (EditText) findViewById(R.id.edittext_login);
        passwordEditText = (EditText) findViewById(R.id.edittext_password);
        submitButton = (Button) findViewById(R.id.button_submit_login);
        submitButton.setOnClickListener(new OnSubmitClickedListener());
    }

    // ----------------------------------
    // PRIVATE METHODS
    // ----------------------------------
    private void performLoginRequest(String login, String password) {
        final User user = new User(login, password);
        LoginRequest request = new LoginRequest(getConfiguration(), user, new Response.Listener<LoginResult>() {
            @Override
            public void onResponse(LoginResult loginResult) {
                progressBar.animate().alpha(0);

                PreferencesWrapper preferences = new PreferencesWrapper(LoginActivity.this);
                preferences.putUserToken(loginResult.getUser().getApiToken());
                preferences.putUserName(loginResult.getUser().getEcampusId());

                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                submitButton.setEnabled(true);
                progressBar.animate().alpha(0);

                if (volleyError instanceof HeiConnectError) {
                    eu.heiconnect.android.webservice.Error error = ((HeiConnectError) volleyError).getResultError();

                    if (error.getCode() == BAD_CREDENTIALS_ERROR) {
                        Toast.makeText(LoginActivity.this, getString(R.string.login_bad_credentials_title), Toast.LENGTH_LONG).show();
                    } else if (error.getCode() == NOT_SIGNED_UP_ERROR) {
                        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(LoginActivity.this);
                        alertBuilder.setTitle(getString(R.string.login_not_signed_up_title));
                        alertBuilder.setMessage(error.getMessage());
                        alertBuilder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(HEI_CONNECT_WEB_URL)));
                            }
                        });
                        alertBuilder.setNegativeButton(R.string.generic_close, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Do nothing
                            }
                        });
                        alertBuilder.show();
                    } else {
                        showGenericErrorAlertDialog(volleyError);
                    }
                } else {
                    showGenericErrorAlertDialog(volleyError);
                }
            }
        }
        );
        request.setTag(this);
        getRequestQueue().add(request);
    }

    // ----------------------------------
    // INNER CLASSES
    // ----------------------------------
    private class OnSubmitClickedListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (!TextUtils.isEmpty(loginEditText.getText()) && !TextUtils.isEmpty(passwordEditText.getText())) {
                submitButton.setEnabled(false);
                progressBar.animate().alpha(1);

                performLoginRequest(loginEditText.getText().toString(), passwordEditText.getText().toString());
            } else {
                int delta = getResources().getDimensionPixelOffset(R.dimen.small);

                PropertyValuesHolder pvhTranslateX = PropertyValuesHolder.ofKeyframe("translationX",
                        Keyframe.ofFloat(0f, 0f),
                        Keyframe.ofFloat(.10f, -delta),
                        Keyframe.ofFloat(.26f, delta),
                        Keyframe.ofFloat(.42f, -delta),
                        Keyframe.ofFloat(.58f, delta),
                        Keyframe.ofFloat(.74f, -delta),
                        Keyframe.ofFloat(.90f, delta),
                        Keyframe.ofFloat(1f, 0f)
                );

                Animator nopeAnimator;
                if (TextUtils.isEmpty(loginEditText.getText())) {
                    loginEditText.requestFocus();
                    nopeAnimator = ObjectAnimator.ofPropertyValuesHolder(loginEditText, pvhTranslateX);
                } else {
                    passwordEditText.requestFocus();
                    nopeAnimator = ObjectAnimator.ofPropertyValuesHolder(passwordEditText, pvhTranslateX);
                }
                nopeAnimator.setDuration(getResources().getInteger(android.R.integer.config_mediumAnimTime));
                nopeAnimator.start();
            }
        }
    }
}
