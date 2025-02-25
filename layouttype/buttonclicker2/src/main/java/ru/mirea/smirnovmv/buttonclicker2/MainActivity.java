package ru.mirea.smirnovmv.buttonclicker2;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CheckBox;

public class MainActivity extends AppCompatActivity {
    private Button btnWhoAmI;
    private Button btnItIsNotMe;
    private TextView tvOut;
    private CheckBox cbMyCheckBox;
    public void onItIsNotMeClick(View v)
    {
        tvOut.setText("Это не я сделал");
        Toast.makeText(this, "Ещё один способ!", Toast.LENGTH_SHORT).show();
        cbMyCheckBox.setChecked(false);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        btnWhoAmI = findViewById(R.id.btnWhoAmI);
        btnItIsNotMe = findViewById(R.id.btnItIsNotMe);
        tvOut = (TextView) findViewById(R.id.tvOut);
        cbMyCheckBox = findViewById(R.id.cbMyCheckBox);


        btnWhoAmI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvOut.setText("Мой номер по списку № 13 (по журналу)");
                cbMyCheckBox.setChecked(true);
            }
        });

        /*
        btnItIsNotMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvOut.setText("Это не я сделал");
                cbMyCheckBox.setChecked(false);
            }
        });
        */

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}