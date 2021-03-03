package com.cosorio.weather;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

public class Test {
public static void main(String ... args){
    LocalDate localDateTime = LocalDate.now();
    System.out.println(localDateTime);
    Float[] floatArray = new Float[4];

    floatArray[0]= 1.2f;
    floatArray[1]= 3.2f;
    floatArray[2]= 6.6f;
    floatArray[3]= 1.7f;

    for(int i=0;i<floatArray.length;i++)
    {
        System.out.println("Element at Index : "+ i + " " + floatArray[i]);
    }

    Arrays.sort(floatArray);

    for(int i=0;i<floatArray.length;i++)
    {
        System.out.println("Element at Index : "+ i + " " + floatArray[i]);
    }


}
}
