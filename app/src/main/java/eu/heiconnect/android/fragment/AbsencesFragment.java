package eu.heiconnect.android.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.nhaarman.listviewanimations.swinginadapters.prepared.SwingBottomInAnimationAdapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import eu.heiconnect.android.R;
import eu.heiconnect.android.activity.ConnectActivity;
import eu.heiconnect.android.adapter.BaseListAdapter;
import eu.heiconnect.android.utils.DateUtils;
import eu.heiconnect.android.utils.PreferencesWrapper;
import eu.heiconnect.android.view.AbsenceCellView;
import eu.heiconnect.android.webservice.absences.Absence;
import eu.heiconnect.android.webservice.absences.AbsencesRequest;
import eu.heiconnect.android.webservice.absences.AbsencesResult;
import eu.heiconnect.android.webservice.grades.Grade;

public class AbsencesFragment extends ConnectFragment {

    // ----------------------------------
    // CONSTANTS
    // ----------------------------------
    private static final int MINUTES_FOR_AUTO_REFRESH = 4 * 60;
    private static final String BUNDLE_KEY_LAST_REQUEST_DATE = "BUNDLE_KEY_LAST_REQUEST_DATE";
    private static final String BUNDLE_KEY_ABSENCES = "BUNDLE_KEY_ABSENCES";

    // ----------------------------------
    // ATTRIBUTES
    // ----------------------------------
    private View emptyListView;
    private Date lastRequestDate;
    private List<Absence> absenceList;
    private BaseListAdapter<Absence> adapter;
    private SwipeRefreshLayout refreshLayout;
    private SwingBottomInAnimationAdapter animationAdapter;

    // ----------------------------------
    // LIFE CYCLE
    // ----------------------------------
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            absenceList = new ArrayList<Absence>();
        } else {
            lastRequestDate = (Date) savedInstanceState.getSerializable(BUNDLE_KEY_LAST_REQUEST_DATE);
            absenceList = (ArrayList<Absence>) savedInstanceState.getSerializable(BUNDLE_KEY_ABSENCES);
        }
        adapter = new BaseListAdapter<Absence>(getActivity(), AbsenceCellView.class, absenceList);
        animationAdapter = new SwingBottomInAnimationAdapter(adapter);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_absences, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        emptyListView = view.findViewById(R.id.viewgroup_absences_empty);

        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swiperefreshlayout_absences);
        refreshLayout.setOnRefreshListener(new RefreshListener());
        refreshLayout.setColorSchemeColors(getResources().getColor(R.color.success), getResources().getColor(R.color.warning),
                getResources().getColor(R.color.primary), getResources().getColor(R.color.danger));

        if (lastRequestDate == null || DateUtils.getDifferenceInMinutes(lastRequestDate, new Date()) >= MINUTES_FOR_AUTO_REFRESH) {
            performAbsencesRequest();
        }

        if (absenceList.size() > 0) {
            emptyListView.setVisibility(View.GONE);
        }

        ListView listView = (ListView) view.findViewById(R.id.listview_absences);
        animationAdapter.setAbsListView(listView);
        listView.setAdapter(animationAdapter);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(BUNDLE_KEY_ABSENCES, new ArrayList<Absence>(absenceList));
        outState.putSerializable(BUNDLE_KEY_LAST_REQUEST_DATE, lastRequestDate);
        super.onSaveInstanceState(outState);
    }

    // ----------------------------------
    // PRIVATE METHODS
    // ----------------------------------
    private void performAbsencesRequest() {
        refreshLayout.setRefreshing(true);

        PreferencesWrapper preferences = new PreferencesWrapper(getActivity());
        AbsencesRequest request = new AbsencesRequest((ConnectActivity) getActivity(), preferences.getUserToken(),
                new AbsencesResultListener(), new ErrorListener());
        request.setTag(this);
        getRequestQueue().add(request);
    }

    // ----------------------------------
    // INNER CLASSES
    // ----------------------------------
    private class RefreshListener implements SwipeRefreshLayout.OnRefreshListener {
        @Override
        public void onRefresh() {
            performAbsencesRequest();
        }
    }


    private class AbsencesResultListener implements Response.Listener<AbsencesResult> {
        @Override
        public void onResponse(AbsencesResult absencesResult) {
            absenceList = absencesResult.getAbsences();
            lastRequestDate = new Date();
            adapter.refill(absenceList);
            refreshLayout.setRefreshing(false);

            if (emptyListView.getVisibility() == View.VISIBLE && absenceList.size() > 0) {
                emptyListView.animate().alpha(0).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        emptyListView.setVisibility(View.GONE);
                        super.onAnimationEnd(animation);
                    }
                });
            }
        }
    }

    private class ErrorListener implements Response.ErrorListener {
        @Override
        public void onErrorResponse(VolleyError volleyError) {
            // TODO Handle errors gracefully

            refreshLayout.setRefreshing(false);
        }
    }
}
