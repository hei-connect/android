package eu.heiconnect.android.webservice.config;

import com.android.volley.Response;

import eu.heiconnect.android.utils.Configuration;
import eu.heiconnect.android.webservice.BaseRequest;

public class ConfigRequest extends BaseRequest<ConfigResult> {

    // ----------------------------------
    // CONSTANTS
    // ----------------------------------
    private static String METHOD_ROUTE = "config.json";

    // ----------------------------------
    // CONSTRUCTORS
    // ----------------------------------
    public ConfigRequest(Configuration configuration, Response.Listener<ConfigResult> listener, Response.ErrorListener errorListener) {
        super(ConfigResult.class, Method.GET, METHOD_ROUTE, configuration, listener, errorListener);
    }

}
