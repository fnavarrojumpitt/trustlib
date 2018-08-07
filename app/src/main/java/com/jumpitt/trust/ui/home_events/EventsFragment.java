package com.jumpitt.trust.ui.home_events;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jumpitt.trust.R;
import com.jumpitt.trust.ui.home.HomeActivity;
import com.jumpitt.trust.ui.home.HomeContract;

import butterknife.ButterKnife;

/**
 * Created by felipe on 26-04-18.
 */

public class EventsFragment extends Fragment {
    HomeContract.View homeListener;
    private int mPage;

    public static EventsFragment newInstance(int position) {

        Bundle args = new Bundle();
        args.putInt("POS", position);
        EventsFragment fragment = new EventsFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPage = getArguments().getInt("POS");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragment = inflater.inflate(R.layout.fragment_home_events, container, false);
        ButterKnife.bind(this, fragment);
        fragment.setTag(mPage);
        if (getActivity() instanceof HomeActivity) {
            homeListener = (HomeContract.View) getActivity();
        } else {
            Log.e(EventsFragment.class.getSimpleName(), "Cannot instantiate listener");
        }
        return fragment;
    }
}
