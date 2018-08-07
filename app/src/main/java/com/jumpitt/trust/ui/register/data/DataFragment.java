package com.jumpitt.trust.ui.register.data;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jumpitt.trust.R;
import com.jumpitt.trust.ui.register.RegisterPersonalDataActivity;
import com.jumpitt.trust.ui.register.RegisterPersonalDataContract;

import butterknife.ButterKnife;

/**
 * Created by felipe on 25-04-18.
 */

public class DataFragment extends Fragment {
    RegisterPersonalDataContract.View registerListener;

    public static DataFragment newInstance() {
        DataFragment fragment = new DataFragment();

        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragment = inflater.inflate(R.layout.fragment_register, container, false);
        ButterKnife.bind(this, fragment);
        if (getActivity() instanceof RegisterPersonalDataActivity) {
            registerListener = (RegisterPersonalDataContract.View) getActivity();
        } else {
            Log.e(DataFragment.class.getSimpleName(), "Listener cannot be referenced");
        }
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

}
