package com.jumpitt.trust.ui.register.code;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.jumpitt.trust.R;
import com.jumpitt.trust.ui.register.RegisterNumberActivity;
import com.jumpitt.trust.ui.register.RegisterNumberContract;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import butterknife.OnTextChanged;

/**
 * Created by felipe on 25-04-18.
 */

public class CodeFragment extends Fragment {
    RegisterNumberContract.View registerListener;
    @BindView(R.id.card_one)
    CardView cardOne;
    @BindView(R.id.card_two)
    CardView cardTwo;
    @BindView(R.id.card_three)
    CardView cardThree;
    @BindView(R.id.card_four)
    CardView cardFour;
    @BindView(R.id.code_one)
    EditText codeOne;
    @BindView(R.id.code_two)
    EditText codeTwo;
    @BindView(R.id.code_three)
    EditText codeThree;
    @BindView(R.id.code_four)
    EditText codeFour;

    public static CodeFragment newInstance() {
        Bundle args = new Bundle();

        CodeFragment fragment = new CodeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragment = inflater.inflate(R.layout.fragment_send_code, container, false);
        ButterKnife.bind(this, fragment);
        if (getActivity() instanceof RegisterNumberActivity) {
            registerListener = (RegisterNumberContract.View) getActivity();
        } else {
            Log.e(CodeFragment.class.getSimpleName(), "Listener cannot be referenced");
        }
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @OnFocusChange(R.id.code_one)
    void onCodeOneFocusChange(boolean focused) {
        cardOne.setCardElevation((focused || !codeOne.getText().toString().isEmpty()) ? 16 : 2);
    }

    @OnFocusChange(R.id.code_two)
    void onCodeTwoFocusChange(boolean focused) {
        cardTwo.setCardElevation((focused || !codeTwo.getText().toString().isEmpty()) ? 16 : 2);
    }

    @OnFocusChange(R.id.code_three)
    void onCodeThreeFocusChange(boolean focused) {
        cardThree.setCardElevation((focused || !codeThree.getText().toString().isEmpty()) ? 16 : 2);
    }

    @OnFocusChange(R.id.code_four)
    void onCodeFourFocusChange(boolean focused) {
        cardFour.setCardElevation((focused || !codeFour.getText().toString().isEmpty()) ? 16 : 2);
    }

    @OnTextChanged(R.id.code_one)
    void onCodeOneTextChanged() {
        if (!codeOne.getText().toString().isEmpty()) {
            codeTwo.requestFocus();
        }
        sendCode();
    }

    @OnTextChanged(R.id.code_two)
    void onCodeTwoTextChanged() {
        if (!codeTwo.getText().toString().isEmpty()) {
            codeThree.requestFocus();
        }
        sendCode();
    }

    @OnTextChanged(R.id.code_three)
    void onCodeThreeTextChanged() {
        if (!codeThree.getText().toString().isEmpty()) {
            codeFour.requestFocus();
        }
        sendCode();
    }

    @OnTextChanged(R.id.code_four)
    void onCodeFourTextChanged() {
        sendCode();
    }

    private void sendCode() {
        String code = codeOne.getText().toString() +
                codeTwo.getText().toString() +
                codeThree.getText().toString() +
                codeFour.getText().toString();
        registerListener.sendCode(code);
    }
}
