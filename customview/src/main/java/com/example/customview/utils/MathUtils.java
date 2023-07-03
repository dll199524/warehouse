package com.example.customview.utils;

public class MathUtils {

    public static Boolean checkInRound(Float x, Float y, Float dx, Float dy, Float radius) {
        return Math.sqrt((x - dx) * (x - dx) + (y - dy) * (y - dy)) < radius;
    }
    public static Double distance(Double x, Double y, Double dx, Double dy) {return Math.sqrt((x - dx) * (x - dx) + (y - dy) * (y - dy));}
}
