package eu.heiconnect.android.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import eu.heiconnect.android.R;
import eu.heiconnect.android.webservice.login.LoginRequest;
import eu.heiconnect.android.webservice.login.LoginResult;
import eu.heiconnect.android.webservice.login.User;

public class LoginActivity extends ConnectActivity {

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

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(loginEditText.getText()) && !TextUtils.isEmpty(passwordEditText.getText())) {
                    submitButton.setEnabled(false);
                    progressBar.animate().alpha(1);

                    performLoginRequest(loginEditText.getText().toString(), passwordEditText.getText().toString());
                } else {
                    // TODO Handle empty fields
                }
            }
        });
    }

    // ----------------------------------
    // PRIVATE METHODS
    // ----------------------------------
    private void performLoginRequest(String login, String password) {
        User user = new User(login, password);
        LoginRequest request = new LoginRequest(getConfiguration(), user, new Response.Listener<LoginResult>() {
            @Override
            public void onResponse(LoginResult loginResult) {
                progressBar.animate().alpha(0);
                submitButton.animate().alpha(0);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                // TODO Handle error properly
                submitButton.setEnabled(true);
                progressBar.animate().alpha(0);
            }
        }
        );
        request.setTag(this);
        getRequestQueue().add(request);
    }
}
