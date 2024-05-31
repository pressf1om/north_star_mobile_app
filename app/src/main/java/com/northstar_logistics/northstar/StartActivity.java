package com.northstar_logistics.northstar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class StartActivity extends AppCompatActivity {

    EditText carNumber;
    String carNumberText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        // Получаем путь к файлу внутреннего хранилища приложения
        File file = new File(getFilesDir(), "car_number.txt");

        // Проверяем, существует ли файл
        if (file.exists()) {
            // Если файл существует, запускаем MainActivity
            Intent intent = new Intent(StartActivity.this, MainActivity.class);
            startActivity(intent);
            // Завершаем текущую активность, чтобы пользователь не мог вернуться к ней
            finish();
        }
    }

    public void saveBtn(View view) {
        // Получаем объект
        carNumber = findViewById(R.id.car_number);
        // Получаем текст из объекта car_number
        carNumberText = carNumber.getText().toString();

        // Получаем путь к файлу внутреннего хранилища приложения
        File file = new File(getFilesDir(), "car_number.txt");

        try {
            // Создаем объект FileWriter для записи данных в файл
            FileWriter writer = new FileWriter(file);

            // Записываем данные в файл
            writer.write(carNumberText);

            // Закрываем поток
            writer.close();

            // Выводим сообщение об успешном сохранении
            Toast.makeText(getApplicationContext(), "Машина " + carNumberText + " сохранена", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            // В случае ошибки выводим сообщение об ошибке
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Ошибка при сохранении данных", Toast.LENGTH_SHORT).show();
        }

        // Создаем объект Intent для перехода на новую активность
        Intent intent = new Intent(StartActivity.this, MainActivity.class);

        // Запускаем новую активность
        startActivity(intent);
        // Завершаем текущую активность, чтобы пользователь не мог вернуться к ней
        finish();
    }
}
