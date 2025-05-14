package ru.mirea.smirnovmv.securesharedpreferences;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;

import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    private TextView tVName;
    private ImageView iVPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tVName = findViewById(R.id.tV_name);
        iVPhoto = findViewById(R.id.iV_photo);

        try {
            // Мастер-ключ для шифрования
            String mainKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);

            // Создаём SharedPreferences
            SharedPreferences secureSharedPreferences = EncryptedSharedPreferences.create(
                    "secret_shared_prefs",
                    mainKeyAlias,
                    getBaseContext(),
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );

            // Получаем имя поэта
            String actorName = secureSharedPreferences.getString("secure", "Слава КПСС (1990 - н.в)");
            tVName.setText(actorName);

            // Заносим строки с именем файла в  SharedPreferences.Editor
            SharedPreferences.Editor editor = secureSharedPreferences.edit();
            editor.putString("secure", "Слава КПСС (1990 - н.в)");
            editor.putString("slavakpss", "slavakpss");
            editor.apply();

            // Получаем имя файла изображения из SharedPreferences
            String photoName = secureSharedPreferences.getString("slavakpss", "slavakpss");

            // Получаем ресурс изображения из папки raw по имени
            int imageResId = getResources().getIdentifier(photoName, "raw", getPackageName());

            // Открываем InputStream из raw ресурса
            Resources res = getResources();
            InputStream inputStream = res.openRawResource(imageResId);

            // Создаем Drawable из InputStream
            Drawable drawable = Drawable.createFromStream(inputStream, photoName);

            // Устанавливаем изображение в ImageView
            iVPhoto.setImageDrawable(drawable);

        } catch (Exception e) {
            e.printStackTrace();
            tVName.setText("Ошибка загрузки данных");
        }
    }
}
