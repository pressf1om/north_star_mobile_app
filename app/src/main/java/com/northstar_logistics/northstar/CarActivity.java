package com.northstar_logistics.northstar;

import androidx.appcompat.app.AppCompatActivity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class CarActivity extends AppCompatActivity {

    TextView car_number_text;
    TextView dbTextView;
    EditText inputText;
    String number_car;
    SQLiteDatabase db;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car);

        car_number_text = findViewById(R.id.upperTxt);
        dbTextView = findViewById(R.id.dbTextView);
        inputText = findViewById(R.id.inputText);

        dbHelper = new DatabaseHelper(this);
        db = dbHelper.getWritableDatabase();

        // Показать содержимое базы данных
        showDatabaseContent();

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
    }

    // Метод для отображения содержимого базы данных в TextView
    private void showDatabaseContent() {
        Cursor cursor = db.query("texts", null, null, null, null, null, null);
        StringBuilder stringBuilder = new StringBuilder();
        while (cursor.moveToNext()) {
            String text = cursor.getString(cursor.getColumnIndexOrThrow("text"));
            stringBuilder.append(text).append("\n");
        }
        dbTextView.setText(stringBuilder.toString());
        cursor.close();
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

    // Переход на страницу "Главная"
    public void goMain(View view) {
        // Создаем объект Intent для перехода на новую активность
        Intent intent = new Intent(CarActivity.this, MainActivity.class);

        // Запускаем новую активность
        startActivity(intent);
    }

    // Переход на страницу "Помощь"
    public void helpMe(View view) {
        // Создаем объект Intent для перехода на новую активность
        Intent intent = new Intent(CarActivity.this, HelpMe.class);

        // Добавляем номер машины в Intent
        intent.putExtra("NUMBER_CAR", number_car);

        // Запускаем новую активность
        startActivity(intent);
    }

    // Сохранение текста в базу данных
    public void saveText(View view) {
        String text = inputText.getText().toString();
        if (!text.isEmpty()) {
            ContentValues values = new ContentValues();
            values.put("text", text);
            db.insert("texts", null, values);
            inputText.setText("");
            showDatabaseContent();
            Toast.makeText(this, "Текст сохранен", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Введите текст", Toast.LENGTH_SHORT).show();
        }
    }

    // Очистка базы данных
    public void clearDatabase(View view) {
        db.delete("texts", null, null);
        showDatabaseContent();
        Toast.makeText(this, "База данных очищена", Toast.LENGTH_SHORT).show();
    }
}
