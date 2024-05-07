package com.northstar_logistics.northstar;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

                    // приводим статусы к нормальному виду
                    switch (status) {
                        case "1":
                            status = "Свободна";
                            break;
                        case "2":
                            status = "Назначена";
                            break;
                        case "3":
                            status = "Прибыла на погрузку";
                            break;
                        case "4":
                            status = "Погружена";
                            break;
                        case "5":
                            status = "Транзит";
                            break;
                        case "6":
                            status = "Прибыла на выгрузку";
                            break;
                        case "7":
                            status = "Выгружена";
                            break;
                        case "8":
                            status = "Завершение рейса";
                            break;
                        case "9":
                            status = "Техническое обслуживание";
                            break;
                    }

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
    // Боковое меню

    // Назад на главную страницу
    public void goBackBtn(View view) {
        // Создаем объект Intent для перехода на новую активность
        Intent intent = new Intent(MainActivity.this, StartActivity.class);

        // Запускаем новую активность
        startActivity(intent);
    }

    // Переход на страницу "Маршрут"
    public void goRoutes(View view) {
        // Создаем объект Intent для перехода на новую активность
        Intent intent = new Intent(MainActivity.this, RoutesActivity.class);

        // Запускаем новую активность
        startActivity(intent);
    }

    // Переход на страницу "Машина"
    public void goCar(View view) {
        // Создаем объект Intent для перехода на новую активность
        Intent intent = new Intent(MainActivity.this, CarActivity.class);

        // Запускаем новую активность
        startActivity(intent);
    }

    // Переход на страницу "Архив"
    public void goArchive(View view) {
        // Создаем объект Intent для перехода на новую активность
        Intent intent = new Intent(MainActivity.this, ArchiveActivity.class);

        // Запускаем новую активность
        startActivity(intent);
    }
}
