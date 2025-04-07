package utils;


import org.demo.helper.RecordUtils;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestRecordCopy {

    @Test
    public void testRecDuplicate() {

        var rec1 = new Src(100, null, 13.35f);

        var rec2 = RecordUtils.duplicate(rec1, Map.of("name", "mock"));

        assertEquals("mock", rec2.name);
        assertEquals(100, rec2.id);
        assertEquals(13.35f, rec2.value);

    }

    @Test
    public void testRecCopy() {

        var rec1 = new Src(200, "mock2", 13.35f);

        var now = LocalDateTime.now();
        var rec2 = RecordUtils.copy(rec1, Target.class, Map.of("date", now, "id", "200"));

        assertEquals("mock2", rec2.name);
        assertEquals("200", rec2.id);
        assertEquals(now, rec2.date);

    }

    public record Src(Integer id, String name, Float value) {

    }

    public record Target(String id, String name, LocalDateTime date) {

    }

}
