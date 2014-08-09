package eu.heiconnect.android.fragment;

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
import eu.heiconnect.android.view.GradeCellView;
import eu.heiconnect.android.webservice.grades.Grade;
import eu.heiconnect.android.webservice.grades.GradesRequest;
import eu.heiconnect.android.webservice.grades.GradesResult;
import eu.heiconnect.android.webservice.schedule.Update;

public class GradesFragment extends ConnectFragment {

    // ----------------------------------
    // CONSTANTS
    // ----------------------------------
    private static final int MINUTES_FOR_AUTO_REFRESH = 4 * 60;
    private static final String BUNDLE_KEY_LAST_REQUEST_DATE = "BUNDLE_KEY_LAST_REQUEST_DATE";
    private static final String BUNDLE_KEY_GRADES = "BUNDLE_KEY_GRADES";
    private static final String BUNDLE_KEY_UPDATE = "BUNDLE_KEY_UPDATE";

    // ----------------------------------
    // ATTRIBUTES
    // ----------------------------------
    private Update update;
    private Date lastRequestDate;
    private List<Grade> gradeList;
    private BaseListAdapter<Grade> adapter;
    private SwipeRefreshLayout refreshLayout;
    private SwingBottomInAnimationAdapter animationAdapter;

    // ----------------------------------
    // LIFE CYCLE
    // ----------------------------------
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            gradeList = new ArrayList<Grade>();
        } else {
            lastRequestDate = (Date) savedInstanceState.getSerializable(BUNDLE_KEY_LAST_REQUEST_DATE);
            gradeList = (ArrayList<Grade>) savedInstanceState.getSerializable(BUNDLE_KEY_GRADES);
            update = (Update) savedInstanceState.getSerializable(BUNDLE_KEY_UPDATE);
        }
        adapter = new BaseListAdapter<Grade>(getActivity(), GradeCellView.class, gradeList);
        animationAdapter = new SwingBottomInAnimationAdapter(adapter);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_grades, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swiperefreshlayout_grades);
        refreshLayout.setOnRefreshListener(new RefreshListener());
        refreshLayout.setColorSchemeColors(getResources().getColor(R.color.success), getResources().getColor(R.color.warning),
                getResources().getColor(R.color.primary), getResources().getColor(R.color.danger));

        if (lastRequestDate == null || DateUtils.getDifferenceInMinutes(lastRequestDate, new Date()) >= MINUTES_FOR_AUTO_REFRESH) {
            performGradesRequest();
        }

        ListView listView = (ListView) view.findViewById(R.id.listview_grades);
        animationAdapter.setAbsListView(listView);
        listView.setAdapter(animationAdapter);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(BUNDLE_KEY_GRADES, new ArrayList<Grade>(gradeList));
        outState.putSerializable(BUNDLE_KEY_UPDATE, update);
        outState.putSerializable(BUNDLE_KEY_LAST_REQUEST_DATE, lastRequestDate);
        super.onSaveInstanceState(outState);
    }

    // ----------------------------------
    // PRIVATE METHODS
    // ----------------------------------
    private void performGradesRequest() {
        refreshLayout.setRefreshing(true);

        PreferencesWrapper preferences = new PreferencesWrapper(getActivity());
        GradesRequest request = new GradesRequest((ConnectActivity) getActivity(), preferences.getUserToken(),
                new GradesResultListener(), new ErrorListener());
        request.setTag(this);
        getRequestQueue().add(request);
    }

    // ----------------------------------
    // INNER CLASSES
    // ----------------------------------
    private class RefreshListener implements SwipeRefreshLayout.OnRefreshListener {
        @Override
        public void onRefresh() {
            performGradesRequest();
        }
    }


    private class GradesResultListener implements Response.Listener<GradesResult> {
        @Override
        public void onResponse(GradesResult gradesResult) {
            gradeList = gradesResult.getGrades();
            update = gradesResult.getLastUpdate();
            lastRequestDate = new Date();
            adapter.refill(gradeList);
            refreshLayout.setRefreshing(false);
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
