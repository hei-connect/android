package eu.heiconnect.android.webservice.login;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyLog;
import com.fasterxml.jackson.core.JsonProcessingException;

import eu.heiconnect.android.activity.ConnectActivity;
import eu.heiconnect.android.webservice.BaseRequest;

public class LoginRequest extends BaseRequest<LoginResult> {

    // ----------------------------------
    // CONSTANTS
    // ----------------------------------
    private static String METHOD_ROUTE = "login.json";

    // ----------------------------------
    // ATTRIBUTES
    // ----------------------------------
    private final User user;

    // ----------------------------------
    // CONSTRUCTORS
    // ----------------------------------
    public LoginRequest(User user) {
        super();
        this.user = user;
    }

    public LoginRequest(ConnectActivity activity, User user, Response.Listener<LoginResult> listener, Response.ErrorListener errorListener) {
        super(LoginResult.class, Method.POST, METHOD_ROUTE, activity, listener, errorListener);

        this.user = user;
    }

    // ----------------------------------
    // OVERRIDEN METHODS
    // ----------------------------------
    @Override
    public byte[] getBody() throws AuthFailureError {
        try {
            return mapper.writeValueAsBytes(user);
        } catch (JsonProcessingException e) {
            VolleyLog.e("JsonProcessingException", user, e);
            return null;
        }
    }
}
