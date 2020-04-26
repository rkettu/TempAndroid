package com.example.corona_finland_app;

public class DateParser {
    public static String parse(String dateString)
    {
        return dateString.substring(0,dateString.indexOf("T")).replaceAll("-","/");
    }
}
