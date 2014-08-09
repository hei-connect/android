package eu.heiconnect.android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import com.astuetz.PagerSlidingTabStrip;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import eu.heiconnect.android.R;
import eu.heiconnect.android.adapter.PagerAdapter;
import eu.heiconnect.android.utils.PreferencesWrapper;

public class MainActivity extends ConnectActivity {

    // ----------------------------------
    // CONSTANTS
    // ----------------------------------
    private static final String MAIN_SCREEN_NAME = "Main";
    private static final String TODAY_SCREEN_NAME = "Today";
    private static final String TOMORROW_PAGE_NAME = "Tomorrow";
    private static final String GRADES_PAGE_NAME = "Grades";
    private static final String ABSENCES_PAGE_NAME = "Absences";
    private static final String LOGOUT_EVENT_ACTION = "Logout";

    // ----------------------------------
    // ATTRIBUTES
    // ----------------------------------
    private PagerAdapter pagerAdapter;

    // ----------------------------------
    // LIFE CYCLE
    // ----------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Tracker tracker = getTracker();
        tracker.setScreenName(MAIN_SCREEN_NAME);
        tracker.send(new HitBuilders.AppViewBuilder().build());
        tracker.setScreenName(TODAY_SCREEN_NAME);
        tracker.send(new HitBuilders.AppViewBuilder().build());

        PreferencesWrapper preferences = new PreferencesWrapper(this);
        setTitle(preferences.getUserName());

        pagerAdapter = new PagerAdapter(this, getFragmentManager());
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager_main);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setPageMargin(getResources().getDimensionPixelSize(R.dimen.medium));

        PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) findViewById(R.id.pagerslidingtabstrip_main);
        tabs.setViewPager(viewPager);
        tabs.setOnPageChangeListener(new OnPageChangeListener());
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Check that the user is well logged in
        PreferencesWrapper preferences = new PreferencesWrapper(this);
        if (preferences.getUserName() == null || preferences.getUserToken() == null) {
            logoutUser();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_logout) {
            logoutUser();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    // ----------------------------------
    // PRIVATE METHODS
    // ----------------------------------
    private void logoutUser() {
        PreferencesWrapper preferences = new PreferencesWrapper(this);

        getTracker().send(new HitBuilders.EventBuilder()
                .setCategory(LoginActivity.SIGN_IN_EVENT_CATEGORY)
                .setAction(LOGOUT_EVENT_ACTION)
                .setLabel(preferences.getUserName())
                .build());

        preferences.putUserName(null);
        preferences.putUserToken(null);
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    // ----------------------------------
    // INNER CLASSES
    // ----------------------------------
    private class OnPageChangeListener extends ViewPager.SimpleOnPageChangeListener {
        @Override
        public void onPageSelected(int position) {
            super.onPageSelected(position);

            Tracker tracker = getTracker();

            if (tracker != null) {
                String path = "Unknown";
                switch (pagerAdapter.getPageType(position)) {
                    case TODAY:
                        path = TODAY_SCREEN_NAME;
                        break;
                    case TOMORROW:
                        path = TOMORROW_PAGE_NAME;
                        break;
                    case GRADES:
                        path = GRADES_PAGE_NAME;
                        break;
                    case ABSENCES:
                        path = ABSENCES_PAGE_NAME;
                        break;
                }

                tracker.setScreenName(path);
                tracker.send(new HitBuilders.AppViewBuilder().build());
            }
        }
    }
}


