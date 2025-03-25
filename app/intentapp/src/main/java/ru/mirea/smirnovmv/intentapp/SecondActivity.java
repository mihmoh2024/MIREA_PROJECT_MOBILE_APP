package ru.mirea.smirnovmv.intentapp;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_second);

        // Получаем переданные данные
        String dateString = getIntent().getStringExtra("CURRENT_TIME");
        int groupNumber = getIntent().getIntExtra("GROUP_NUMBER", 0);

        // Вычисляем квадрат значения номера
        int squareValue = groupNumber * groupNumber;

        // Формируем сообщение
        String message = "КВАДРАТ ЗНАЧЕНИЯ МОЕГО НОМЕРА ПО СПИСКУ В ГРУППЕ СОСТАВЛЯЕТ ЧИСЛО "
                + squareValue + ", а текущее время " + dateString;

        // Отображаем сообщение в TextView
        TextView textView = findViewById(R.id.textView);
        textView.setText(message);


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}