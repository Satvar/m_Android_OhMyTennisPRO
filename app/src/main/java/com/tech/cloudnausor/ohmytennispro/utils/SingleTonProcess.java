package com.tech.cloudnausor.ohmytennispro.utils;

import android.app.Activity;
import android.content.Context;
import android.view.Window;

import androidx.appcompat.app.AlertDialog;

import com.tech.cloudnausor.ohmytennispro.R;

public class SingleTonProcess {

    AlertDialog.Builder builder;
    AlertDialog dialog;
    private static final SingleTonProcess ourInstance = new SingleTonProcess();

    public static SingleTonProcess getInstance() {
        return ourInstance;
    }

    private SingleTonProcess() { }

    public void show(Context context) {
        if (dialog != null && dialog.isShowing()) {
            return;
        }
        builder = new AlertDialog.Builder(context);
        //View view = getLayoutInflater().inflate(R.layout.progress);
        builder.setView(R.layout.progressdialoglayout);
        builder.setCancelable(false);
        dialog  = builder.create();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();
    }

    public void dismiss() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}
