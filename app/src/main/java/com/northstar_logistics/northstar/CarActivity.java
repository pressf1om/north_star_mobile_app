package com.northstar_logistics.northstar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

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