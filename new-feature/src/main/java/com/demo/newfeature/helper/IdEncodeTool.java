package com.demo.newfeature.helper;

import com.demo.newfeature.ConfigProp;
import com.demo.newfeature.management.DemoManagement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.sqids.Sqids;

import java.util.List;
import java.util.Random;
import java.util.random.RandomGenerator;

@Slf4j
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
        log.info(" entity id:{}", id);
        return sqIds.encode(List.of(id.longValue()));
    }

    public Integer decode(String id) {
        List<Long> result = sqIds.decode(id);

        if (result.isEmpty()) {
            return -1;
        }
        return result.getFirst().intValue();
    }


    public String encodePager(DemoManagement.Pager pager) {

        return sqIds.encode(List.of(
                pager.limit().longValue(),
                DatetimeUtils.getTimestamp(pager.lastLocal())
        ));
    }

    public Optional2<DemoManagement.Pager> decodePageToken(String token) {
        if (token == null) {
            return Optional2.empty();
        }
        List<Long> result = sqIds.decode(token);

        if (result.isEmpty()) {
            return Optional2.empty();
        }
        var date = DatetimeUtils.getLocalTime(result.get(1));
        var page = new DemoManagement.Pager(result.get(0).intValue(), date);
        return Optional2.of(page);
    }
}
