package eu.heiconnect.android.webservice.config;

import com.android.volley.Response;

import eu.heiconnect.android.webservice.BaseRequest;

public class ConfigRequest extends BaseRequest<ConfigResult> {

    private static String METHOD_ROUTE = "config.json";

    public ConfigRequest(Response.Listener<ConfigResult> listener, Response.ErrorListener errorListener) {
        super(ConfigResult.class, Method.GET, METHOD_ROUTE, listener, errorListener);
    }

}
