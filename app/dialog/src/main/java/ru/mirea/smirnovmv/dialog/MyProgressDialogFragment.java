package ru.mirea.smirnovmv.dialog;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class MyProgressDialogFragment extends DialogFragment {

    private ProgressDialog progressDialog;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Загрузка...");
        progressDialog.setCancelable(false);
        return progressDialog;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (progressDialog != null) {
            progressDialog.show();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
}
