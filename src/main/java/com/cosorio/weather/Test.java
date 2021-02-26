package com.cosorio.weather;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;

public class Test {
public static void main(String ... args){
    LocalDate localDateTime = LocalDate.now();
    System.out.println(localDateTime);
    float[] floatArray = new float[4];

    floatArray[0]= 1.2f;
    floatArray[1]= 3.2f;
    floatArray[2]= 6.6f;
    floatArray[3]= 1.7f;

    for(int i=0;i<floatArray.length;i++)
    {
        System.out.println("Element at Index : "+ i + " " + floatArray[i]);
    }



}
}
