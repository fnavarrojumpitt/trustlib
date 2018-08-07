package com.jumpitt.trust.ui.welcome;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v4.app.FragmentManager;
import android.text.Html;
import android.transition.TransitionManager;
import android.widget.Button;
import android.widget.TextView;

import com.jumpitt.trust.R;
import com.jumpitt.trust.ui.BaseActivity;
import com.jumpitt.trust.ui.home.HomeActivity;
import com.jumpitt.trust.ui.login.LoggedPromptFragment;
import com.jumpitt.trust.ui.login.LoginPromptFragment;
import com.jumpitt.trust.ui.register.RegisterNumberActivity;

import java.util.Locale;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;

public class WelcomeActivity extends BaseActivity implements WelcomeContract.View {
    private static final int WELCOME_STATUS = 0;
    private static final int LOGIN_STATUS = 1;
    private static final int LOGGED_STATUS = 2;

    @BindView(R.id.register_button)
    Button registerButton;
    @BindView(R.id.welcome_constraint)
    ConstraintLayout welcomeConstraint;

    @BindString(R.string.login_register_button)
    String registerButtonNormalString;
    @BindString(R.string.login_change_account)
    String registerButtonChangeAccountString;

    private ConstraintSet defaultConstraintSet;
    private ConstraintSet promptConstraintSet;
    private int actualStatus = LOGGED_STATUS;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);

        defaultConstraintSet = new ConstraintSet();
        promptConstraintSet = new ConstraintSet();
        defaultConstraintSet.clone(this, R.layout.activity_welcome);
        promptConstraintSet.clone(this, R.layout.activity_welcome_prompt);
        fragmentManager = getSupportFragmentManager();

        //TODO: Check status
        changeStatus(actualStatus);
    }

    @OnTouch(R.id.welcome_constraint)
    boolean onTouch() {
        hideSoftKeyboard(WelcomeActivity.this);
        return false;
    }

    @OnClick(R.id.login_button)
    void onLoginButtonClicked() {
        if (actualStatus == WELCOME_STATUS) {
            changeStatus(LOGIN_STATUS);
            //TODO: show login prompt
        } else {
            startActivity(new Intent(this, HomeActivity.class));
            finish();
        }
    }

    @OnClick(R.id.register_button)
    void onRegisterButtonClicked() {
        if (actualStatus != LOGGED_STATUS) {
            startActivity(new Intent(this, RegisterNumberActivity.class));
        } else {
            removeFragment(fragmentManager, fragmentManager.getFragments().get(0), true);
            changeStatus(LOGIN_STATUS);
        }
    }


    private void changeStatus(int status) {
        TransitionManager.beginDelayedTransition(welcomeConstraint);
        switch (status) {
            case WELCOME_STATUS:
                defaultConstraintSet.applyTo(welcomeConstraint);
                registerButton.setText(Html.fromHtml(registerButtonNormalString), TextView.BufferType.SPANNABLE);
                break;
            case LOGIN_STATUS:
                promptConstraintSet.applyTo(welcomeConstraint);
                registerButton.setText(Html.fromHtml(registerButtonNormalString), TextView.BufferType.SPANNABLE);
                replaceFragment(fragmentManager, R.id.login_input_container, LoginPromptFragment.newInstance(), "Prompt", true, false);
                break;
            case LOGGED_STATUS:
                promptConstraintSet.applyTo(welcomeConstraint);
                registerButton.setText(Html.fromHtml(String.format(Locale.getDefault(), registerButtonChangeAccountString, "USER")), TextView.BufferType.SPANNABLE);
                replaceFragment(fragmentManager, R.id.login_input_container, LoggedPromptFragment.newInstance(), "Prompt", true, false);
                break;
        }
        actualStatus = status;
    }


}
