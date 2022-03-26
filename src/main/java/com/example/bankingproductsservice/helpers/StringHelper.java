package com.example.bankingproductsservice.helpers;

public class StringHelper {

    public static String formatObjToJSON(String dataName, Object data){
        String str = data.toString().trim();

        if (str.equals("")) {
            if (data instanceof Iterable) {
                str = "[null]";
            } else {
                str = "null";
            }
        }
        return "{\n\"" + dataName + "\": \n" + str + "\n}";
    }
}
