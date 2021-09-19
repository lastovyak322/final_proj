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
        System.out.println("a\\s+a");
    }
}
