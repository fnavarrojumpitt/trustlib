package com.jumpitt.trust.ui.login;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jumpitt.trust.R;
import com.jumpitt.trust.ui.welcome.WelcomeActivity;
import com.jumpitt.trust.ui.welcome.WelcomeContract;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by felipe on 23-04-18.
 */

public class LoggedPromptFragment extends Fragment {
    @BindView(R.id.input_edittext_password)
    TextInputEditText passwordInput;

    WelcomeContract.View welcomeListener;

    public static LoggedPromptFragment newInstance() {

        Bundle args = new Bundle();

        LoggedPromptFragment fragment = new LoggedPromptFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public LoggedPromptFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragment = inflater.inflate(R.layout.login_prompt_logged, container, false);
        ButterKnife.bind(this, fragment);

        passwordInput.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        if (getActivity() instanceof WelcomeActivity) {
            welcomeListener = (WelcomeContract.View) getActivity();
        } else {
            Log.e(LoginPromptFragment.class.getSimpleName(), "Listener cannot be referenced");
        }
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
