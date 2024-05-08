package com.northstar_logistics.northstar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class RoutesActivity extends AppCompatActivity {

    TextView idRoutesTxt, status_display, question;
    String textQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routes);

        idRoutesTxt = findViewById(R.id.idRoutesTxt);
        status_display = findViewById(R.id.status_display);
        question = findViewById(R.id.question);

        // Получаем Intent, который вызвал эту активность
        Intent intent = getIntent();

        // Получаем данные из Intent
        String data_id = intent.getStringExtra("id"); // "key" - ключ, по которому получаем данные
        String data_status = intent.getStringExtra("status"); // "key" - ключ, по которому получаем данные

        // Устанавливаем значения TextView
        idRoutesTxt.setText(String.format("ID Заявки: %s", data_id));
        status_display.setText(String.format("Статус заявки сейчас: \n%s", data_status));


        // Вопрос для смены статуса
        switch (data_status) {
            case "Назначена":
                textQuestion = "Вы доехали до места погрузки?";
                break;
            case "Прибыла на погрузку":
                textQuestion = "Вы загрузились?";
                break;
            case "Погружена":
                textQuestion = "Вы в пути?";
                break;
            case "Транзит":
                textQuestion = "Вы доехали до выгрузки?";
                break;
            case "Прибыла на выгрузку":
                textQuestion = "Вы выгрузились?";
                break;
            case "Выгружена":
                textQuestion = "Вы готовы завершить рейс?";
                break;
            case "На Т.О.":
                textQuestion = "Вы обслужили машину?";
                break;
            case "Завершение рейса":
                textQuestion = "Вы готовы ехать дальше?";
                break;
            default:
                textQuestion = "Неизвестный статус машины";
                break;
        }

        question.setText(textQuestion);


        // Вызов POST запроса
        // new PostTask().execute(number_car, "5");\
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

    // Боковое меню

    // Назад на страницу входа
    public void goBackBtn(View view) {
        // Создаем объект Intent для перехода на новую активность
        Intent intent = new Intent(RoutesActivity.this, StartActivity.class);

        // Запускаем новую активность
        startActivity(intent);
    }

    // Переход на страницу "Главная"
    public void goMain(View view) {
        // Создаем объект Intent для перехода на новую активность
        Intent intent = new Intent(RoutesActivity.this, MainActivity.class);

        // Запускаем новую активность
        startActivity(intent);
    }

    // Переход на страницу "Машина"
    public void goCar(View view) {
        // Создаем объект Intent для перехода на новую активность
        Intent intent = new Intent(RoutesActivity.this, CarActivity.class);

        // Запускаем новую активность
        startActivity(intent);
    }

    // Переход на страницу "Архив"
    public void goArchive(View view) {
        // Создаем объект Intent для перехода на новую активность
        Intent intent = new Intent(RoutesActivity.this, ArchiveActivity.class);

        // Запускаем новую активность
        startActivity(intent);
    }
}