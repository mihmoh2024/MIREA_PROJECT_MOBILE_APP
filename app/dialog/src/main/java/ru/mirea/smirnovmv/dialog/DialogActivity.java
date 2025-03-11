package ru.mirea.smirnovmv.dialog;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.google.android.material.snackbar.Snackbar;

public class DialogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dialog);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void onClickShowDialog(View view) {
        AlertDialogFragment dialogFragment = new AlertDialogFragment();
        dialogFragment.show(getSupportFragmentManager(), "mirea");
    }

    public void onOkClicked() {
        Toast.makeText(getApplicationContext(), "Вы выбрали кнопку \"Иду дальше\"!",
                Toast.LENGTH_LONG).show();
        showSnackbar("Вы выбрали кнопку \"Иду дальше\"!");
    }

    public void onCancelClicked() {
        Toast.makeText(getApplicationContext(), "Вы выбрали кнопку \"Нет\"!",
                Toast.LENGTH_LONG).show();
        showSnackbar("Вы выбрали кнопку \"Нет\"!");
    }

    public void onNeutralClicked() {
        Toast.makeText(getApplicationContext(), "Вы выбрали кнопку \"На паузе\"!",
                Toast.LENGTH_LONG).show();
        showSnackbar("Вы выбрали кнопку \"На паузе\"!");
    }

    private void showSnackbar(String message) {
        View rootView = findViewById(R.id.main);
        Snackbar.make(rootView, message, Snackbar.LENGTH_LONG).show();
    }

    public void onClickShowTimePicker(View view) {
        MyTimeDialogFragment timeDialogFragment = new MyTimeDialogFragment();
        timeDialogFragment.show(getSupportFragmentManager(), "timePicker");
    }
    public void onTimeSet(int hourOfDay, int minute) {
        String time = String.format("Вы выбрали: %02d:%02d", hourOfDay, minute);
        Toast.makeText(getApplicationContext(), time, Toast.LENGTH_LONG).show();
        showSnackbar(time);
    }
    public void onClickShowDatePicker(View view) {
        MyDateDialogFragment dateDialogFragment = new MyDateDialogFragment();
        dateDialogFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void onDateSet(int year, int month, int day) {
        // Месяцы начинаются с 0, поэтому добавляем 1
        String date = String.format("Вы выбрали: %02d.%02d.%04d", day, month + 1, year);
        Toast.makeText(getApplicationContext(), date, Toast.LENGTH_LONG).show();
        showSnackbar(date);
    }

    public void onClickShowProgressDialog(View view) {
        MyProgressDialogFragment progressDialogFragment = new MyProgressDialogFragment();
        progressDialogFragment.show(getSupportFragmentManager(), "progressDialog");
    }
}
