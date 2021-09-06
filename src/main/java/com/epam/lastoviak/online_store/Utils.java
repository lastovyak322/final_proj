package com.epam.lastoviak.online_store;

public class Utils {
    public static boolean isEmptyForm(String... form){
        for (String s : form) {
            if (s == null || s.isEmpty()) {
                return true;
            }
        }
        return false;
    }
}
