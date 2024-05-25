package com.northstar_logistics.northstar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HelpMe extends AppCompatActivity {

    private TextInputEditText textInputEditText;
    private OkHttpClient client;
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    TextInputLayout enterProblemField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_me);

        enterProblemField = findViewById(R.id.enterProblemField);
        client = new OkHttpClient();
    }

    public void goBack(View view) {
        // Создаем объект Intent для перехода на новую активность
        Intent intent = new Intent(HelpMe.this, CarActivity.class);
        // Запускаем новую активность
        startActivity(intent);
    }

    public void sendPostRequest(View view) {
        String text = enterProblemField.getEditText().getText().toString();
        if (!text.isEmpty()) {
            postRequest("http://northstar-logistics.ru/help_me_driver", text);
            enterProblemField.getEditText().getText().clear();
        } else {
            Toast.makeText(this, "Пожалуйста, введите текст", Toast.LENGTH_SHORT).show();
        }
    }

    private void postRequest(String url, String message) {
        OkHttpClient client = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("message", message);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(HelpMe.this, "Ошибка при отправке запроса", Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                }
                runOnUiThread(() -> Toast.makeText(HelpMe.this, "Запрос успешно отправлен", Toast.LENGTH_SHORT).show());
            }
        });
    }

}
