package ru.mirea.smirnovmv.dialog;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import java.util.Calendar;

public class MyTimeDialogFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // Получаем текущее время
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Создаем TimePickerDialog
        return new TimePickerDialog(getActivity(), (view, selectedHour, selectedMinute) -> {
            // Обработка выбора времени
            if (getActivity() instanceof DialogActivity) {
                ((DialogActivity) getActivity()).onTimeSet(selectedHour, selectedMinute);
            }
        }, hour, minute, true);
    }
}