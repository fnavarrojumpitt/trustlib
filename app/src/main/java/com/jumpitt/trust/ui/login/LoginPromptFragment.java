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

public class LoginPromptFragment extends Fragment {
    @BindView(R.id.input_edittext_password)
    TextInputEditText passwordInput;

    WelcomeContract.View welcomeListener;

    public static LoginPromptFragment newInstance() {

        Bundle args = new Bundle();

        LoginPromptFragment fragment = new LoginPromptFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public LoginPromptFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmet = inflater.inflate(R.layout.login_prompt_normal, container, false);
        ButterKnife.bind(this, fragmet);

        passwordInput.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        if (getActivity() instanceof WelcomeActivity) {
            welcomeListener = (WelcomeContract.View) getActivity();
        } else {
            Log.e(LoginPromptFragment.class.getSimpleName(), "Listener cannot be referenced");
        }
        return fragmet;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
