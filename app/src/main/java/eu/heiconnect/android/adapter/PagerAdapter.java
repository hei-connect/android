package eu.heiconnect.android.adapter;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.support.v13.app.FragmentPagerAdapter;

import eu.heiconnect.android.R;
import eu.heiconnect.android.fragment.AbsencesFragment;
import eu.heiconnect.android.fragment.GradesFragment;
import eu.heiconnect.android.fragment.ScheduleFragment;

public class PagerAdapter extends FragmentPagerAdapter {

    private final Context context;

    // ----------------------------------
    // CONSTRUCTORS
    // ----------------------------------
    public PagerAdapter(Context context, FragmentManager fragmentManager) {
        super(fragmentManager);
        this.context = context;
    }

    // ----------------------------------
    // PUBLIC METHODS
    // ----------------------------------
    public Page getPageType(int position) {
        return Page.values()[position];
    }

    // ----------------------------------
    // OVERRIDEN METHODS
    // ----------------------------------
    @Override
    public Fragment getItem(int position) {
        Page page = getPageType(position);

        switch (page) {
            case TODAY:
                return ScheduleFragment.newInstance(ScheduleFragment.Day.TODAY);

            case TOMORROW:
                return ScheduleFragment.newInstance(ScheduleFragment.Day.TOMORROW);

            case GRADES:
                return new GradesFragment();

            case ABSENCES:
                return new AbsencesFragment();

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return Page.values().length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return context.getString(getPageType(position).getTitleId());
    }

    // ----------------------------------
    // INNER CLASSES
    // ----------------------------------
    public static enum Page {
        TODAY(R.string.main_tab_title_today),
        TOMORROW(R.string.main_tab_title_tomorrow),
        GRADES(R.string.main_tab_title_grades),
        ABSENCES(R.string.main_tab_title_absences);

        private int titleId;

        Page(int title) {
            this.titleId = title;
        }

        public int getTitleId() {
            return titleId;
        }
    }
}
