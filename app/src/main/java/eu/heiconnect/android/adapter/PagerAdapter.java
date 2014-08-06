package eu.heiconnect.android.adapter;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;

public class PagerAdapter extends FragmentPagerAdapter {

    // ----------------------------------
    // CONSTRUCTORS
    // ----------------------------------
    public PagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    // ----------------------------------
    // OVERRIDEN METHODS
    // ----------------------------------
    @Override
    public Fragment getItem(int position) {
        Page page = Page.values()[position];

        switch (page) {
            case TODAY:
                return new Fragment();

            case TOMORROW:
                return new Fragment();

            case GRADES:
                return new Fragment();

            case ABSENCES:
                return new Fragment();

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
        return Page.values()[position].toString();
    }

    // ----------------------------------
    // INNER CLASSES
    // ----------------------------------
    public static enum Page {
        TODAY,
        TOMORROW,
        GRADES,
        ABSENCES
    }
}
