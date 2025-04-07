package utils;


import com.demo.newfeature.helper.RecordUtils;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestRecordCopy {

    @Test
    public void testRecCopy() {

        var rec1 = new Src(100, null, 13.35f);

        var rec2 = RecordUtils.copy(rec1, Map.of("name", "mock"));

        assertEquals("mock", rec2.name);
        assertEquals(100, rec2.id);
        assertEquals(13.35f, rec2.value);

    }

    record Src(Integer id, String name, Float value) {

    }

    record Target(String id, String name, LocalDateTime date) {

    }

}
