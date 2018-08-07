package com.jumpitt.trust.ui.home_dynamic;

import android.animation.ValueAnimator;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.jumpitt.trust.R;
import com.jumpitt.trust.ui.home.HomeActivity;
import com.jumpitt.trust.ui.home.HomeContract;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.content.Context.CLIPBOARD_SERVICE;

/**
 * Created by felipe on 26-04-18.
 */

public class DynamicFragment extends Fragment {
    @BindView(R.id.label_code)
    TextView codeLabel;
    @BindView(R.id.progressbar)
    ProgressBar progressBar;
    @BindDrawable(R.drawable.progress_bar_normal)
    Drawable progressBarNormal;
    @BindDrawable(R.drawable.progress_no_time)
    Drawable progressBarRed;

    int max = 5000;

    HomeContract.View homeListener;
    private Timer timer;
    private TimerTask timerTask;
    private Handler handler = new Handler();
    private int mPage;

    public static DynamicFragment newInstance(int position) {

        Bundle args = new Bundle();
        args.putInt("POS", position);
        DynamicFragment fragment = new DynamicFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPage = getArguments().getInt("POS");
    }

    //Animar codigo al copiarlo
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragment = inflater.inflate(R.layout.fragment_home_dynamic, container, false);
        ButterKnife.bind(this, fragment);
        fragment.setTag(mPage);
        if (getActivity() instanceof HomeActivity) {
            homeListener = (HomeContract.View) getActivity();
        } else {
            Log.e(DynamicFragment.class.getSimpleName(), "Cannot instantiate listener");
        }
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressBar.setMax(max);
        startTimer();

    }

    @Override
    public void onPause() {
        super.onPause();
        stopTimer();
    }

    private void stopTimer() {
        if (timer != null) {
            timer.cancel();
            timer.purge();
        }
    }

    private void startTimer() {
        timer = new Timer();
        timerTask = new TimerTask() {
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        Random random = new Random();
                        String codeL = String.valueOf(random.nextInt(999));
                        for (int i = 0; i < 3 - codeL.length(); i++) {
                            codeL = "0" + codeL;
                        }
                        String codeR = String.valueOf(random.nextInt(999));
                        for (int i = 0; i < 3 - codeR.length(); i++) {
                            codeR = "0" + codeR;
                        }
                        codeLabel.setText(codeL + " " + codeR);
                        progressBar.setProgress(max);
                        startCountDown();
                    }
                });
            }
        };
        timer.schedule(timerTask, 0, max);
    }

    private void startCountDown() {
        ValueAnimator widthAnimator = ValueAnimator.ofInt(max, 0);
        widthAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int animatedValue = (int) animation.getAnimatedValue();
                if (animatedValue > max / 3) {
                    progressBar.setProgressDrawable(progressBarNormal);
                } else {
                    progressBar.setProgressDrawable(progressBarRed);
                }
                progressBar.setProgress(animatedValue);
            }
        });
        widthAnimator.setDuration(max);
        widthAnimator.start();
    }

    @OnClick(R.id.copy_button)
    void onCopyClicked() {
        if (getActivity() == null) return;

        ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("Code", codeLabel.getText());
        if (clipboard == null) return;
        clipboard.setPrimaryClip(clip);
        Toast.makeText(getActivity(), "Código Copiado", Toast.LENGTH_SHORT).show();
    }
}
