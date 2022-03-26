package com.example.bankingproductsservice.helpers;

public class StringHelper {

    public static String formatObjToJSON(String dataName, Object data){
        return "{\n\"" + dataName + "\": \n" + data + "\n}";
    }
}
