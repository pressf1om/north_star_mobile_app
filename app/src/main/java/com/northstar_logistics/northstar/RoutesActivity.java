package com.northstar_logistics.northstar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class RoutesActivity extends AppCompatActivity {

    TextView idRoutesTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routes);

        idRoutesTxt = findViewById(R.id.idRoutesTxt);

        // Получаем Intent, который вызвал эту активность
        Intent intent = getIntent();

        // Получаем данные из Intent
        String data = intent.getStringExtra("id"); // "key" - ключ, по которому получаем данные

        idRoutesTxt.setText(String.format("ID Заявки: %s", data));

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