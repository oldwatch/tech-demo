package com.demo.kafka.tools.helper;

import java.util.regex.Pattern;

public class Utils {

    private static final Pattern pattern = Pattern.compile("^([A-Z][^A-Z]+)");

    public static int getIntValue(String str, int defaultVal) {

        if (str != null && !str.isBlank()) {
            return Integer.parseInt(str);
        } else {
            return defaultVal;
        }
    }

    public static String getFirstWord(String str) {
        var match = pattern.matcher(str);
        if (match.find()) {
            return match.group();
        } else {
            return "";
        }
    }

    public static boolean getBooleanValue(String str, boolean b) {

        if (str != null && !str.isBlank()) {
            str = str.toLowerCase();
            return str.equals("true") || str.equals("t");
        } else {
            return b;
        }
    }
}
