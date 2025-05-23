package com.demo.kafka.tools.helper;

import java.util.Locale;
import java.util.Random;
import java.util.regex.Pattern;

public class Utils {

    private static final Pattern pattern = Pattern.compile("^(\\w+)");
    private static final Pattern ptnName = Pattern.compile("^([A-Z]{1}[^A-Z]+)");
    private static final Pattern word2nd = Pattern.compile("^\\s?(\\S+)\\s+(\\S+)");
    private static final Random random = new Random(System.currentTimeMillis());
    private static final Pattern ptnKeyword = Pattern.compile("^[^\\-]+\\-(\\.+)$");

    public static String getKeyword(String str) {
        var matcher = ptnKeyword.matcher(str);
        if (matcher.find()) {
            return matcher.group(1);
        } else {
            return str;
        }
    }

    public static int getIntValue(String str, int defaultVal) {

        if (str != null && !str.isBlank()) {
            return Integer.parseInt(str);
        } else {
            return defaultVal;
        }
    }

    public static String getSimpleClsName(String str) {
        var match = ptnName.matcher(str);
        if (match.find()) {
            return match.group().toLowerCase(Locale.ENGLISH);
        } else {
            return "";
        }
    }

    public static Integer getRandomInt() {
        return random.nextInt(100);
    }

    public static String getFirstWord(String str) {
        var match = pattern.matcher(str);
        if (match.find()) {
            return match.group();
        } else {
            return "";
        }
    }

    public static String getSecordWord(String str) {
        var match = word2nd.matcher(str);
        if (match.find()) {
            return match.group(2);
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

    public static String removeOuter(String param) {
        if (param == null || param.isBlank()) {
            return "";
        }
        if (param.startsWith("\"") && param.endsWith("\"")) {
            return param.substring(1, param.length() - 1);
        } else {
            return param;
        }

    }

}
