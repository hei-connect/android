package eu.heiconnect.android.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import eu.heiconnect.android.R;

public class ScheduleFragment extends Fragment {

    // ----------------------------------
    // CONSTANTS
    // ----------------------------------
    private static final String ARG_DAY = "ARG_DAY";
    private Day day;

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
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_schedule, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SwipeRefreshLayout refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swiperefreshlayout_schedule);
        refreshLayout.setOnRefreshListener(new RefreshListener());
        refreshLayout.setColorSchemeColors(getResources().getColor(R.color.success), getResources().getColor(R.color.warning),
                getResources().getColor(R.color.primary), getResources().getColor(R.color.danger));
        ListView listView = (ListView) view.findViewById(R.id.listview_schedule);
    }

    // ----------------------------------
    // INNER CLASSES
    // ----------------------------------
    private class RefreshListener implements SwipeRefreshLayout.OnRefreshListener {
        @Override
        public void onRefresh() {

        }
    }

    public enum Day {
        TODAY, TOMORROW
    }
}
