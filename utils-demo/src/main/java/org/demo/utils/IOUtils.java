package org.demo.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Locale;

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
