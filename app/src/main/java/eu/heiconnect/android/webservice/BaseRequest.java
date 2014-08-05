package eu.heiconnect.android.webservice;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import eu.heiconnect.android.BuildConfig;
import eu.heiconnect.android.utils.Configuration;

public abstract class BaseRequest<T> extends Request<T> {

    // ----------------------------------
    // ATTRIBUTES
    // ----------------------------------
    private final Class<T> clazz;
    private final Response.Listener<T> listener;
    protected ObjectMapper mapper;

    // ----------------------------------
    // CONSTRUCTORS
    // ----------------------------------
    public BaseRequest(Class<T> clazz, int method, String methodUrl, Configuration configuration, Response.Listener<T> listener, Response.ErrorListener errorListener) {
        super(method, getBasetUrl(configuration) + methodUrl, errorListener);

        this.clazz = clazz;
        this.listener = listener;
        this.mapper = getAndInitializeMapper();
    }

    // ----------------------------------
    // PUBLIC METHODS
    // ----------------------------------
    public static ObjectMapper getAndInitializeMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, true);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        return mapper;
    }

    // ----------------------------------
    // OVERRIDEN METHODS
    // ----------------------------------
    @Override
    public String getBodyContentType() {
        return "application/json; charset=utf-8";
    }

    @Override
    protected void deliverResponse(T response) {
        listener.onResponse(response);
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            return Response.success(mapper.readValue(json, clazz), HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JsonMappingException e) {
            return Response.error(new ParseError(e));
        } catch (JsonParseException e) {
            return Response.error(new ParseError(e));
        } catch (IOException e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected VolleyError parseNetworkError(VolleyError volleyError) {
        try {
            if (volleyError.networkResponse != null) {
                String json = new String(volleyError.networkResponse.data, HttpHeaderParser.parseCharset(volleyError.networkResponse.headers));
                mapper.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, true);
                Error resultError = mapper.readValue(json, Error.class);

                return new HeiConnectError(resultError);
            }
            return volleyError;
        } catch (UnsupportedEncodingException e) {
            return volleyError;
        } catch (JsonMappingException e) {
            return volleyError;
        } catch (JsonParseException e) {
            return volleyError;
        } catch (IOException e) {
            return volleyError;
        }
    }

    // ----------------------------------
    // PRIVATE METHODS
    // ----------------------------------
    private static String getBasetUrl(Configuration configuration) {
        return configuration.getApiBaseUrl() + BuildConfig.API_URL_PATH;
    }

}
