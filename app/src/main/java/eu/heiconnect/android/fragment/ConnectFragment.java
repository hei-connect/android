package eu.heiconnect.android.fragment;

import android.app.Fragment;

import com.android.volley.RequestQueue;

import eu.heiconnect.android.ConnectApplication;
import eu.heiconnect.android.utils.Configuration;

public class ConnectFragment extends Fragment {

    // ----------------------------------
    // LIFE CYCLE
    // ----------------------------------
    @Override
    public void onStop() {
        getRequestQueue().cancelAll(this);
        super.onStop();
    }

    // ----------------------------------
    // PROTECTED METHODS
    // ----------------------------------
    protected RequestQueue getRequestQueue() {
        ConnectApplication application = (ConnectApplication) getActivity().getApplication();
        return application.getRequestQueue();
    }

}
