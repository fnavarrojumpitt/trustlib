package com.jumpitt.trust.ui.register.phone;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jumpitt.trust.R;
import com.jumpitt.trust.ui.register.RegisterNumberActivity;
import com.jumpitt.trust.ui.register.RegisterNumberContract;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTextChanged;

/**
 * Created by felipe on 25-04-18.
 */

public class PhoneFragment extends Fragment {
    private static final String PHONE_KEY = "phone";
    @BindView(R.id.input_edittext_phone)
    TextInputEditText phoneInput;
    RegisterNumberContract.View registerListener;

    public static PhoneFragment newInstance(String phone) {
        PhoneFragment fragment = new PhoneFragment();
        if (phone != null) {
            Bundle args = new Bundle();
            args.putString(PHONE_KEY, phone);
            fragment.setArguments(args);
        }
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragment = inflater.inflate(R.layout.fragment_send_phone, container, false);
        ButterKnife.bind(this, fragment);
        if (getActivity() instanceof RegisterNumberActivity) {
            registerListener = (RegisterNumberContract.View) getActivity();
        } else {
            Log.e(PhoneFragment.class.getSimpleName(), "Listener cannot be referenced");
        }
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            if (args.containsKey(PHONE_KEY)) {
                phoneInput.setText(args.getString(PHONE_KEY));
            }
        }
    }

    @OnTextChanged(R.id.input_edittext_phone)
    void onPhoneButton() {
        String phone = phoneInput.getText().toString();
        registerListener.onPhoneTextChange((phone.length() == 8) ? phone : null);
    }

}
