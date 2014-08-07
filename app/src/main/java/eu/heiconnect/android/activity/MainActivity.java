package eu.heiconnect.android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.astuetz.PagerSlidingTabStrip;

import eu.heiconnect.android.R;
import eu.heiconnect.android.adapter.PagerAdapter;
import eu.heiconnect.android.utils.PreferencesWrapper;

public class MainActivity extends ConnectActivity {

    // ----------------------------------
    // LIFE CYCLE
    // ----------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PagerAdapter pagerAdapter = new PagerAdapter(this, getFragmentManager());
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager_main);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setPageMargin(getResources().getDimensionPixelSize(R.dimen.medium));

        PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) findViewById(R.id.pagerslidingtabstrip_main);
        tabs.setViewPager(viewPager);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Check that the user is well logged in
        PreferencesWrapper preferences = new PreferencesWrapper(this);
        if (preferences.getUserName() == null || preferences.getUserToken() == null) {
            preferences.putUserName(null);
            preferences.putUserToken(null);
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }
}


