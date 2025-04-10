package ru.mirea.smirnovmv.cryptoloader;

import static ru.mirea.smirnovmv.cryptoloader.MainActivity.decryptMsg;

import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.loader.content.AsyncTaskLoader;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class MyLoader extends AsyncTaskLoader<String> {
    private byte[] encryptedData;
    private byte[] keyBytes;

    public static final String ARG_ENCRYPTED = "encrypted";
    public static final String ARG_KEY = "key";

    public MyLoader(@NonNull Context context, Bundle args) {
        super(context);
        if (args != null) {
            encryptedData = args.getByteArray(ARG_ENCRYPTED);
            keyBytes = args.getByteArray(ARG_KEY);
        }
    }
    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }
    @Override
    public String loadInBackground() {
        try {
            SecretKey secretKey = new SecretKeySpec(keyBytes, "AES");
            return decryptMsg(encryptedData, secretKey);
        } catch (Exception e) {
            Log.e("MyLoader", "Decryption error", e);
            return null;
        }
    }

}