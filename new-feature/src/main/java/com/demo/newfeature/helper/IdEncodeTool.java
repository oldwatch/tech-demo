package com.demo.newfeature.helper;

import com.demo.newfeature.ConfigProp;
import org.springframework.stereotype.Component;
import org.sqids.Sqids;

import java.util.List;
import java.util.Random;
import java.util.random.RandomGenerator;

@Component
public class IdEncodeTool {

    private final Sqids sqIds;
    private final Random random;

    public IdEncodeTool(ConfigProp prop) {
        sqIds = Sqids.builder()
                .minLength(8)
                .alphabet(prop.sqIdMask())
                .build();
        random = Random.from(RandomGenerator.getDefault());
    }

    public String encode(Integer id) {
        return sqIds.encode(List.of(id.longValue(), random.nextLong()));
    }

    public Integer decode(String id) {
        List<Long> result = sqIds.decode(id);

        if (result.isEmpty()) {
            return -1;
        }
        return result.getFirst().intValue();
    }

}
