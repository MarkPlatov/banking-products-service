package com.example.bankingproductsservice.helpers;

public class StringHelper {

    public static String formatObjToJSON(String dataName, Object data){
        String str;
        if (data == null || data.toString().trim().equals("")){
            str = "[null]";
        } else {
            str = data.toString().trim();
        }

        return "{\n\"" + dataName + "\": \n" + str + "\n}";
    }
}
