package utils;

import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestCharUtil {

    private static final Pattern PATTERN_SPECIAL_CHARACTERS = Pattern.compile("([,\\. =\"])");

    private String escape(String string) {

        var var = string.replaceAll("\n", "_");

        return PATTERN_SPECIAL_CHARACTERS.matcher(var).replaceAll("_");
    }


    @Test
    public void testConvert() {
        var test = "this.is abc=cba \n  abd ";

        var result = escape(test);

        assertEquals("this_is_abc_cba____abd_", result);
    }
}
