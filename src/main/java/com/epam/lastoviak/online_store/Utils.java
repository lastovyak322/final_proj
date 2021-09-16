package com.epam.lastoviak.online_store;

import java.math.BigDecimal;

public class Utils {
    public static boolean isEmptyForm(String... form){
        for (String s : form) {
            if (s == null || s.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
Object a= "a";
String b="a";
        BigDecimal z=new BigDecimal(5);
        BigDecimal x=new BigDecimal(6);
       // System.out.println(z.m);
    }
}
