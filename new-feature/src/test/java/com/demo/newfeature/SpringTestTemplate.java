package com.demo.newfeature;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.demo.idconvert.IdEncodeTool;
import org.demo.idconvert.jackson.EntityIDMask;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class SpringTestTemplate {

    private final static Logger log = LoggerFactory.getLogger(SpringTestTemplate.class);

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private IdEncodeTool encodeTool;

    @Test
    public void testJson() {
        var one = new One("test");
        var two = new Two(2, "name");
        var three = new Three(3);

        var numList = new NumberList(one, two, three);

        try {
            var json = mapper.writeValueAsString(numList);

            log.info("json:{}", json);
            var newList = mapper.readValue(json, NumberList.class);

            assertEquals(3, newList.list.size());
            for (var e : newList.list()) {
                switch (e) {
                    case One o -> assertEquals("test", o.str());
                    case Two t -> assertEquals("name", t.name());
                    case Three th -> assertEquals(3, th.value());
                }
            }

        } catch (JsonProcessingException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testCustomerJackson() {

        var test = new TestRec(1000, "tset", 101);


        try {
            var json = mapper.writeValueAsString(test);
            var withID = mapper.readValue(json, WithEntityID.class);
            assertEquals(101, withID.val);

            String idStr = withID.id;
            Integer id = encodeTool.decode(idStr);
            assertEquals(1000, id);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            fail();
//            throw new RuntimeException(e);
        }


    }

    public record NumberList(List<DemoData> list) {
        public NumberList(One o, Two t, Three th) {
            List<DemoData> list = List.of(o, t, th);
            this(list);
        }
    }

    public record TestRec(
            @EntityIDMask
            Integer id,
            String name,
            Integer val) {

    }

    public record WithEntityID(
            String id,
            String name,
            Integer val) {
    }

}
