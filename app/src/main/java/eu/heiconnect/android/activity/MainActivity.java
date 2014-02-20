package eu.heiconnect.android.activity;

import android.app.Activity;
import android.os.Bundle;

import eu.heiconnect.android.R;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
    }
}
