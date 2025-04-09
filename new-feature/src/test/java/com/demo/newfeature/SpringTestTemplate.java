package com.demo.newfeature;

import com.demo.newfeature.helper.IdEncodeTool;
import com.demo.newfeature.helper.jackson.EntityIDMask;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class SpringTestTemplate {

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private IdEncodeTool encodeTool;

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
