package com.northstar_logistics.northstar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class RoutesActivity extends AppCompatActivity {

    TextView idRoutesTxt, status_display, question;
    String textQuestion;
    ProgressBar progressBar;
    Button btnChangeStatus;
    String status_;
    String number_car;
    int valueForProgressBar;
    String data_status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routes);

        // отображение айди на верхней части страницы
        idRoutesTxt = findViewById(R.id.idRoutesTxt);

        // отображение статуса
        status_display = findViewById(R.id.status_display);

        // отображение вопроса
        question = findViewById(R.id.question);

        // Находим прогресс-бар
        progressBar = findViewById(R.id.progressBar);

        // находим кнопку
        btnChangeStatus = findViewById(R.id.btnChangeStatus);

        // Устанавливаем максимальное значение прогресса
        progressBar.setMax(91);

        // Получаем Intent, который вызвал эту активность
        Intent intent = getIntent();

        // Получаем данные из Intent
        String data_id = intent.getStringExtra("id"); // "key" - ключ, по которому получаем данные
        data_status = intent.getStringExtra("status"); // "key" - ключ, по которому получаем данные
        status_ = intent.getStringExtra("status_"); // "key" - ключ, по которому получаем данные
        number_car = intent.getStringExtra("number_car"); // "key" - ключ, по которому получаем данные

                // Устанавливаем значения TextView
        idRoutesTxt.setText(String.format("ID Заявки: %s", data_id));
        status_display.setText(String.format("Статус заявки сейчас: \n%s", data_status));


        // Вопрос для смены статуса
        switch (status_) {
            case "2":
                textQuestion = "Вы доехали до места погрузки?";
                break;
            case "3":
                textQuestion = "Вы загрузились?";
                break;
            case "4":
                textQuestion = "Вы в пути?";
                break;
            case "5":
                textQuestion = "Вы доехали до выгрузки?";
                break;
            case "6":
                textQuestion = "Вы выгрузились?";
                break;
            case "7":
                textQuestion = "Вы готовы завершить рейс?";
                break;
            case "8":
                textQuestion = "Вы готовы ехать дальше?";
                break;
            default:
                textQuestion = "Неизвестный статус машины";
                break;
        }

        question.setText(textQuestion);

        btnChangeStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (status_) {
                    case "1":
                        status_ = "2";
                        data_status = "Назначена";
                        valueForProgressBar = 13;
                    case "2":
                        question.setText("Назначена");
                        new PostTask().execute(number_car, "3");
                        status_ = "3";
                        data_status = "Прибыла на погрузку";
                        valueForProgressBar = 26;
                        break;
                    case "3":
                        question.setText("Прибыла на погрузку");
                        new PostTask().execute(number_car, "4");
                        status_ = "4";
                        data_status = "Погружена";
                        valueForProgressBar = 39;
                        break;
                    case "4":
                        question.setText("Погружена");
                        new PostTask().execute(number_car, "5");
                        status_ = "5";
                        data_status = "Транзит";
                        valueForProgressBar = 52;
                        break;
                    case "5":
                        question.setText("Транзит");
                        new PostTask().execute(number_car, "6");
                        status_ = "6";
                        data_status = "Прибыла на выгрузку";
                        valueForProgressBar = 65;
                        break;
                    case "6":
                        question.setText("Прибыла на выгрузку");
                        new PostTask().execute(number_car, "7");
                        status_ = "7";
                        data_status = "Выгружена";
                        valueForProgressBar = 78;
                        break;
                    case "7":
                        question.setText("Выгружена");
                        new PostTask().execute(number_car, "8");
                        status_ = "8";
                        data_status = "Завершение рейса";
                        valueForProgressBar = 91;
                        break;
                    case "8":
                        question.setText("Завершение рейса");
                        new PostTask().execute(number_car, "2");
                        status_ = "2";
                        data_status = "Назначена";
                        break;
                }
            }
        });

        // Устанавливаем текущее значение прогресса
        progressBar.setProgress(valueForProgressBar);
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