package com.example.lesson2;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class MyDialogFragment extends DialogFragment {

    private String message;
    private static boolean dialogShown = false;

    public static boolean isDialogShown() {
        return dialogShown;
    }

    public MyDialogFragment() {
    }

    public MyDialogFragment(String message) {
        this.message = message;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dialogShown = false;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        dialogShown = true;
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.error)
                .setMessage(message)
                .setIcon(android.R.drawable.stat_notify_error)
                .setPositiveButton("Ок", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Закрываем окно
                        dismiss();
                    }
                });
        return builder.create();
    }
}
