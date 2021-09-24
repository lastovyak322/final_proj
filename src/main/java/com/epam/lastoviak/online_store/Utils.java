package com.epam.lastoviak.online_store;

import com.epam.lastoviak.online_store.db.dto.Account;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

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
     int [] a={1,2,3,4,5,6};


    }
}
