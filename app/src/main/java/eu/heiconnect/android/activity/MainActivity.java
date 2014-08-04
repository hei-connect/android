package eu.heiconnect.android.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import eu.heiconnect.android.R;
import eu.heiconnect.android.webservice.login.LoginResult;
import eu.heiconnect.android.webservice.login.User;
import eu.heiconnect.android.webservice.login.LoginRequest;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        User user = new User("h09330", "eaaixh7f");
        LoginRequest request = new LoginRequest(user, new Response.Listener<LoginResult>() {
            @Override
            public void onResponse(LoginResult loginResult) {
                Log.v("MainActivity", "Yay");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.v("MainActivity", "Nay");
            }
        }
        );
        requestQueue.add(request);
        requestQueue.start();
    }
}
