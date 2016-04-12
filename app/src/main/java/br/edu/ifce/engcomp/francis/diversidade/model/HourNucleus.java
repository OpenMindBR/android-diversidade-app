package br.edu.ifce.engcomp.francis.diversidade.model;

import java.io.Serializable;

/**
 * Created by Bolsista on 12/04/2016.
 */
public class HourNucleus implements Serializable {
    String weekDays;
    String hour;

    public HourNucleus(String weekDays, String hour) {
        this.weekDays = weekDays;
        this.hour = hour;

    }

    public String getWeekDays() {
        return weekDays;
    }

    public void setWeekDays(String weekDays) {
        this.weekDays = weekDays;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }
}
