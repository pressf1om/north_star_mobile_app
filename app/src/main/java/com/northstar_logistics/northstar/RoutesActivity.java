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

import java.io.File;

public class RoutesActivity extends AppCompatActivity {

    TextView idRoutesTxt, status_display, question;
    String textQuestion;
    ProgressBar progressBar;
    Button btnChangeStatus;
    String status_;
    String number_car;
    String data_status;
    int intValueForProgressBar;

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
        if (data_id != null && data_status != null) {
            idRoutesTxt.setText(String.format("ID Заявки: %s", data_id));
            status_display.setText(String.format("Статус заявки сейчас: \n%s", data_status));
        } else {
            idRoutesTxt.setText("---");
            status_display.setText("Заявка не найдена");
            btnChangeStatus.setVisibility(View.GONE);
        }


        if (status_ != null) {
            question.setText(changeQuestion(status_));

            int status_int = Integer.parseInt(status_);
            status_int = status_int - 1;

            String status_forProgressBar = String.valueOf(status_int);
            progressBar.setProgress(changeProgressBar(status_forProgressBar));

            if (status_.equals("1")) {
                status_display.setText("Сейчас заявки не найдены");
                question.setText("Пожалуйста, дождитесь назначения новой заявки");
                btnChangeStatus.setVisibility(View.GONE);
                Log.i("STATUS", "Пожалуйста, дождитесь назначения новой заявки");
            }
        }

        btnChangeStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (status_) {
                    case "2":
                        if (btnChangeStatus.getVisibility() == View.GONE) {
                            btnChangeStatus.setVisibility(View.VISIBLE);
                        }
                        progressBar.setProgress(changeProgressBar("2"));
                        data_status = "Прибыла на погрузку";
                        status_display.setText(String.format("Статус заявки сейчас: \n%s", data_status));
                        question.setText(changeQuestion("3"));
                        new PostTask().execute(number_car, "3");
                        status_ = "3";
                        break;
                    case "3":
                        if (btnChangeStatus.getVisibility() == View.GONE) {
                            btnChangeStatus.setVisibility(View.VISIBLE);
                        }
                        progressBar.setProgress(changeProgressBar("3"));
                        data_status = "Погружена";
                        status_display.setText(String.format("Статус заявки сейчас: \n%s", data_status));
                        question.setText(changeQuestion("4"));
                        new PostTask().execute(number_car, "4");
                        status_ = "4";
                        break;
                    case "4":
                        if (btnChangeStatus.getVisibility() == View.GONE) {
                            btnChangeStatus.setVisibility(View.VISIBLE);
                        }
                        progressBar.setProgress(changeProgressBar("4"));
                        data_status = "Транзит";
                        status_display.setText(String.format("Статус заявки сейчас: \n%s", data_status));
                        question.setText(changeQuestion("5"));
                        new PostTask().execute(number_car, "5");
                        status_ = "5";
                        break;
                    case "5":
                        if (btnChangeStatus.getVisibility() == View.GONE) {
                            btnChangeStatus.setVisibility(View.VISIBLE);
                        }
                        progressBar.setProgress(changeProgressBar("5"));
                        data_status = "Прибыла на выгрузку";
                        status_display.setText(String.format("Статус заявки сейчас: \n%s", data_status));
                        question.setText(changeQuestion("6"));
                        new PostTask().execute(number_car, "6");
                        status_ = "6";
                        break;
                    case "6":
                        if (btnChangeStatus.getVisibility() == View.GONE) {
                            btnChangeStatus.setVisibility(View.VISIBLE);
                        }
                        progressBar.setProgress(changeProgressBar("6"));
                        data_status = "Выгружена";
                        status_display.setText(String.format("Статус заявки сейчас: \n%s", data_status));
                        question.setText(changeQuestion("7"));
                        new PostTask().execute(number_car, "7");
                        status_ = "7";
                        break;
                    case "7":
                        if (btnChangeStatus.getVisibility() == View.GONE) {
                            btnChangeStatus.setVisibility(View.VISIBLE);
                        }
                        progressBar.setProgress(changeProgressBar("7"));
                        data_status = "Завершение рейса";
                        status_display.setText(String.format("Статус заявки сейчас: \n%s", data_status));
                        question.setText(changeQuestion("8"));
                        new PostTask().execute(number_car, "8");
                        status_ = "8";
                        break;
                    case "8":
                        if (btnChangeStatus.getVisibility() == View.GONE) {
                            btnChangeStatus.setVisibility(View.VISIBLE);
                        }
                        // добавить изменение стиля и самой кнопки для завершения заявки + обновить прогресс бар
                        progressBar.setProgress(changeProgressBar("8"));
                        data_status = "Выполнена";
                        status_display.setText(String.format("Статус заявки сейчас: \n%s", data_status));
                        question.setText("Ожидайте новой заявки");
                        new PostTask().execute(number_car, "1");
                        status_ = "1";
                        Log.i("STATUS_APPLICATION", "ВЫПОЛНЕНА");
                        break;
                }
            }
        });
    }

    // Post отправка данных
    private static class PostTask extends AsyncTask<String, Void, Void> {
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

    public int changeProgressBar (String status_) {
        // Вопрос для смены статуса
        switch (status_) {
            case "2":
                intValueForProgressBar = 13;
                break;
            case "3":
                intValueForProgressBar = 26;
                break;
            case "4":
                intValueForProgressBar = 39;
                break;
            case "5":
                intValueForProgressBar = 52;
                break;
            case "6":
                intValueForProgressBar = 65;
                break;
            case "7":
                intValueForProgressBar = 78;
                break;
            case "8":
                intValueForProgressBar = 91;
                break;
            default:
                intValueForProgressBar = 0;
                break;
        }
        return intValueForProgressBar;
    }

    public String changeQuestion(String status_) {
        // Вопрос для смены статуса
        if (status_ != null) {
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
        } else {
            textQuestion = "Заявки не найдены.";
        }
        return textQuestion;
    }

    // Боковое меню

    // Назад на страницу входа
    public void goBackBtn(View view) {
        // Создаем объект Intent для перехода на новую активность
        Intent intent = new Intent(RoutesActivity.this, StartActivity.class);

        // Удаляем файл "car_number.txt", если он существует
        File file = new File(getFilesDir(), "car_number.txt");
        if (file.exists()) {
            boolean isDeleted = file.delete();
            if (!isDeleted) {
                Log.e("FileDeletion", "Failed to delete file");
                // Дополнительная обработка ошибки, если не удалось удалить файл
            }
        } else {
            Log.i("FileDeletion", "File does not exist");
            // Дополнительная обработка, если файл не существует
        }

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

}