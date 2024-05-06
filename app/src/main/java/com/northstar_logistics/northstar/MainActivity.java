package com.northstar_logistics.northstar;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    TextView car_number_text_main_activity;
    String number_car;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Вызов GET запроса
        // new GetTask().execute(number_car);

        // Вызов POST запроса
        // new PostTask().execute(number_car, "5");\

        car_number_text_main_activity = findViewById(R.id.car_number_text_main_activity);

        // Получаем путь к файлу car_number.txt внутреннего хранилища приложения
        File file = new File(getFilesDir(), "car_number.txt");

        try {
            // Создаем объект FileReader для чтения данных из файла
            FileReader reader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(reader);

            // Считываем строку из файла и сохраняем в переменную number_car
            String number_car = bufferedReader.readLine();

            car_number_text_main_activity.setText(number_car);

            // Закрываем потоки
            bufferedReader.close();
            reader.close();

        } catch (IOException e) {
            // В случае ошибки выводим сообщение об ошибке
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Ошибка при чтении данных из файла", Toast.LENGTH_SHORT).show();
        }
    }

    // получение данных
    private class GetTask extends AsyncTask<String, Void, JSONObject> {
        @Override
        protected JSONObject doInBackground(String... params) {
            try {
                String jsonResponse = GetPostWorking.get(params[0]);
                if (jsonResponse != null) {
                    Log.i("NETWORK", "Объект получен");
                    return new JSONObject(jsonResponse);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        // действия после завершения
        @Override
        protected void onPostExecute(JSONObject result) {
            if (result != null) {
                try {
                    String status = result.getString("status");

                    String displayText = "Car Number: " + number_car + "\n"
                            + "Status: " + status + "\n";

                    // main_text.setText(displayText);
                    Log.i("NETWORK", "Объект выведен");
                } catch (JSONException e) {
                    e.printStackTrace();
                    // main_text.setText("Ошибка при распарсивании JSON: " + e.getMessage());
                }
            } else {
                // main_text.setText("Ошибка при выполнении GET запроса");
            }
        }
    }

    // Post отправка данных
    private class PostTask extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            try {
                GetPostWorking.post(params[0], params[1]);
                Log.i("NETWORK", "Объект отправлен Post запросом");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        // действия после завершения
        @Override
        protected void onPostExecute(Void aVoid) {

        }
    }
}
