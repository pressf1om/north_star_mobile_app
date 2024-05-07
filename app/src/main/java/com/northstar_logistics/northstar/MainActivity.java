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

    // Надпись номера машины на главной страницу
    TextView car_number_text_main_activity;
    // Номер машины получаемый из текстового файла
    String number_car;
    // Надписи на главной странице
    TextView id_txt, status_txt, date_of_start_txt, coord_start_txt, coord_end_txt, weight_txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // находим элементы TextView
        car_number_text_main_activity = findViewById(R.id.car_number_text_main_activity);
        id_txt = findViewById(R.id.id);
        status_txt = findViewById(R.id.status);
        date_of_start_txt = findViewById(R.id.date_of_start);
        coord_start_txt = findViewById(R.id.coord_start);
        coord_end_txt = findViewById(R.id.coord_end);
        weight_txt = findViewById(R.id.weight);


        // Получаем путь к файлу car_number.txt внутреннего хранилища приложения
        File file = new File(getFilesDir(), "car_number.txt");

        try {
            // Создаем объект FileReader для чтения данных из файла
            FileReader reader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(reader);

            // Считываем строку из файла и сохраняем в переменную number_car
            number_car = bufferedReader.readLine();

            // Устанавливаем номер автомобиля на главной странице
            car_number_text_main_activity.setText(number_car);

            // Закрываем потоки
            bufferedReader.close();
            reader.close();

        } catch (IOException e) {
            // В случае ошибки выводим сообщение об ошибке
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Ошибка при чтении данных из файла", Toast.LENGTH_SHORT).show();
        }

        // Вызов GET запроса
        new GetTask().execute(number_car);

        // Вызов POST запроса
        // new PostTask().execute(number_car, "5");\
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
                } else {
                    Log.i("NETWORK", "jsonResponse != null");
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
                    // Парсим json
                    String id = result.getString("id");
                    String status = result.getString("status");
                    String date_of_start = result.getString("date_of_start");
                    String coord_start = result.getString("coord_start");
                    String coord_end = result.getString("coord_end");
                    String weight = result.getString("weight");

                    // устанавливаем значения из json
                    id_txt.setText(String.format("ID: %s", id));
                    status_txt.setText(String.format("Статус заявки: %s", status));
                    date_of_start_txt.setText(String.format("Дата начала: %s", date_of_start));
                    coord_start_txt.setText(String.format("Начальные координаты: \n%s", coord_start));
                    coord_end_txt.setText(String.format("Конечные координаты: \n%s", coord_end));
                    weight_txt.setText(String.format("Вес груза (в тоннах): %s", weight));

                    Log.i("NETWORK", "Объект выведен");

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("JSON", "Ошибка при распарсивании JSON: " + e.getMessage());
                    Toast.makeText(getApplicationContext(),
                            "Ошибка при распарсивании JSON: " + e.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            } else {
                Log.e("GET", "Ошибка при выполнении GET запроса");
                Toast.makeText(getApplicationContext(),
                        "Ошибка при выполнении GET запроса",
                        Toast.LENGTH_SHORT).show();
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
