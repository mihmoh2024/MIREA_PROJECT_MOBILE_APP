package ru.mirea.smirnovmv.dialog;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import java.util.Calendar;

public class MyDateDialogFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // Получаем текущее время
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Создаем DatePickerDialog
        return new DatePickerDialog(getActivity(), (view, selectedYear, selectedMonth, selectedDay) -> {
            // Обработка выбора даты
            if (getActivity() instanceof DialogActivity) {
                ((DialogActivity) getActivity()).onDateSet(selectedYear, selectedMonth, selectedDay);
            }
        }, year, month, day);
    }
}
