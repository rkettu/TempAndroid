package com.example.corona_finland_app;

public class MyCustomCorona {
    private String date;
    private String area;
    private int totalHospitalised;
    private int inWard;
    private int inIcu;
    private int dead;

    MyCustomCorona(String date, String area, int totalHospitalised, int inWard, int inIcu, int dead)
    {
        this.date = date;
        this.area = area;
        this.totalHospitalised = totalHospitalised;
        this.inWard = inWard;
        this.inIcu = inIcu;
        this.dead = dead;
    }

    public String getDate() { return date; }
    public String getArea() { return area; }
    public int getTotalHospitalised() { return totalHospitalised; }
    public int getInWard() { return inWard; }
    public int getInIcu() { return inIcu; }
    public int getDead() { return dead; }

}
