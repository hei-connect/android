package eu.heiconnect.android.webservice;

import com.android.volley.Response;

import eu.heiconnect.android.activity.ConnectActivity;
import eu.heiconnect.android.utils.Configuration;

public abstract class LoggedInRequest<T> extends BaseRequest<T> {

    // ----------------------------------
    // CONSTANTS
    // ----------------------------------
    private static final String TOKEN_ROUTE = "%s?token=%s";

    // ----------------------------------
    // CONSTRUCTORS
    // ----------------------------------
    public LoggedInRequest(Class<T> clazz, int method, String methodUrl, String token, ConnectActivity activity, Response.Listener<T> listener, Response.ErrorListener errorListener) {
        super(clazz, method, appendTokenToRoute(methodUrl, token), activity, listener, errorListener);
    }

    // ----------------------------------
    // PRIVATE METHODS
    // ----------------------------------
    private static String appendTokenToRoute(String methodUrl, String token) {
        return String.format(TOKEN_ROUTE, methodUrl, token);
    }
}
