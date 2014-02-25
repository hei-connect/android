package eu.heiconnect.android.webservice;

import com.android.volley.VolleyError;

import eu.heiconnect.android.model.Error;

public class HeiConnectError extends VolleyError {

    private Error resultError;

    public HeiConnectError(Error resultError) {
        this.resultError = resultError;
    }
}