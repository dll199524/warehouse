package com.example.car.Hvac;

public interface HvacCallback {

    default void onTemperatureChange(String temp) {}

}
