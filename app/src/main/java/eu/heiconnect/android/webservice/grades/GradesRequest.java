package eu.heiconnect.android.webservice.grades;

import com.android.volley.Response;

import eu.heiconnect.android.activity.ConnectActivity;
import eu.heiconnect.android.webservice.LoggedInRequest;
import eu.heiconnect.android.webservice.schedule.ScheduleResult;

public class GradesRequest extends LoggedInRequest<GradesResult> {

    // ----------------------------------
    // CONSTANTS
    // ----------------------------------
    private static String METHOD_ROUTE = "grades.json";

    // ----------------------------------
    // CONSTRUCTORS
    // ----------------------------------
    public GradesRequest(ConnectActivity activity, String token, Response.Listener<GradesResult> listener, Response.ErrorListener errorListener) {
        super(GradesResult.class, Method.GET, METHOD_ROUTE, token, activity, listener, errorListener);
    }
}
