package ru.mirea.smirnovmv.data_thread;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.concurrent.TimeUnit;

import ru.mirea.smirnovmv.data_thread.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        final Runnable runn1 = new Runnable() {
            public void run() {
                binding.tvInfo.setText("runn1");
            }
        };
        final Runnable runn2 = new Runnable() {
            public void run() {
                binding.tvInfo.setText("runn2");
            }
        };
        final Runnable runn3 = new Runnable() {
            public void run() {
                binding.tvInfo.setText("runn3");
            }
        };

        // Начинаем новый поток
        Thread t = new Thread(new Runnable() {
            public void run() {
                try {
                    // Задержка 2 секунды перед выполнением первого Runnable
                    TimeUnit.SECONDS.sleep(2);
                    // Немедленное выполнение runn1
                    runOnUiThread(runn1);

                    // Задержка 1 секунда перед выполнением второго Runnable
                    TimeUnit.SECONDS.sleep(1);
                    // Запланировать выполнение третьего Runnable через 2 секунды
                    binding.tvInfo.postDelayed(runn3, 2000);

                    //добавление второго Runnable в очередь
                    binding.tvInfo.post(runn2);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();

        // Обработка отступов для системных баров
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
