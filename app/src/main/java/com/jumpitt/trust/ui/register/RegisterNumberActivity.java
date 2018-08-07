package com.jumpitt.trust.ui.register;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.jumpitt.trust.R;
import com.jumpitt.trust.ui.BaseActivity;
import com.jumpitt.trust.ui.register.code.CodeFragment;
import com.jumpitt.trust.ui.register.phone.PhoneFragment;
import com.jumpitt.trust.ui.success.SuccessActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTouch;

import static com.jumpitt.trust.ui.success.SuccessActivity.DESCRIPTION_EXTRA;
import static com.jumpitt.trust.ui.success.SuccessActivity.IMAGE_EXTRA;
import static com.jumpitt.trust.ui.success.SuccessActivity.TITLE_EXTRA;
import static com.jumpitt.trust.ui.success.SuccessActivity.TYPE_EXTRA;
import static com.jumpitt.trust.ui.success.SuccessActivity.TYPE_PHONE;

/**
 * Created by felipe on 25-04-18.
 */

public class RegisterNumberActivity extends BaseActivity implements RegisterNumberContract.View {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    MenuItem continueButton;

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_container);
        ButterKnife.bind(this);

        toolbar.setTitle("Verifica tu número");
        setSupportActionBar(toolbar);

        if (getActionBar() != null) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }

        fragmentManager = getSupportFragmentManager();

        replaceFragment(fragmentManager, R.id.register_container, PhoneFragment.newInstance(null), "Phone", false, false);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.continue_menu, menu);
        continueButton = menu.findItem(R.id.nav_continue);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_continue:
                continueButton.setEnabled(false);
                if (fragmentManager.getBackStackEntryCount() == 0) {
                    replaceFragment(fragmentManager, R.id.register_container,
                            CodeFragment.newInstance(), "Phone", true, true);
                } else {
                    showSuccess();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showSuccess() {
        Intent intent = new Intent(this, SuccessActivity.class);
        intent.putExtra(IMAGE_EXTRA, R.drawable.ic_phone_success);
        intent.putExtra(TITLE_EXTRA, "Verificación de número se realizó de manera correcta");
        intent.putExtra(DESCRIPTION_EXTRA, "El código ingresado es el correcto por lo cual tu número de teléfono quedó habilitado para ser usado en esta cuenta...");
        intent.putExtra(TYPE_EXTRA, TYPE_PHONE);
        startActivity(intent);
    }

    @OnTouch
    boolean onTouch() {
        hideSoftKeyboard(RegisterNumberActivity.this);
        return false;
    }

    @Override
    public void onPhoneTextChange(String phone) {
        continueButton.setEnabled(phone != null);
        if (phone != null) {
            hideSoftKeyboard(RegisterNumberActivity.this);
        }
    }

    @Override
    public void sendCode(String code) {
        continueButton.setEnabled(code.length() == 4);
        if (code.length() == 4) {
            hideSoftKeyboard(RegisterNumberActivity.this);
            showSuccess();
        }
    }
}
