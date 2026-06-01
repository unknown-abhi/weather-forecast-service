package org.implantbase.weatherforecastservice.dto;


import lombok.Data;

@Data
public class Main {

    private double temp;
    private double feels_like;
    private int humidity;
}
