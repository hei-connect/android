package eu.heiconnect.android.activity;

import android.content.Intent;
import android.os.Bundle;

import eu.heiconnect.android.R;
import eu.heiconnect.android.utils.PreferencesWrapper;

public class MainActivity extends ConnectActivity {


    // ----------------------------------
    // LIFE CYCLE
    // ----------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Check that the user is well logged in
        PreferencesWrapper preferences = new PreferencesWrapper(this);
        if (preferences.getUserName() == null || preferences.getUserToken() == null) {
            preferences.putUserName(null);
            preferences.putUserToken(null);
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
