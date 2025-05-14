package ru.mirea.smirnovmv.mireaproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class profile extends Fragment {

    private static final String PREFS_NAME = "MyPrefs";
    private static final String KEY_NAME = "key_name";
    private static final String KEY_SURNAME = "key_surname";
    private static final String KEY_GROUP = "key_group";

    private EditText eTName;
    private EditText eTSurname;
    private EditText eTGroupMirea;
    private Button bSave;
    private Button bLoad;

    private SharedPreferences sharedPref;

    public profile() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        eTName = view.findViewById(R.id.eT_name);
        eTSurname = view.findViewById(R.id.eT_surname);
        eTGroupMirea = view.findViewById(R.id.eT_groupmirea);
        bSave = view.findViewById(R.id.b_save);
        bLoad = view.findViewById(R.id.b_load);

        sharedPref = requireActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        bSave.setOnClickListener(v -> {
            savePreferences();
            Toast.makeText(getContext(), "Данные сохранены", Toast.LENGTH_LONG).show();
        });

        bLoad.setOnClickListener(v -> {
            loadPreferences();
            Toast.makeText(getContext(), "Данные загружены", Toast.LENGTH_SHORT).show();
        });
    }

    private void savePreferences() {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(KEY_NAME, eTName.getText().toString());
        editor.putString(KEY_SURNAME, eTSurname.getText().toString());
        editor.putString(KEY_GROUP, eTGroupMirea.getText().toString());
        editor.apply();
    }

    private void loadPreferences() {
        eTName.setText(sharedPref.getString(KEY_NAME, ""));
        eTSurname.setText(sharedPref.getString(KEY_SURNAME, ""));
        eTGroupMirea.setText(sharedPref.getString(KEY_GROUP, ""));
    }
}
