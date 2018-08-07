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
import com.jumpitt.trust.ui.register.data.DataFragment;
import com.jumpitt.trust.ui.success.SuccessActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTouch;

import static com.jumpitt.trust.ui.success.SuccessActivity.DESCRIPTION_EXTRA;
import static com.jumpitt.trust.ui.success.SuccessActivity.IMAGE_EXTRA;
import static com.jumpitt.trust.ui.success.SuccessActivity.TITLE_EXTRA;
import static com.jumpitt.trust.ui.success.SuccessActivity.TYPE_EXTRA;
import static com.jumpitt.trust.ui.success.SuccessActivity.TYPE_REGISTER;

/**
 * Created by felipe on 25-04-18.
 */

public class RegisterPersonalDataActivity extends BaseActivity implements RegisterPersonalDataContract.View {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    MenuItem continueButton;

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_container);
        ButterKnife.bind(this);

        toolbar.setTitle("Registro");
        setSupportActionBar(toolbar);

        if (getActionBar() != null) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }

        fragmentManager = getSupportFragmentManager();

        replaceFragment(fragmentManager, R.id.register_container, DataFragment.newInstance(), "Phone", false, false);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.continue_menu, menu);
        continueButton = menu.findItem(R.id.nav_continue);
        continueButton.setEnabled(true);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_continue:
                showSuccess();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showSuccess() {
        Intent intent = new Intent(this, SuccessActivity.class);
        intent.putExtra(IMAGE_EXTRA, R.drawable.ic_register_success);
        intent.putExtra(TITLE_EXTRA, "Usuario registrado, Bienvenido!");
        intent.putExtra(DESCRIPTION_EXTRA, "Validamos todos tus datos de manera correcta, ahora eres un nuevo usuario de Trust. Cualquier dato lo pudedes editar en tu perfil... Disfruta de la aplicación!");
        intent.putExtra(TYPE_EXTRA, TYPE_REGISTER);
        startActivity(intent);
    }

    @OnTouch
    boolean onTouch() {
        hideSoftKeyboard(RegisterPersonalDataActivity.this);
        return false;
    }
}
