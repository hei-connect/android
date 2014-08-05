package eu.heiconnect.android.webservice.config;

import android.app.Application;

import com.android.volley.Response;

import eu.heiconnect.android.utils.Configuration;
import eu.heiconnect.android.webservice.BaseRequest;

public class ConfigRequest extends BaseRequest<ConfigResult> {

    private static String METHOD_ROUTE = "config.json";

    public ConfigRequest(Configuration configuration, Response.Listener<ConfigResult> listener, Response.ErrorListener errorListener) {
        super(ConfigResult.class, Method.GET, METHOD_ROUTE, configuration, listener, errorListener);
    }

}
