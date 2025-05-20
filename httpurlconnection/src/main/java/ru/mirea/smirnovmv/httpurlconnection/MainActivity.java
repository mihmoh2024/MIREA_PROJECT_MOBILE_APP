package ru.mirea.smirnovmv.httpurlconnection;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;


import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private TextView textViewIP, textViewCity, textViewRegion, textViewCountry, textViewLatitude, textViewLongitude, textViewTemperature, textViewWindSpeed;
    private Button buttonLoad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewIP = findViewById(R.id.textViewIP);
        textViewCity = findViewById(R.id.textViewCity);
        textViewRegion = findViewById(R.id.textViewRegion);
        textViewCountry = findViewById(R.id.textViewCountry);
        textViewLatitude = findViewById(R.id.textViewLatitude);
        textViewLongitude = findViewById(R.id.textViewLongitude);
        textViewTemperature = findViewById(R.id.textViewTemperature);
        textViewWindSpeed = findViewById(R.id.textViewWindSpeed);
        buttonLoad = findViewById(R.id.buttonLoad);

        buttonLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new DownloadIpInfoTask().execute("https://ipinfo.io/json");
            }
        });
    }

    // Загрузка информации о IP и локации
    private class DownloadIpInfoTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            buttonLoad.setEnabled(false);
            textViewIP.setText("Загрузка...");
            textViewCity.setText("");
            textViewRegion.setText("");
            textViewCountry.setText("");
            textViewLatitude.setText("");
            textViewLongitude.setText("");
            textViewTemperature.setText("");
            textViewWindSpeed.setText("");
        }

        @Override
        protected String doInBackground(String... urls) {
            try {
                return downloadUrl(urls[0]);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            buttonLoad.setEnabled(true);
            if (result == null) {
                Toast.makeText(MainActivity.this, "Ошибка загрузки данных", Toast.LENGTH_LONG).show();
                return;
            }

            try {
                JSONObject json = new JSONObject(result);

                String ip = json.optString("ip", "N/A");
                String city = json.optString("city", "N/A");
                String region = json.optString("region", "N/A");
                String country = json.optString("country", "N/A");
                String loc = json.optString("loc", "0,0");

                String[] coords = loc.split(",");
                String latitude = coords.length > 0 ? coords[0] : "0";
                String longitude = coords.length > 1 ? coords[1] : "0";

                textViewIP.setText("IP: " + ip);
                textViewCity.setText("Город: " + city);
                textViewRegion.setText("Регион: " + region);
                textViewCountry.setText("Страна: " + country);
                textViewLatitude.setText("Широта: " + latitude);
                textViewLongitude.setText("Долгота: " + longitude);

                // Запускаем загрузку погоды по координатам
                new DownloadWeatherTask().execute(latitude, longitude);

            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(MainActivity.this, "Ошибка обработки данных", Toast.LENGTH_LONG).show();
            }
        }
    }

    // Задача для загрузки погоды с Open-Meteo
    private class DownloadWeatherTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            textViewTemperature.setText("Загрузка погоды...");
            textViewWindSpeed.setText("");
        }

        @Override
        protected String doInBackground(String... params) {
            String latitude = params[0];
            String longitude = params[1];
            String url = "https://api.open-meteo.com/v1/forecast?latitude=" + latitude + "&longitude=" + longitude + "&current_weather=true";

            try {
                return downloadUrl(url);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            if (result == null) {
                textViewTemperature.setText("Ошибка загрузки погоды");
                return;
            }

            try {
                JSONObject json = new JSONObject(result);
                JSONObject currentWeather = json.optJSONObject("current_weather");
                if (currentWeather != null) {
                    double temperature = currentWeather.optDouble("temperature", Double.NaN);
                    double windspeed = currentWeather.optDouble("windspeed", Double.NaN);

                    textViewTemperature.setText("Температура: " + (Double.isNaN(temperature) ? "N/A" : temperature + " °C"));
                    textViewWindSpeed.setText("Скорость ветра: " + (Double.isNaN(windspeed) ? "N/A" : windspeed + " км/ч"));
                } else {
                    textViewTemperature.setText("Погода недоступна");
                }
            } catch (JSONException e) {
                e.printStackTrace();
                textViewTemperature.setText("Ошибка обработки погоды");
            }
        }
    }

    // Метод для скачивания данных по URL
    private String downloadUrl(String urlString) throws IOException {
        InputStream inputStream = null;
        HttpURLConnection connection = null;
        try {
            URL url = new URL(urlString);
            connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            connection.setRequestMethod("GET");
            connection.setDoInput(true);

            connection.connect();

            int responseCode = connection.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                return null;
            }

            inputStream = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder sb = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            return sb.toString();

        } finally {
            if (inputStream != null) inputStream.close();
            if (connection != null) connection.disconnect();
        }
    }
}
