package com.northstar_logistics.northstar;

public class DataModel {
    private String id;
    private String car_now;
    private String coord_end;
    private String coord_start;
    private String date_of_start;
    private String status;
    private String weight;
    private String result;

    public String getDataToShow() {

        result = "ID: " + id + "\n" +
                "Машина: " + car_now + "\n" +
                "Начальные координаты: " + coord_start + "\n" +
                "Конечные координаты: " + coord_end + "\n" +
                "Дата старта: " + date_of_start + "\n" +
                "Статус: " + status + "\n" +
                "Вес: " + weight + "\n";

        return result;
    }
}

