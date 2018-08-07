package com.jumpitt.trust.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;

import com.jumpitt.trust.R;
import com.jumpitt.trustlib.TrustHelper;

/**
 * Created by felipe on 24-04-18.
 */

@SuppressLint("Registered")
public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null && activity.getCurrentFocus() != null) {
            inputMethodManager.hideSoftInputFromWindow(
                    activity.getCurrentFocus().getWindowToken(), 0);
        }
    }

    public static void replaceFragment(FragmentManager fragmentManager, int containerId, Fragment fragment, String tag, boolean animated, boolean addToBackStack) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (animated) {
            transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
        }
        if (addToBackStack) {
            transaction.addToBackStack(null);
        }
        transaction.replace(containerId, fragment, tag)
                .commitAllowingStateLoss();
    }

    public static void removeFragment(FragmentManager fragmentManager, Fragment fragment, boolean animated) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (animated) {
            transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
        }
        transaction.remove(fragment)
                .commitAllowingStateLoss();
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
