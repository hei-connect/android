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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import eu.heiconnect.android.R;
import eu.heiconnect.android.activity.ConnectActivity;
import eu.heiconnect.android.adapter.BaseListAdapter;
import eu.heiconnect.android.utils.DateUtils;
import eu.heiconnect.android.utils.PreferencesWrapper;
import eu.heiconnect.android.view.CourseCellView;
import eu.heiconnect.android.view.UpdateHeaderView;
import eu.heiconnect.android.webservice.LoggedInRequest;
import eu.heiconnect.android.webservice.schedule.Course;
import eu.heiconnect.android.webservice.schedule.ScheduleResult;
import eu.heiconnect.android.webservice.schedule.TodayRequest;
import eu.heiconnect.android.webservice.schedule.TomorrowRequest;
import eu.heiconnect.android.webservice.schedule.Update;

public class ScheduleFragment extends ConnectFragment {

    // ----------------------------------
    // CONSTANTS
    // ----------------------------------
    private static final int MINUTES_FOR_AUTO_REFRESH = 1;
    private static final String ARG_DAY = "ARG_DAY";
    private static final String BUNDLE_KEY_COURSES = "BUNDLE_KEY_COURSES";
    private static final String BUNDLE_KEY_UPDATE = "BUNDLE_KEY_UPDATE";
    private static final String BUNDLE_KEY_LAST_REQUEST_DATE = "BUNDLE_KEY_LAST_REQUEST_DATE";

    // ----------------------------------
    // ATTRIBUTES
    // ----------------------------------
    private Day day;
    private Update update;
    private Date lastRequestDate;
    private List<Course> courseList;
    private BaseListAdapter<Course> adapter;
    private SwipeRefreshLayout refreshLayout;
    private UpdateHeaderView updateHeaderView;

    // ----------------------------------
    // CONSTRUCTORS
    // ----------------------------------
    public static ScheduleFragment newInstance(Day day) {
        ScheduleFragment fragment = new ScheduleFragment();

        Bundle arguments = new Bundle();
        arguments.putSerializable(ARG_DAY, day);
        fragment.setArguments(arguments);

        return fragment;
    }

    // ----------------------------------
    // LIFE CYCLE
    // ----------------------------------
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        day = (Day) getArguments().get(ARG_DAY);

        if (savedInstanceState == null) {
            courseList = new ArrayList<Course>();
        } else {
            lastRequestDate = (Date) savedInstanceState.getSerializable(BUNDLE_KEY_LAST_REQUEST_DATE);
            courseList = (ArrayList<Course>) savedInstanceState.getSerializable(BUNDLE_KEY_COURSES);
            update = (Update) savedInstanceState.getSerializable(BUNDLE_KEY_UPDATE);
        }
        adapter = new BaseListAdapter<Course>(getActivity(), CourseCellView.class, courseList);

        if (Day.TODAY.equals(day)) {
            updateHeaderView = new UpdateHeaderView(getActivity());
            if (update != null) {
                updateHeaderView.bindData(update.getUpdatedAt());
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_schedule, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swiperefreshlayout_schedule);
        refreshLayout.setOnRefreshListener(new RefreshListener());
        refreshLayout.setColorSchemeColors(getResources().getColor(R.color.success), getResources().getColor(R.color.warning),
                getResources().getColor(R.color.primary), getResources().getColor(R.color.danger));

        if (lastRequestDate == null || DateUtils.getDifferenceInMinutes(lastRequestDate, new Date()) >= MINUTES_FOR_AUTO_REFRESH) {
            performScheduleRequest();
        }

        ListView listView = (ListView) view.findViewById(R.id.listview_schedule);
        listView.setAdapter(adapter);
        if (updateHeaderView != null) {
            listView.addHeaderView(updateHeaderView);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(BUNDLE_KEY_COURSES, new ArrayList<Course>(courseList));
        outState.putSerializable(BUNDLE_KEY_UPDATE, update);
        outState.putSerializable(BUNDLE_KEY_LAST_REQUEST_DATE, lastRequestDate);
        super.onSaveInstanceState(outState);
    }

    // ----------------------------------
    // PRIVATE METHODS
    // ----------------------------------
    private void performScheduleRequest() {
        refreshLayout.setRefreshing(true);

        PreferencesWrapper preferences = new PreferencesWrapper(getActivity());
        LoggedInRequest<ScheduleResult> request;
        if (Day.TODAY.equals(day)) {
            request = new TodayRequest((ConnectActivity) getActivity(), preferences.getUserToken(),
                    new ScheduleResultListener(), new ErrorListener());
        } else {
            request = new TomorrowRequest((ConnectActivity) getActivity(), preferences.getUserToken(),
                    new ScheduleResultListener(), new ErrorListener());
        }
        request.setTag(this);
        getRequestQueue().add(request);
    }

    // ----------------------------------
    // INNER CLASSES
    // ----------------------------------
    private class RefreshListener implements SwipeRefreshLayout.OnRefreshListener {
        @Override
        public void onRefresh() {
            performScheduleRequest();
        }
    }


    private class ScheduleResultListener implements Response.Listener<ScheduleResult> {
        @Override
        public void onResponse(ScheduleResult scheduleResult) {
            courseList = scheduleResult.getCourses();
            update = scheduleResult.getLastUpdate();
            lastRequestDate = new Date();
            adapter.refill(courseList);
            if (updateHeaderView != null) {
                updateHeaderView.bindData(update.getUpdatedAt());
            }

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

    public enum Day {
        TODAY, TOMORROW
    }
}
