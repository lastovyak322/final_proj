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

    public static void main(String[] args) {
Object a= "a";
String b="a";
        System.out.println(true);
    }
}
