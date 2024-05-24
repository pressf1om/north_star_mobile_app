package com.northstar_logistics.northstar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ArchiveActivity extends AppCompatActivity {

    TextView car_number_text, main_text;
    String number_car;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_archive);

        // Ищем элемент по ID
        car_number_text = findViewById(R.id.upperTxt);
        main_text = findViewById(R.id.main_text);

        // Получаем путь к файлу car_number.txt внутреннего хранилища приложения
        File file = new File(getFilesDir(), "car_number.txt");

        try {
            // Создаем объект FileReader для чтения данных из файла
            FileReader reader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(reader);

            // Считываем строку из файла и сохраняем в переменную number_car
            number_car = bufferedReader.readLine();

            // Устанавливаем номер автомобиля на главной странице
            car_number_text.setText(number_car);

            // Закрываем потоки
            bufferedReader.close();
            reader.close();

        } catch (IOException e) {
            // В случае ошибки выводим сообщение об ошибке
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Ошибка при чтении данных из файла", Toast.LENGTH_SHORT).show();
        }

        // Вызываем метод для выполнения GET-запроса
        getData(number_car);
    }

    // Метод для выполнения GET-запроса к серверу
    private void getData(String carNumber) {
        OkHttpClient client = new OkHttpClient();

        // Создаем запрос к указанному URL с номером машины
        Request request = new Request.Builder()
                .url("http://northstar-logistics.ru/api/completed_applications/" + carNumber)
                .build();

        // Отправляем запрос асинхронно
        client.newCall(request).enqueue(new Callback() {
            // в случае ошибки
            @Override
            public void onFailure(Call call, IOException e) {
                // В случае ошибки выводим сообщение об ошибке
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(ArchiveActivity.this, "Ошибка при выполнении запроса", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            // успешный запрос
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    // Получаем тело ответа и выводим его в логи
                    final String responseData = response.body().string();
                    Log.d("ArchiveActivity", "Ответ на GET-запрос: " + responseData);

                    // Обновляем TextView с полученными данными
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            main_text.setText(responseData);
                        }
                    });
                } else {
                    Log.e("ArchiveActivity", "Ошибка при выполнении GET-запроса: " + response.code());
                }
            }

        });
    }

    // Боковое меню

    // Назад на страницу входа
    public void goBackBtn(View view) {
        // Создаем объект Intent для перехода на новую активность
        Intent intent = new Intent(ArchiveActivity.this, StartActivity.class);

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
        Intent intent = new Intent(ArchiveActivity.this, MainActivity.class);

        // Запускаем новую активность
        startActivity(intent);
    }

}