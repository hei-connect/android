package eu.heiconnect.android.webservice.schedule;

import com.android.volley.Response;

import eu.heiconnect.android.activity.ConnectActivity;
import eu.heiconnect.android.webservice.LoggedInRequest;

public class TomorrowRequest extends LoggedInRequest<ScheduleResult> {

    // ----------------------------------
    // CONSTANTS
    // ----------------------------------
    private static String METHOD_ROUTE = "schedule/tomorrow.json";

    // ----------------------------------
    // CONSTRUCTORS
    // ----------------------------------
    public TomorrowRequest(ConnectActivity activity, String token, Response.Listener<ScheduleResult> listener, Response.ErrorListener errorListener) {
        super(ScheduleResult.class, Method.GET, METHOD_ROUTE, token, activity, listener, errorListener);
    }
}
