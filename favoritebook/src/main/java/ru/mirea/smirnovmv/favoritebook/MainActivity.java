package ru.mirea.smirnovmv.favoritebook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private ActivityResultLauncher<Intent> activityResultLauncher;
    static final String BOOK_NAME_KEY = "book_name";
    static final String QUOTES_KEY = "quotes_name";
    static final String USER_MESSAGE = "MESSAGE";
    private TextView textViewUserBook;

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        textViewUserBook = findViewById(R.id.textView);

        // Инициализация ActivityResultLauncher
        activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            assert data != null;
                            String userBook = data.getStringExtra(MainActivity.USER_MESSAGE);
                            String quote = data.getStringExtra(ShareActivity.QUOTES_KEY);
                            // Отображаем текст и цитату
                            textViewUserBook.setText("Название Вашей любимой книги: " + userBook + ". Цитата: " + quote);
                        }
                    }
                }
        );
        Button buttonOpenInput = findViewById(R.id.buttonOpenInput);
        buttonOpenInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ShareActivity.class);
                activityResultLauncher.launch(intent); // используем activityResultLauncher
            }
        });
    }
}
    /*
    private static final String DEFAULT_TEXT = "Тут появится название вашей любимой книги и любимая цитата из нее!";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        Button buttonOpenInput = findViewById(R.id.buttonOpenInput);
        buttonOpenInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, InputActivity.class);
                startActivityForResult(intent, 1);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK ) {
            String bookName = data.getStringExtra("BOOK_NAME");
            String quote = data.getStringExtra("QUOTE");
            textView.setText("Название Вашей любимой книги: " + bookName + ". Цитата: " + quote);
        }
    }
     */