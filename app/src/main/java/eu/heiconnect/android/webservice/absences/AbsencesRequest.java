package eu.heiconnect.android.webservice.absences;

import com.android.volley.Response;

import eu.heiconnect.android.activity.ConnectActivity;
import eu.heiconnect.android.webservice.LoggedInRequest;
import eu.heiconnect.android.webservice.grades.GradesResult;

public class AbsencesRequest extends LoggedInRequest<AbsencesResult> {

    // ----------------------------------
    // CONSTANTS
    // ----------------------------------
    private static String METHOD_ROUTE = "absences.json";

    // ----------------------------------
    // CONSTRUCTORS
    // ----------------------------------
    public AbsencesRequest(ConnectActivity activity, String token, Response.Listener<AbsencesResult> listener, Response.ErrorListener errorListener) {
        super(AbsencesResult.class, Method.GET, METHOD_ROUTE, token, activity, listener, errorListener);
    }
}
