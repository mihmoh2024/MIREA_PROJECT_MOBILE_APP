package ru.mirea.smirnovmv.internalfilestorage;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private static final String FILE_NAME = "data.txt";

    private EditText eTData;
    private Button bSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        eTData = findViewById(R.id.eT_data);
        bSave = findViewById(R.id.b_save);

        bSave.setOnClickListener(v -> {
            String data = eTData.getText().toString();
            try (FileOutputStream outputStream = openFileOutput(FILE_NAME, Context.MODE_PRIVATE)) {
                outputStream.write(data.getBytes());
                Toast.makeText(MainActivity.this, "Данные сохранены в файл", Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                Log.e(LOG_TAG, "Ошибка при записи файла", e);
                Toast.makeText(MainActivity.this, "Ошибка при сохранении данных", Toast.LENGTH_LONG).show();
            }
        });
    }
}
