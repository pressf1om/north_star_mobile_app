package com.northstar_logistics.northstar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.File;

public class CarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car);
    }

    // Назад на страницу входа
    public void goBackBtn(View view) {
        // Создаем объект Intent для перехода на новую активность
        Intent intent = new Intent(CarActivity.this, StartActivity.class);

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

    // Переход на страницу "Маршрут"
    public void goRoutes(View view) {
        // Создаем объект Intent для перехода на новую активность
        Intent intent = new Intent(CarActivity.this, RoutesActivity.class);

        // Запускаем новую активность
        startActivity(intent);
    }

    // Переход на страницу "Главная"
    public void goMain(View view) {
        // Создаем объект Intent для перехода на новую активность
        Intent intent = new Intent(CarActivity.this, MainActivity.class);

        // Запускаем новую активность
        startActivity(intent);
    }

    // Переход на страницу "Архив"
    public void goArchive(View view) {
        // Создаем объект Intent для перехода на новую активность
        Intent intent = new Intent(CarActivity.this, ArchiveActivity.class);

        // Запускаем новую активность
        startActivity(intent);
    }
}