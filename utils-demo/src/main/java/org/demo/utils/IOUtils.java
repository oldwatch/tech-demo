package org.demo.utils;

import module java.base;


public class IOUtils {

    public static String getCleanInput(BufferedReader reader) throws IOException {
        var input = reader.readLine();
        if (input == null || input.isBlank()) {
            return null;
        }
        input = input.trim().toLowerCase(Locale.ENGLISH);
        return input;
    }
}
