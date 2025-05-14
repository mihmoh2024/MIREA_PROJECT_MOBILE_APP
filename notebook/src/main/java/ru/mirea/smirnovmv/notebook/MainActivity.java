package ru.mirea.smirnovmv.notebook;

import android.os.Bundle;
import android.os.Environment;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.io.*;

public class MainActivity extends AppCompatActivity {

    private EditText eTNameFile;
    private EditText eTQuote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        eTNameFile = findViewById(R.id.eT_namefile);
        eTQuote = findViewById(R.id.eT_quote);
        Button bSave = findViewById(R.id.b_save);
        Button bLoad = findViewById(R.id.b_load);

        bSave.setOnClickListener(v -> saveFile());
        bLoad.setOnClickListener(v -> loadFile());
    }

    private void saveFile() {
        String fileName = eTNameFile.getText().toString();
        String content = eTQuote.getText().toString();

        if (fileName.isEmpty()) {
            showToast("Введите название файла");
            return;
        }

        try {
            File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
            if (!path.exists()) path.mkdirs();

            File file = new File(path, fileName);

            try (FileOutputStream fos = new FileOutputStream(file, false)) {
                fos.write(content.getBytes());
                showToast("Файл сохранён: " + file.getAbsolutePath());
            }
        } catch (Exception e) {
            showToast("Ошибка сохранения: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void loadFile() {
        String fileName = eTNameFile.getText().toString();

        if (fileName.isEmpty()) {
            showToast("Введите название файла");
            return;
        }

        try {
            File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
            File file = new File(path, fileName);

            if (!file.exists()) {
                showToast("Файл не найден");
                return;
            }

            StringBuilder content = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(file), "UTF-8"))) {

                String line;
                while ((line = reader.readLine()) != null) {
                    content.append(line).append("\n");
                }
            }

            eTQuote.setText(content.toString().trim());
            showToast("Данные успешно загружены");

        } catch (Exception e) {
            showToast("Ошибка загрузки: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
