package com.jumpitt.trustlib.utils;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

import com.jumpitt.trustlib.R;

public class Permissions {
    public static final int REQUEST_PERMISSION = 1991;
    private Activity mActivity;
    private String[] permissions;
    private AlertDialog rationaleDialog;

    public Permissions(Activity mActivity, String[] permissions) {
        this.mActivity = mActivity;
        this.permissions = permissions;
        initDialog();
    }

    private void initDialog() {
        rationaleDialog = new AlertDialog.Builder(mActivity)
                .setTitle("Permisos Obligatorios")
                .setMessage(mActivity.getString(R.string.app_name) + " necesita acceder a esta información para ofrecerle mayor seguridad en las transacciones.")
                .setPositiveButton("Permitir", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        requestPermission();
                    }
                })
                .setCancelable(false)
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mActivity.finish();
                    }
                }).create();
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(mActivity, permissions, REQUEST_PERMISSION);
    }

    public boolean areGranted() {
        for (String permission : permissions) {
            if (ActivityCompat.checkSelfPermission(mActivity, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    public void shouldShowRequestPermissionRationale() {
        for (String permission : permissions) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(mActivity,
                    permission)) {
                rationaleDialog.show();
                return;
            }
        }
        requestPermissions();
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(mActivity,
                new String[]{Manifest.permission.READ_PHONE_STATE, Manifest.permission.ACCESS_COARSE_LOCATION},
                REQUEST_PERMISSION);
    }
}
