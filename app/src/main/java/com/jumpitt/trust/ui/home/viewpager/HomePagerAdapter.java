package com.jumpitt.trust.ui.home.viewpager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.jumpitt.trust.ui.home_dynamic.DynamicFragment;
import com.jumpitt.trust.ui.home_events.EventsFragment;
import com.jumpitt.trust.ui.home_history.HistoryFragment;
import com.jumpitt.trust.ui.home_profile.ProfileFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by felip on 02/03/2017.
 */

public class HomePagerAdapter extends FragmentPagerAdapter {
    public static final int FRAGMENT_EVENTS = 0;
    public static final int FRAGMENT_HISTORY = 1;
    public static final int FRAGMENT_DYNAMIC = 2;
    public static final int FRAGMENT_PROFILE = 3;

    private List<Fragment> fragments = new ArrayList<>();

    public HomePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case FRAGMENT_EVENTS:
                EventsFragment eventsFragment = EventsFragment.newInstance(FRAGMENT_EVENTS);
                fragments.add(eventsFragment);
                return eventsFragment;
            case FRAGMENT_HISTORY:
                HistoryFragment historyFragment = HistoryFragment.newInstance(FRAGMENT_HISTORY);
                fragments.add(historyFragment);
                return historyFragment;
            case FRAGMENT_DYNAMIC:
                DynamicFragment dynamicFragment = DynamicFragment.newInstance(FRAGMENT_DYNAMIC);
                fragments.add(dynamicFragment);
                return dynamicFragment;
            case FRAGMENT_PROFILE:
                ProfileFragment profileFragment = ProfileFragment.newInstance(FRAGMENT_PROFILE);
                fragments.add(profileFragment);
                return profileFragment;
        }
        return null;
    }


    public Fragment getFragment(int position) {
        return fragments.get(position);
    }

}
