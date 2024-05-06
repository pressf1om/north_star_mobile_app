package com.northstar_logistics.northstar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetPostWorking {

    // базовая ссылка
    private static final String BASE_URL = "http://northstar-logistics.ru/api/applications/";

    // Функция для выполнения GET запроса
    public static String get(String number_car) throws IOException {
        // убираем пробелы из номера
        String formattedNumberCar = number_car.replace(" ", "%20");
        // формируем ссылку
        String urlString = BASE_URL + formattedNumberCar;
        // запрос
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        InputStream inputStream = connection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder response = new StringBuilder();
        // сохраняем каждую линию в line
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        // response - финальный результат
        reader.close();
        return response.toString();
    }

    // Функция для выполнения POST запроса
    public static String post(String number_car, String new_status) throws IOException {
        // формируем ссылку
        String formattedNumberCar = number_car.replace(" ", "%20");
        // формируем ссылку
        String urlString = BASE_URL + formattedNumberCar;
        // запрос
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        // тело запроса
        String postData = "{\"new_status\": \"" + new_status + "\"}";

        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = postData.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        int responseCode = connection.getResponseCode();
        return ("POST Response Code : " + responseCode);
    }
}




