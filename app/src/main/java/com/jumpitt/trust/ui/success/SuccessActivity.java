package com.jumpitt.trust.ui.success;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.jumpitt.trust.R;
import com.jumpitt.trust.ui.home.HomeActivity;
import com.jumpitt.trust.ui.register.RegisterPersonalDataActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by felipe on 25-04-18.
 */

public class SuccessActivity extends AppCompatActivity {
    public static final String IMAGE_EXTRA = "image";
    public static final String TITLE_EXTRA = "title";
    public static final String DESCRIPTION_EXTRA = "description";
    public static final String TYPE_EXTRA = "type";
    public static final int TYPE_PHONE = 0;
    public static final int TYPE_REGISTER = 1;

    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.description)
    TextView description;
    @BindView(R.id.continue_button)
    Button continueButton;

    private int type = -1;

    @Override
    public void onBackPressed() {
        //Do nothing
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);
        ButterKnife.bind(this);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            image.setImageDrawable(ContextCompat.getDrawable(this, extras.getInt(IMAGE_EXTRA)));
            title.setText(extras.getString(TITLE_EXTRA));
            description.setText(extras.getString(DESCRIPTION_EXTRA));
            type = extras.getInt(TYPE_EXTRA);
            switch (type) {
                case TYPE_PHONE:
                    continueButton.setText("Crear Cuenta");
                    break;
                case TYPE_REGISTER:
                    continueButton.setText("Finalizar");
                    break;
            }
        }
    }

    @OnClick(R.id.continue_button)
    void onContinueButtonClicked() {
        switch (type) {
            case TYPE_PHONE:
                startActivity(new Intent(this, RegisterPersonalDataActivity.class));
                finish();
                break;
            case TYPE_REGISTER:
                startActivity(new Intent(this, HomeActivity.class));
                finish();
                break;
        }
    }
}
