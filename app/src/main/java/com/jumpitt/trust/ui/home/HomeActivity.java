package com.jumpitt.trust.ui.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.jumpitt.trust.R;
import com.jumpitt.trust.ui.home.viewpager.HomePagerAdapter;
import com.jumpitt.trust.utils.HomePagerTransformer;
import com.jumpitt.trustlib.TrustHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by felipe on 26-04-18.
 */

public class HomeActivity extends TrustHelper implements HomeContract.View {

    @BindView(R.id.bottom_navigation)
    BottomNavigationViewEx navigationViewEx;
    @BindView(R.id.home_pager)
    ViewPager homePager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        homePager.setOffscreenPageLimit(3);
        HomePagerAdapter adapter = new HomePagerAdapter(getSupportFragmentManager());
        homePager.setAdapter(adapter);
        homePager.setPageTransformer(false, new HomePagerTransformer(getActionBarDefaultSize()));

        navigationViewEx.enableAnimation(true);
        navigationViewEx.enableShiftingMode(false);
        navigationViewEx.enableItemShiftingMode(false);
        navigationViewEx.setIconSize(48, 28);
        navigationViewEx.setTextSize(10);
        navigationViewEx.setTypeface(ResourcesCompat.getFont(this, R.font.fira_sans_extra_condensed));
        navigationViewEx.setupWithViewPager(homePager);
    }

    @OnClick(R.id.trustlib)
    void trust() {
        getTrifles();
    }

    public int getActionBarDefaultSize() {
        int actionBarHeight = -1;
        TypedValue tv = new TypedValue();
        if (getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, getResources().getDisplayMetrics());
        }
        return actionBarHeight;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
